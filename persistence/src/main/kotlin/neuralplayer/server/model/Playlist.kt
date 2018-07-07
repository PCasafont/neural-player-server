package neuralplayer.server.model

import java.util.*
import javax.persistence.*

/**
 * @author Pere
 * @since 2018/04/21
 */
@Entity
@Table(name = "playlist")
class Playlist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	var id: Long? = null

	@Column(name = "name", nullable = false)
	lateinit var name: String

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_id")
	lateinit var creator: User

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "playlist_track",
			joinColumns = [JoinColumn(name = "playlist_id")],
			inverseJoinColumns = [JoinColumn(name = "track_id")])
	lateinit var tracks: MutableSet<TrackBasicData>

	constructor()

	constructor(name: String, creator: User, tracks: MutableSet<TrackBasicData> = HashSet()) {
		this.name = name
		this.creator = creator
		this.tracks = tracks
	}


	override fun equals(other: Any?): Boolean {
		return this === other || other is Playlist && id == other.id
	}

	override fun hashCode(): Int {
		return Objects.hashCode(id)
	}

	override fun toString(): String {
		return "$name: $tracks"
	}
}
