package neuralplayer.server.model

import java.util.*
import javax.persistence.*

/**
 * @author Pere
 * @since 2018/04/21
 */
@Entity
@Table(name = "track")
class TrackBasicData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	var id: Long? = null

	@Column(name = "title", nullable = false)
	lateinit var title: String

	@Column(name = "artist")
	var artist: String? = null

	@Column(name = "album")
	var album: String? = null

	@Column(name = "file_extension")
	lateinit var fileExtension: String

	override fun equals(other: Any?): Boolean {
		return this === other || other is TrackBasicData && id == other.id
	}

	override fun hashCode(): Int {
		return Objects.hashCode(id)
	}

	override fun toString(): String {
		return "$title|$artist|$album"
	}
}
