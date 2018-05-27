package neuralplayer.server.service

import neuralplayer.server.dto.PlaylistDto
import neuralplayer.server.model.Playlist
import neuralplayer.server.model.User
import neuralplayer.server.repository.PlaylistRepository
import neuralplayer.server.util.ServicePreconditions
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Pere
 * @since 2018/04/15
 */
@Service
@Transactional
class PlaylistService(private val playlistRepository: PlaylistRepository,
					  private val trackService: TrackService) {

	@Transactional(readOnly = true)
	fun findById(id: Long): Playlist? {
		return playlistRepository.findById(id).orElse(null)
	}

	@Transactional(readOnly = true)
	fun findAll(): List<Playlist> {
		return playlistRepository.findAll()
	}

	@Transactional
	fun create(playlistDto: PlaylistDto, creator: User): Playlist {
		val playlist = Playlist(ServicePreconditions.checkRequestElementNotNull(playlistDto.name), creator)
		return playlistRepository.save(playlist)
	}

	@Transactional
	fun update(id: Long, playlistDto: PlaylistDto): Playlist {
		val playlist = ServicePreconditions.checkEntityExists(findById(id))
		ServicePreconditions.checkRequestElementNotNull(playlistDto)
		playlist.name = ServicePreconditions.checkRequestElementNotNull(playlistDto.name)
		playlist.tracks = ServicePreconditions.checkRequestElementNotNull(playlistDto.tracks).map {
			ServicePreconditions.checkEntityExists(trackService.findById(it))
		}.toMutableSet()
		return playlistRepository.save(playlist)
	}

	@Transactional
	fun delete(playlist: Playlist) {
		playlistRepository.delete(playlist)
	}

	@Transactional
	fun delete(id: Long) {
		playlistRepository.deleteById(id)
	}

	companion object {
		private val LOGGER = LoggerFactory.getLogger(PlaylistService::class.java)
	}
}
