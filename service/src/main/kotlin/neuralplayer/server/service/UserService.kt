package neuralplayer.server.service

import neuralplayer.server.dto.UserDto
import neuralplayer.server.model.Role
import neuralplayer.server.model.User
import neuralplayer.server.repository.UserRepository
import neuralplayer.server.util.ServicePreconditions
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 * @author Pere
 * @since 2018/04/15
 */
@Service
@Transactional
class UserService(private val userRepository: UserRepository,
				  private val passwordEncoder: PasswordEncoder) {

	/**
	 * Finds the user with the given id.
	 *
	 * @param id The identifier of the user to find.
	 * @return The user corresponding to the given id.
	 */
	@Transactional(readOnly = true)
	fun findById(id: Long): User? {
		return userRepository.findById(id).orElse(null)
	}

	/**
	 * Finds the user with the given username.
	 *
	 * @param username The unique username of the user to find.
	 * @return The user corresponding to the given id.
	 */
	@Transactional(readOnly = true)
	fun findByUsername(username: String): User? {
		return userRepository.findByUsername(username)
	}

	@Transactional(readOnly = true)
	fun findByEmail(email: String): User? {
		return userRepository.findByEmail(email)
	}

	/**
	 * Returns a list with all the entities in the service.<br></br>
	 * If nothing is found, an empty list will be returned to the calling client.
	 *
	 * @return The list of all the entities.
	 */
	@Transactional(readOnly = true)
	fun findAll(): List<User> {
		return userRepository.findAll()
	}

	@Transactional
	fun create(userDto: UserDto): User {
		return userRepository.save(User(
				ServicePreconditions.checkRequestElementNotNull(userDto.username),
				ServicePreconditions.checkRequestElementNotNull(userDto.email),
				passwordEncoder.encode(ServicePreconditions.checkRequestElementNotNull(userDto.password)),
				ServicePreconditions.checkRequestElementNotNull(userDto.displayName),
				(userDto.roles ?: listOf("USER")).map { Role.valueOf(it) },
				LocalDateTime.now()))
	}

	@Transactional
	fun update(user: User, userDto: UserDto): User {
		ServicePreconditions.checkRequestElementNotNull(user.id)
		ServicePreconditions.checkRequestElementNotNull(userDto)
		user.username = ServicePreconditions.checkRequestElementNotNull(userDto.username)
		user.email = ServicePreconditions.checkRequestElementNotNull(userDto.email)
		if (userDto.password != null) {
			user.password = passwordEncoder.encode(userDto.password)
		}
		user.displayName = ServicePreconditions.checkRequestElementNotNull(userDto.displayName)
		user.roles = ServicePreconditions.checkRequestElementNotNull(userDto.roles).map { Role.valueOf(it) }.toMutableSet()
		return userRepository.save(user)
	}

	/**
	 * Deletes the given user.
	 *
	 * @param user The user to delete.
	 */
	@Transactional
	fun delete(user: User) {
		userRepository.delete(user)
	}

	companion object {
		private val LOGGER = LoggerFactory.getLogger(UserService::class.java)
	}
}
