package neuralplayer.server.mapper

import neuralplayer.server.dto.TrackDto
import neuralplayer.server.model.Track
import neuralplayer.server.model.TrackBasicData
import neuralplayer.server.model.User
import neuralplayer.server.repository.UserTrackRepository
import org.springframework.stereotype.Component

/**
 * @author Pere
 * @since 2018/04/21
 */
@Component
class TrackMapper(private val userTrackRepository: UserTrackRepository) {

	fun createTrackDto(track: Track, user: User? = null): TrackDto {
		return TrackDto(
				track.id,
				track.title,
				track.artist,
				track.album,
				track.fileExtension,
				user?.let { userTrackRepository.findByUserAndTrackId(it, track.id!!) }?.preferenceScore ?: 0.0)
	}

	fun createTrackDto(track: TrackBasicData, user: User? = null): TrackDto {
		return TrackDto(
				track.id,
				track.title,
				track.artist,
				track.album,
				track.fileExtension,
				user?.let { userTrackRepository.findByUserAndTrackId(it, track.id!!) }?.preferenceScore ?: 0.0)
	}
}
