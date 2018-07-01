/*
 * This file is generated by jOOQ.
 */
package neuralplayer.server.model.jooq.tables.daos

import neuralplayer.server.model.jooq.tables.pojos.PlaylistTrack
import neuralplayer.server.model.jooq.tables.records.PlaylistTrackRecord
import org.jooq.Configuration
import org.jooq.Record2
import org.jooq.impl.DAOImpl

import javax.annotation.Generated

/**
 * This class is generated by jOOQ.
 */
@Generated(value = ["http://www.jooq.org", "jOOQ version:3.11.2"], comments = "This class is generated by jOOQ")
class PlaylistTrackDao : DAOImpl<PlaylistTrackRecord, PlaylistTrack, Record2<Long, Long>> {

	/**
	 * Create a new PlaylistTrackDao without any configuration
	 */
	constructor() : super(neuralplayer.server.model.jooq.tables.PlaylistTrack.PLAYLIST_TRACK, PlaylistTrack::class.java)

	/**
	 * Create a new PlaylistTrackDao with an attached configuration
	 */
	constructor(configuration: Configuration) : super(neuralplayer.server.model.jooq.tables.PlaylistTrack.PLAYLIST_TRACK, PlaylistTrack::class.java, configuration)

	/**
	 * {@inheritDoc}
	 */
	override fun getId(`object`: PlaylistTrack): Record2<Long, Long> {
		return compositeKeyRecord(`object`.playlistId, `object`.trackId)
	}

	/**
	 * Fetch records that have `playlist_id IN (values)`
	 */
	fun fetchByPlaylistId(vararg values: Long): List<PlaylistTrack> {
		return fetch(neuralplayer.server.model.jooq.tables.PlaylistTrack.PLAYLIST_TRACK.PLAYLIST_ID, *values.toTypedArray())
	}

	/**
	 * Fetch records that have `track_id IN (values)`
	 */
	fun fetchByTrackId(vararg values: Long): List<PlaylistTrack> {
		return fetch(neuralplayer.server.model.jooq.tables.PlaylistTrack.PLAYLIST_TRACK.TRACK_ID, *values.toTypedArray())
	}
}
