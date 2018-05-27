package neuralplayer.server.web

import neuralplayer.server.dto.TrackDto
import neuralplayer.server.mapper.TrackMapper
import neuralplayer.server.model.User
import neuralplayer.server.service.TrackService
import neuralplayer.server.service.UserService
import neuralplayer.server.util.WebPreconditions
import org.springframework.http.HttpStatus
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("tracks")
class TrackController(private val trackService: TrackService,
					  private val userService: UserService,
					  private val trackMapper: TrackMapper) {

	fun user(principal: Principal?): User {
		return WebPreconditions.checkUser(userService.findByUsername(WebPreconditions.checkUser(principal).name))
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	fun getAll(principal: Principal?): Flux<TrackDto> {
		val user = user(principal)
		return Flux.fromIterable(trackService.findAll().map { trackMapper.createTrackDto(it, user) })
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	fun create(@RequestPart("track") @Valid trackDto: TrackDto, @RequestPart("file") filePart: FilePart): Mono<TrackDto> {
		return trackService.create(trackDto, filePart).map { trackMapper.createTrackDto(it) }
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	fun update(@PathVariable id: Long,
			   @RequestBody @Valid trackDto: TrackDto,
			   principal: Principal?): Mono<TrackDto> {
		val user = user(principal)
		val track = trackService.update(id, trackDto, user)
		return Mono.just(trackMapper.createTrackDto(track, user))
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	fun delete(@PathVariable id: Long) {
		return trackService.delete(id)
	}

	@GetMapping("/{id}/download")
	@ResponseStatus(HttpStatus.OK)
	fun download(@PathVariable("id") id: Long): Mono<ByteArray> {
		return trackService.getFile(id).toMono()
	}
}
