package neuralplayer.server.service

import neuralplayer.server.dto.TrackDto
import neuralplayer.server.model.Track
import neuralplayer.server.model.User
import neuralplayer.server.model.UserTrack
import neuralplayer.server.repository.TrackRepository
import neuralplayer.server.repository.UserTrackRepository
import neuralplayer.server.util.ServicePreconditions
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.io.ByteArrayOutputStream



/**
 * @author Pere
 * @since 2018/04/15
 */
@Service
@Transactional
class TrackService(private val trackRepository: TrackRepository,
				   private val userTrackRepository: UserTrackRepository) {

	@Transactional(readOnly = true)
	fun findById(id: Long): Track? {
		return trackRepository.findById(id).orElse(null)
	}

	@Transactional(readOnly = true)
	fun findAll(): List<Track> {
		return trackRepository.findAll()
	}

	@Transactional
	fun create(trackDto: TrackDto, filePart: FilePart): Mono<Track> {
		val track = findAll().find {
			it.title == trackDto.title && it.artist == trackDto.artist && it.album == trackDto.album
		}
		if (track != null) {
			return Mono.just(track)
		}

		return filePart.content().collectList().map {
			val baos = ByteArrayOutputStream()
			it.forEach {
				baos.write(it.asByteBuffer().array())
			}
			val fileExtension = filePart.filename().substringAfterLast('.', "")
			trackRepository.save(Track(
					ServicePreconditions.checkRequestElementNotNull(trackDto.title),
					trackDto.artist,
					trackDto.album,
					baos.toByteArray(),
					fileExtension))
		}
	}

	@Transactional
	fun getFile(trackId: Long): ByteArray {
		val track = ServicePreconditions.checkEntityExists(findById(trackId))
		return track.file
	}

	@Transactional
	fun update(id: Long, trackDto: TrackDto): Track {
		val track = ServicePreconditions.checkEntityExists(findById(id))
		ServicePreconditions.checkRequestElementNotNull(trackDto)
		track.title = ServicePreconditions.checkRequestElementNotNull(trackDto.title)
		track.artist = trackDto.artist
		track.album = trackDto.album
		return trackRepository.save(track)
	}

	@Transactional
	fun updatePreferenceScore(id: Long, user: User, preferenceScore: Double) {
		val track = ServicePreconditions.checkEntityExists(findById(id))
		val userTrack = userTrackRepository.save(
				userTrackRepository.findByUserAndTrack(user, track)?.apply {
					this.preferenceScore = preferenceScore
				} ?: UserTrack(user, track, preferenceScore)
		)
		userTrackRepository.save(userTrack)
	}

	@Transactional
	fun delete(track: Track) {
		trackRepository.delete(track)
	}

	@Transactional
	fun delete(id: Long) {
		trackRepository.deleteById(id)
	}
}
