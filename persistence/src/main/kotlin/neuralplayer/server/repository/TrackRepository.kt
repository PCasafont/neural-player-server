package neuralplayer.server.repository

import neuralplayer.server.model.Track
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TrackRepository : JpaRepository<Track, Long>
