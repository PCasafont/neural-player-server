/*
 * This file is generated by jOOQ.
 */
package neuralplayer.server.model.jooq.tables.daos

import neuralplayer.server.model.jooq.tables.pojos.UserTrack
import neuralplayer.server.model.jooq.tables.records.UserTrackRecord
import org.jooq.Configuration
import org.jooq.Record2
import org.jooq.impl.DAOImpl

import javax.annotation.Generated

/**
 * This class is generated by jOOQ.
 */
@Generated(value = ["http://www.jooq.org", "jOOQ version:3.11.2"], comments = "This class is generated by jOOQ")
class UserTrackDao : DAOImpl<UserTrackRecord, UserTrack, Record2<Long, Long>> {

	/**
	 * Create a new UserTrackDao without any configuration
	 */
	constructor() : super(neuralplayer.server.model.jooq.tables.UserTrack.USER_TRACK, UserTrack::class.java)

	/**
	 * Create a new UserTrackDao with an attached configuration
	 */
	constructor(configuration: Configuration) : super(neuralplayer.server.model.jooq.tables.UserTrack.USER_TRACK, UserTrack::class.java, configuration)

	/**
	 * {@inheritDoc}
	 */
	override fun getId(`object`: UserTrack): Record2<Long, Long> {
		return compositeKeyRecord(`object`.userId, `object`.trackId)
	}

	/**
	 * Fetch records that have `user_id IN (values)`
	 */
	fun fetchByUserId(vararg values: Long): List<UserTrack> {
		return fetch(neuralplayer.server.model.jooq.tables.UserTrack.USER_TRACK.USER_ID, *values.toTypedArray())
	}

	/**
	 * Fetch records that have `track_id IN (values)`
	 */
	fun fetchByTrackId(vararg values: Long): List<UserTrack> {
		return fetch(neuralplayer.server.model.jooq.tables.UserTrack.USER_TRACK.TRACK_ID, *values.toTypedArray())
	}

	/**
	 * Fetch records that have `preference_score IN (values)`
	 */
	fun fetchByPreferenceScore(vararg values: Double): List<UserTrack> {
		return fetch(neuralplayer.server.model.jooq.tables.UserTrack.USER_TRACK.PREFERENCE_SCORE, *values.toTypedArray())
	}
}