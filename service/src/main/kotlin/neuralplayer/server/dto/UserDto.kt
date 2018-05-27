package neuralplayer.server.dto

/**
 * @author Pere
 * @since 2018/04/15
 */
data class UserDto(val id: Long? = null,
				   val username: String? = null,
				   val email: String? = null,
				   val password: String? = null,
				   val displayName: String? = null,
				   val roles: List<String>? = null)
