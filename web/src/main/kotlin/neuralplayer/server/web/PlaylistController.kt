package neuralplayer.server.web

import neuralplayer.server.dto.PlaylistDto
import neuralplayer.server.mapper.PlaylistMapper
import neuralplayer.server.service.PlaylistService
import neuralplayer.server.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.security.Principal
import javax.validation.Valid





@RestController
@RequestMapping("playlists")
class PlaylistController(private val playlistService: PlaylistService,
						 private val userService: UserService,
						 private val playlistMapper: PlaylistMapper) {

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	fun getAll(): Flux<PlaylistDto> {
		return Flux.fromIterable(playlistService.findAll().map { playlistMapper.createPlaylistDto(it) })
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	fun create(@RequestBody @Valid playlistDto: PlaylistDto,
			   principal: Principal): Mono<PlaylistDto> {
		return Mono.just(playlistMapper.createPlaylistDto(playlistService.create(playlistDto, userService.findByUsername(principal.name)!!)))
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	fun update(@PathVariable id: Long,
			   @RequestBody @Valid playlistDto: PlaylistDto,
			   principal: Principal): Mono<PlaylistDto> {
		return Mono.just(playlistMapper.createPlaylistDto(playlistService.update(id, playlistDto)))
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	fun delete(@PathVariable id: Long) {
		return playlistService.delete(id)
	}
}
