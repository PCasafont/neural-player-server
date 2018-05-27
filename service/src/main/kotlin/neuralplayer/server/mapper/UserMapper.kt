package neuralplayer.server.mapper

import neuralplayer.server.dto.UserDto
import neuralplayer.server.model.User
import org.springframework.stereotype.Component

/**
 * @author Pere
 * @since 2018/04/15
 */
@Component
class UserMapper {

	fun createUserDto(user: User): UserDto {
		return UserDto(user.id, user.username, user.email, null, user.displayName, user.roles.map { it.name }.toList())
	}
}
