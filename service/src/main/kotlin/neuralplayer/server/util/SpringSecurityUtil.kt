package neuralplayer.server.util

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails


object SpringSecurityUtil {

	fun getCurrentAuthentication(): Authentication? = SecurityContextHolder.getContext()?.authentication

	/**
	 * Gets the current user details.
	 *
	 * @return The current user details or null if can't be retrieved.
	 */
	fun getCurrentUserDetails(): UserDetails? = getCurrentAuthentication()?.principal as? UserDetails

	fun getCurrentUser(): User? = getCurrentAuthentication()?.principal as? User

	fun getNameOfCurrentUser(): String? = getCurrentAuthentication()?.name

	/**
	 * Check if current user is authenticated.
	 *
	 * @return true if user is authenticated.
	 */
	fun isAuthenticated(): Boolean = SpringSecurityUtil.getCurrentUserDetails() != null

	/**
	 * Check if current user is anonymous guest.
	 *
	 * @return true if user is anonymous guest.
	 */
	fun isAnonymous(): Boolean = SpringSecurityUtil.getCurrentUserDetails() == null
}
