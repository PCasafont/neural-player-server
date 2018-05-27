package neuralplayer.server.util

import neuralplayer.server.exception.BadRequestException
import neuralplayer.server.exception.UnauthorizedException
import javax.persistence.EntityNotFoundException

object WebPreconditions {

	/**
	 * Ensures that the user entity is not null.
	 *
	 * @param entity An user/principial reference
	 * @return The non-null reference that was validated
	 * @throws UnauthorizedException if `entity` is null
	 */
	fun <T> checkUser(entity: T?): T {
		return entity ?: throw UnauthorizedException()
	}

	/**
	 * Ensures that the entity reference passed as a parameter to the calling method is not null.
	 *
	 * @param entity An object reference
	 * @return The non-null reference that was validated
	 * @throws EntityNotFoundException if `entity` is null
	 */
	fun <T> checkRequestElementNotNull(entity: T?): T {
		return entity ?: throw BadRequestException()
	}
}
