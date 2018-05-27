package neuralplayer.server.web

import neuralplayer.server.dto.UserDto
import neuralplayer.server.mapper.UserMapper
import neuralplayer.server.model.Role
import neuralplayer.server.model.User
import neuralplayer.server.service.UserService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList

/**
 * @author Pere
 * @since 2018/04/19
 */
@RunWith(SpringRunner::class)
@WebFluxTest(UserController::class)
class UserControllerTest {

	@Autowired
	private lateinit var webTestClient: WebTestClient

	@MockBean
	private lateinit var userService: UserService

	@MockBean
	private lateinit var userMapper: UserMapper

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
		val userCaptor = ArgumentCaptor.forClass(User::class.java)
		val userDtoCaptor = ArgumentCaptor.forClass(UserDto::class.java)

		// Initialize mocks
		`when`(userService.findAll()).thenReturn(users.values.toList())
		`when`(userService.create(userDtoCaptor.capture() ?: UserDto())).thenAnswer {
			val id = (users.values.mapNotNull { it.id }.max() ?: 0L) + 1L
			val userDto = userDtoCaptor.value
			users.put(id, User(userDto.username!!, userDto.email!!, userDto.password!!, userDto.displayName!!, userDto.roles!!.map { Role.valueOf(it) }))
		}
		`when`(userMapper.createUserDto(userCaptor.capture() ?: User("", "", "", "", emptyList()))).thenAnswer {
			UserMapper().createUserDto(userCaptor.value)
		}
	}

	@Test
	fun `get all`() {
		webTestClient.get()
				.uri("/users")
				.exchange()
				.expectStatus().is2xxSuccessful
				.expectBodyList<UserDto>()
				.hasSize(users.size)
	}

	@Test
	fun `save one`() {
		val prevSize = users.size
		webTestClient.post()
				.uri("/users")
				.syncBody(UserDto(null, "test", "test@test.test", "test", "test", listOf("USER")))
				.exchange()
				.expectStatus().is2xxSuccessful
		Assert.assertEquals(prevSize + 1, users.size)
	}
}
