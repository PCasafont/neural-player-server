package neuralplayer.server.model

import neuralplayer.server.converter.JpaRoleListConverter
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

/**
 * @author Pere
 * @since 2018/04/15
 */
@Entity
@Table(name = "user")
class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	var id: Long? = null

	@Column(name = "username", unique = true, nullable = false)
	lateinit var username: String

	@Column(name = "email", unique = true, nullable = false)
	lateinit var email: String

	@Column(name = "password", nullable = false)
	lateinit var password: String

	@Column(name = "display_name", nullable = false)
	lateinit var displayName: String

	@Lob
	@Convert(converter = JpaRoleListConverter::class)
	@Column(name = "roles", nullable = false)
	lateinit var roles: MutableSet<Role>

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "created_at", nullable = false)
	lateinit var createdAt: LocalDateTime
		private set

	constructor()

	constructor(username: String,
				email: String,
				password: String,
				displayName: String,
				roles: Collection<Role>,
				createdAt: LocalDateTime = LocalDateTime.now()) {
		this.username = username
		this.email = email
		this.password = password
		this.displayName = displayName
		this.roles = HashSet(roles)
		this.createdAt = createdAt
	}

	override fun equals(other: Any?): Boolean {
		return this === other || other is User && id == other.id
	}

	override fun hashCode(): Int {
		return Objects.hashCode(id)
	}

	override fun toString(): String {
		return "$displayName|$email"
	}
}
