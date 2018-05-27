package neuralplayer.server.util

import neuralplayer.server.exception.BadRequestException
import javax.persistence.EntityNotFoundException

object ServicePreconditions {

	/**
	 * Ensures that the entity reference passed as a parameter to the calling method is not null.
	 *
	 * @param entity An object reference
	 * @return The non-null reference that was validated
	 * @throws EntityNotFoundException if `entity` is null
	 */
	fun <T> checkEntityExists(entity: T?): T {
		return entity ?: throw EntityNotFoundException()
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
