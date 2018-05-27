package neuralplayer.server.mapper

import neuralplayer.server.dto.PlaylistDto
import neuralplayer.server.model.Playlist
import org.springframework.stereotype.Component

/**
 * @author Pere
 * @since 2018/04/21
 */
@Component
class PlaylistMapper {

	fun createPlaylistDto(playlist: Playlist): PlaylistDto {
		return PlaylistDto(playlist.id, playlist.name, playlist.creator.displayName, playlist.tracks.map { it.id!! })
	}
}
