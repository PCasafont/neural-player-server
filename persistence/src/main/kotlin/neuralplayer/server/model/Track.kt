package neuralplayer.server.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

/**
 * @author Pere
 * @since 2018/04/21
 */
@Entity
@Table(name = "track")
class Track {

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

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "file")
	lateinit var file: ByteArray

	@Column(name = "file_extension")
	lateinit var fileExtension: String

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "created_at", nullable = false)
	lateinit var createdAt: LocalDateTime
		private set

	constructor()

	constructor(title: String, artist: String? = null, album: String? = null, file: ByteArray, fileExtension: String, createdAt: LocalDateTime = LocalDateTime.now()) {
		this.title = title
		this.artist = artist
		this.album = album
		this.file = file
		this.fileExtension = fileExtension
		this.createdAt = createdAt
	}

	override fun equals(other: Any?): Boolean {
		return this === other || other is Track && id == other.id
	}

	override fun hashCode(): Int {
		return Objects.hashCode(id)
	}

	override fun toString(): String {
		return "$title|$artist|$album"
	}
}
