package neuralplayer.server.dto

/**
 * @author Pere
 * @since 2018/04/15
 */
data class TrackDto(val id: Long? = null,
					val title: String? = null,
					val artist: String? = null,
					val album: String? = null,
					val fileExtension: String? = null,
					val preferenceScore: Double? = null)
