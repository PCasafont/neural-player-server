package neuralplayer.server.repository

import neuralplayer.server.model.User
import neuralplayer.server.model.UserTrack
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserTrackRepository : JpaRepository<UserTrack, UserTrack.PK> {

	fun findByUserAndTrackId(user: User, trackId: Long): UserTrack?
}
