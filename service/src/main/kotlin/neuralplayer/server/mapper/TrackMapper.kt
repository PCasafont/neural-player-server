package neuralplayer.server.mapper

import neuralplayer.server.dto.TrackDto
import neuralplayer.server.model.Track
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
				user?.let { userTrackRepository.findByUserAndTrack(it, track) }?.preferenceScore ?: 0.0)
	}
}
