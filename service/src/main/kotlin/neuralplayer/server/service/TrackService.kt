package neuralplayer.server.service

import neuralplayer.server.dto.TrackDto
import neuralplayer.server.model.Track
import neuralplayer.server.model.TrackBasicData
import neuralplayer.server.model.User
import neuralplayer.server.model.UserTrack
import neuralplayer.server.repository.TrackBasicDataRepository
import neuralplayer.server.repository.TrackRepository
import neuralplayer.server.repository.UserTrackRepository
import neuralplayer.server.util.ServicePreconditions
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.io.ByteArrayOutputStream
import javax.persistence.EntityExistsException

/**
 * @author Pere
 * @since 2018/04/15
 */
@Service
@Transactional
class TrackService(private val trackRepository: TrackRepository,
				   private val trackBasicDataRepository: TrackBasicDataRepository,
				   private val userTrackRepository: UserTrackRepository) {

	@Transactional(readOnly = true)
	fun findById(id: Long): Track? {
		return trackRepository.findById(id).orElse(null)
	}

	@Transactional(readOnly = true)
	fun findBasicById(id: Long): TrackBasicData? {
		return trackBasicDataRepository.findById(id).orElse(null)
	}

	@Transactional(readOnly = true)
	fun findAllBasic(): List<TrackBasicData> {
		return trackBasicDataRepository.findAll()
	}

	@Transactional
	fun create(trackDto: TrackDto, filePart: FilePart): Mono<Track> {
		val title = ServicePreconditions.checkRequestElementNotNull(trackDto.title)
		val track = trackBasicDataRepository.findByTitleAndArtistAndAlbum(title, trackDto.artist, trackDto.album)
		if (track != null) {
			throw EntityExistsException("There's a track with the same title, artist and album already.")
		}

		return filePart.content().collectList().map {
			val baos = ByteArrayOutputStream()
			it.forEach {
				baos.write(it.asByteBuffer().array())
			}
			val fileExtension = filePart.filename().substringAfterLast('.', "")
			trackRepository.save(Track(
					title,
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
	fun update(id: Long, trackDto: TrackDto): TrackBasicData {
		val track = ServicePreconditions.checkEntityExists(findBasicById(id))
		ServicePreconditions.checkRequestElementNotNull(trackDto)
		track.title = ServicePreconditions.checkRequestElementNotNull(trackDto.title)
		track.artist = trackDto.artist
		track.album = trackDto.album
		return trackBasicDataRepository.save(track)
	}

	@Transactional
	fun updatePreferenceScore(id: Long, user: User, preferenceScore: Double) {
		val track = ServicePreconditions.checkEntityExists(findBasicById(id))
		val userTrack = userTrackRepository.findByUserAndTrackId(user, track.id!!)?.apply {
			this.preferenceScore = preferenceScore
		} ?: UserTrack(user, track, preferenceScore)
		userTrackRepository.save(userTrack)
	}

	@Transactional
	fun delete(track: TrackBasicData) {
		trackBasicDataRepository.delete(track)
	}

	@Transactional
	fun delete(id: Long) {
		trackBasicDataRepository.deleteById(id)
	}
}
