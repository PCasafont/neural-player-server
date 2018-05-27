package neuralplayer.server.exception

class EntityNotFoundException : RuntimeException {

	constructor() : super("Entity not found")

	constructor(message: String, cause: Throwable) : super(message, cause)
}
