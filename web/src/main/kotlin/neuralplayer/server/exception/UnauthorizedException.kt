package neuralplayer.server.exception

class UnauthorizedException : RuntimeException {

	constructor() : super("Unauthorized")

	constructor(message: String, cause: Throwable) : super(message, cause)
}
