/*
 * This file is generated by jOOQ.
 */
package neuralplayer.server.model.jooq.tables.pojos

import javax.annotation.Generated
import java.io.Serializable

/**
 * This class is generated by jOOQ.
 */
@Generated(value = ["http://www.jooq.org", "jOOQ version:3.11.2"], comments = "This class is generated by jOOQ")
data class Playlist(var id: Long,
					var name: String,
					var creatorId: Long) : Serializable {

	companion object {

		private const val serialVersionUID: Long = -874240768
	}
}