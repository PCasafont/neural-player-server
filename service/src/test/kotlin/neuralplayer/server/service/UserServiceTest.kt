package neuralplayer.server.service

import neuralplayer.server.config.ServiceConfiguration
import neuralplayer.server.dto.UserDto
import neuralplayer.server.model.Role
import neuralplayer.server.model.User
import neuralplayer.server.repository.UserRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

/**
 * @author Pere
 * @since 2018/04/19
 */
@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [ServiceConfiguration::class])
class UserServiceTest {

	@MockBean
	private lateinit var userRepository: UserRepository

	@Autowired
	private lateinit var userService: UserService

	@Autowired
	private lateinit var passwordEncoder: PasswordEncoder

	private lateinit var users: MutableMap<Long, User>

	@Before
	fun init() {
		// Initialize users
		users = mutableMapOf(
				1L to User("admin", "admin@mail.mail", "adminpass", "Admin", listOf(Role.ADMIN)),
				2L to User("user1", "user1@mail.mail", "user1pass", "User 1", listOf(Role.USER)),
				3L to User("user2", "user2@mail.mail", "user2pass", "User 2", listOf(Role.USER)),
				4L to User("user3", "user3@mail.mail", "user3pass", "User 3", listOf(Role.USER)))
		users.forEach { id, user -> user.id = id }

		// Initialize argument captors
		val longCaptor = ArgumentCaptor.forClass(Long::class.java)
		val stringCaptor = ArgumentCaptor.forClass(String::class.java)
		val userCaptor = ArgumentCaptor.forClass(User::class.java)

		// Initialize mocks
		`when`(userRepository.findById(longCaptor.capture())).thenAnswer {
			Optional.ofNullable(users[longCaptor.value])
		}
		`when`(userRepository.findByUsername(stringCaptor.capture() ?: "")).thenAnswer {
			users.values.find { it.username == stringCaptor.value }
		}
		`when`(userRepository.findByEmail(stringCaptor.capture() ?: "")).thenAnswer {
			users.values.find { it.email == stringCaptor.value }
		}
		`when`(userRepository.findAll()).thenReturn(users.values.toList())
		`when`(userRepository.save(userCaptor.capture())).thenAnswer {
			val id = userCaptor.value.id ?: (users.values.mapNotNull { it.id }.max() ?: 0L) + 1L
			userCaptor.value.id = id
			users.put(id, userCaptor.value)
			userCaptor.value
		}
		`when`(userRepository.delete(userCaptor.capture())).thenAnswer {
			val id = userCaptor.value.id
			users.remove(id)
		}
	}

	@Test
	fun `find by id`() {
		Assert.assertEquals(users[1], userService.findById(1))
	}

	@Test
	fun `find not existent by id`() {
		Assert.assertEquals(null, userService.findById(-1))
	}

	@Test
	fun `find by username`() {
		Assert.assertEquals(users[3], userService.findByUsername("user2"))
	}

	@Test
	fun `find by email`() {
		Assert.assertEquals(users[4], userService.findByEmail("user3@mail.mail"))
	}

	@Test
	fun `find all`() {
		Assert.assertEquals(users.values.toList(), userService.findAll())
	}

	@Test
	fun `create one`() {
		val newUser = userService.create(UserDto(null, "test", "test@test.test", "test", "test", listOf("USER")))
		Assert.assertNotNull(userService.findById(newUser.id!!))
	}

	@Test
	fun `update one`() {
		val user = users[1]!!
		userService.update(user, UserDto(null, "test", "test@test.test", null, "test", listOf("USER")))
		val updatedUser = userService.findById(user.id!!)!!
		Assert.assertEquals("test", updatedUser.username)
		Assert.assertEquals("test@test.test", updatedUser.email)
		Assert.assertEquals("test", updatedUser.displayName)
		Assert.assertEquals(listOf(Role.USER).toString(), updatedUser.roles.toString())
	}

	@Test
	fun `update one and verify password encryption`() {
		val user = users[2]!!
		userService.update(user, UserDto(null, "test", "test@test.test", "test", "test", listOf("USER")))
		val updatedUser = userService.findById(user.id!!)!!
		Assert.assertTrue(passwordEncoder.matches("test", updatedUser.password))
	}

	@Test
	fun `delete one`() {
		val user = users[4]!!
		userService.delete(user)
		Assert.assertNull(userService.findById(user.id!!))
	}
}
