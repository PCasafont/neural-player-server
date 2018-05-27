package neuralplayer.server.converter

import neuralplayer.server.model.Role
import javax.persistence.AttributeConverter

class JpaRoleListConverter : AttributeConverter<Set<Role>, String> {

	override fun convertToDatabaseColumn(roles: Set<Role>): String {
		return roles.joinToString(separator = ",")
	}

	override fun convertToEntityAttribute(dbData: String): Set<Role> {
		return dbData.split(",").map { Role.valueOf(it) }.toSet()
	}
}
