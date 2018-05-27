package neuralplayer.server.repository

import neuralplayer.server.config.PersistenceJpaConfiguration
import neuralplayer.server.model.Role
import neuralplayer.server.model.User
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDateTime

/**
 * @author Pere
 * @since 2018/04/19
 */
@RunWith(SpringRunner::class)
@DataJpaTest
@ContextConfiguration(classes = [PersistenceJpaConfiguration::class])
class UserRepositoryTest {

	@Autowired
	private lateinit var userRepository: UserRepository

	private lateinit var user: User

	@Before
	fun init() {
		user = userRepository.save(User(
				"test",
				"test@test.test",
				"pass",
				"Test",
				arrayListOf(Role.USER),
				LocalDateTime.now()))
	}

	@Test
	fun `find by id`() {
		Assert.assertEquals(user, userRepository.findById(user.id!!).get())
	}

	@Test
	fun `find by username`() {
		Assert.assertEquals(user, userRepository.findByUsername(user.username))
	}

	@Test
	fun `find by email`() {
		Assert.assertEquals(user, userRepository.findByEmail(user.email))
	}

	@Test
	fun `save new user`() {
		userRepository.save(User("test2",
				"test2@test.test",
				"pass",
				"Test2",
				arrayListOf(Role.USER),
				LocalDateTime.now()))
	}

	@Test(expected = DataIntegrityViolationException::class)
	fun `duplicate username`() {
		userRepository.save(User("test",
				"test2@test.test",
				"pass",
				"Test2",
				arrayListOf(Role.USER),
				LocalDateTime.now()))
	}

	@Test(expected = DataIntegrityViolationException::class)
	fun `duplicate email`() {
		userRepository.save(User("test2",
				"test@test.test",
				"pass",
				"Test2",
				arrayListOf(Role.USER),
				LocalDateTime.now()))
	}

	@Test
	fun `delete by id`() {
		userRepository.deleteById(user.id!!)
	}

	@Test
	fun `delete by entity`() {
		userRepository.delete(user)
	}
}
