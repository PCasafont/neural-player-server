package neuralplayer.server.web

import neuralplayer.server.exception.BadRequestException
import neuralplayer.server.exception.UnauthorizedException
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.codec.HttpMessageWriter
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.result.view.ViewResolver
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Mono
import javax.persistence.EntityNotFoundException

@Component
@Order(-2)
class ExceptionHandler : WebExceptionHandler {

	private val logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

	override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
		return handle(ex).flatMap {
			it.writeTo(exchange, HandlerStrategiesResponseContext(HandlerStrategies.withDefaults()))
		}.flatMap {
			Mono.empty<Void>()
		}
	}

	fun handle(throwable: Throwable): Mono<ServerResponse> {
		return when (throwable) {
			is ResponseStatusException -> {
				createResponse(throwable.status, "${throwable.message}")
			}
			is UnauthorizedException -> {
				createResponse(HttpStatus.UNAUTHORIZED, "Unauthorized${throwable.message?.let { ", details: $it" } ?: ""}")
			}
			is EntityNotFoundException -> {
				createResponse(HttpStatus.NOT_FOUND, "Entity not found${throwable.message?.let { ", details: $it" } ?: ""}")
			}
			is BadRequestException -> {
				createResponse(HttpStatus.BAD_REQUEST, "Bad request${throwable.message?.let { ", details: $it" } ?: ""}")
			}
			else -> {
				logger.warn("Unhandled exception", throwable)
				createResponse(HttpStatus.INTERNAL_SERVER_ERROR,
						"Unhandled exception [${throwable.javaClass.simpleName}]: ${throwable.message}" )
			}
		}
	}

	fun createResponse(httpStatus: HttpStatus, message: String): Mono<ServerResponse> {
		return ServerResponse.status(httpStatus).syncBody(WebError(httpStatus.reasonPhrase, message))
	}

	private class WebError(val code: String, val message: String = "")

	private class HandlerStrategiesResponseContext(val strategies: HandlerStrategies) : ServerResponse.Context {
		override fun messageWriters(): MutableList<HttpMessageWriter<*>> = this.strategies.messageWriters()
		override fun viewResolvers(): MutableList<ViewResolver> = this.strategies.viewResolvers()
	}
}
