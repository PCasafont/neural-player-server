/*
 * This file is generated by jOOQ.
 */
package neuralplayer.server.model.jooq.tables.records

import neuralplayer.server.model.jooq.tables.Playlist
import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record3
import org.jooq.Row3
import org.jooq.impl.UpdatableRecordImpl

import javax.annotation.Generated

/**
 * This class is generated by jOOQ.
 */
@Generated(value = ["http://www.jooq.org", "jOOQ version:3.11.2"], comments = "This class is generated by jOOQ")
@SuppressWarnings( "unchecked")
class PlaylistRecord : UpdatableRecordImpl<PlaylistRecord>, Record3<Long, String, Long> {

	/**
	 * Getter for `neural_player.playlist.id`.
	 */
	/**
	 * Setter for `neural_player.playlist.id`.
	 */
	var id: Long
		get() = get(0) as Long
		set(value) = set(0, value)

	/**
	 * Getter for `neural_player.playlist.name`.
	 */
	/**
	 * Setter for `neural_player.playlist.name`.
	 */
	var name: String
		get() = get(1) as String
		set(value) = set(1, value)

	/**
	 * Getter for `neural_player.playlist.creator_id`.
	 */
	/**
	 * Setter for `neural_player.playlist.creator_id`.
	 */
	var creatorId: Long
		get() = get(2) as Long
		set(value) = set(2, value)

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	override fun key(): Record1<Long> {
		return super.key() as Record1<Long>
	}

	// -------------------------------------------------------------------------
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	override fun fieldsRow(): Row3<Long, String, Long> {
		return super.fieldsRow() as Row3<Long, String, Long>
	}

	/**
	 * {@inheritDoc}
	 */
	override fun valuesRow(): Row3<Long, String, Long> {
		return super.valuesRow() as Row3<Long, String, Long>
	}

	/**
	 * {@inheritDoc}
	 */
	override fun field1(): Field<Long> {
		return Playlist.PLAYLIST.ID
	}

	/**
	 * {@inheritDoc}
	 */
	override fun field2(): Field<String> {
		return Playlist.PLAYLIST.NAME
	}

	/**
	 * {@inheritDoc}
	 */
	override fun field3(): Field<Long> {
		return Playlist.PLAYLIST.CREATOR_ID
	}

	/**
	 * {@inheritDoc}
	 */
	override fun component1(): Long? {
		return id
	}

	/**
	 * {@inheritDoc}
	 */
	override fun component2(): String {
		return name
	}

	/**
	 * {@inheritDoc}
	 */
	override fun component3(): Long {
		return creatorId
	}

	/**
	 * {@inheritDoc}
	 */
	override fun value1(): Long {
		return id
	}

	/**
	 * {@inheritDoc}
	 */
	override fun value2(): String {
		return name
	}

	/**
	 * {@inheritDoc}
	 */
	override fun value3(): Long {
		return creatorId
	}

	/**
	 * {@inheritDoc}
	 */
	override fun value1(value: Long): PlaylistRecord {
		id = value
		return this
	}

	/**
	 * {@inheritDoc}
	 */
	override fun value2(value: String): PlaylistRecord {
		name = value
		return this
	}

	/**
	 * {@inheritDoc}
	 */
	override fun value3(value: Long): PlaylistRecord {
		creatorId = value
		return this
	}

	/**
	 * {@inheritDoc}
	 */
	override fun values(value1: Long, value2: String, value3: Long): PlaylistRecord {
		value1(value1)
		value2(value2)
		value3(value3)
		return this
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PlaylistRecord
	 */
	constructor() : super(Playlist.PLAYLIST)

	/**
	 * Create a detached, initialised PlaylistRecord
	 */
	constructor(id: Long, name: String, creatorId: Long) : super(Playlist.PLAYLIST) {

		set(0, id)
		set(1, name)
		set(2, creatorId)
	}

	companion object {

		private val serialVersionUID: Long = -2123861342
	}
}
