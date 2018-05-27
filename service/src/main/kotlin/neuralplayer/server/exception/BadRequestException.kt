package neuralplayer.server.exception

class BadRequestException : RuntimeException {

	constructor() : super("Bad Request")

	constructor(message: String, cause: Throwable) : super(message, cause)
}
