package neuralplayer.server.repository

import neuralplayer.server.model.TrackBasicData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TrackBasicDataRepository : JpaRepository<TrackBasicData, Long> {
	fun findByTitleAndArtistAndAlbum(title: String, artist: String?, album: String?): TrackBasicData?
}
