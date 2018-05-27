package neuralplayer.server.service

import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

/**
 * Database user authentication service.
 */
@Service
class SpringUserService(private val userService: UserService) : ReactiveUserDetailsService {

	/**
	 * Finds a user by its name in the service.
	 */
	override fun findByUsername(username: String): Mono<UserDetails> {
		val user = userService.findByUsername(username) ?: throw UsernameNotFoundException("Username was not found: $username")
		val roleStringsAsArray = user.roles.map { it.name }.toTypedArray()
		val authorities = AuthorityUtils.createAuthorityList(*roleStringsAsArray)
		return Mono.just(User(user.username, user.password, authorities))
	}
}
