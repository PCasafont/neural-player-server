package neuralplayer.server.repository

import neuralplayer.server.model.Playlist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlaylistRepository : JpaRepository<Playlist, Long> {

	fun findByName(name: String): Playlist?
}
