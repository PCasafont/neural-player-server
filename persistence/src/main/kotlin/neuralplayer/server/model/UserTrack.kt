package neuralplayer.server.model

import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 * @author Pere
 * @since 2018/04/21
 */
@Entity
@Table(name = "user_track")
class UserTrack {

	@Embeddable
	data class PK(
			@Column(name = "user_id")
			val userId: Long = -1,
			@Column(name = "track_id")
			val trackId: Long = -1) : Serializable

	@EmbeddedId
	lateinit var id: PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	lateinit var user: User

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "track_id", insertable = false, updatable = false)
	lateinit var track: Track

	@Column(name = "preference_score", nullable = false)
	var preferenceScore: Double = 0.0

	constructor()

	constructor(user: User, track: Track, preferenceScore: Double) {
		id = PK(user.id!!, track.id!!)
		this.user = user
		this.track = track
		this.preferenceScore = preferenceScore
	}


	override fun equals(other: Any?): Boolean {
		return this === other || other is UserTrack && id == other.id
	}

	override fun hashCode(): Int {
		return Objects.hashCode(id)
	}

	override fun toString(): String {
		return "$user|$track"
	}
}
