package neuralplayer.server.dto

/**
 * @author Pere
 * @since 2018/04/15
 */
data class PlaylistDto(val id: Long? = null,
					   val name: String? = null,
					   val creator: String? = null,
					   val tracks: List<Long>? = null)
