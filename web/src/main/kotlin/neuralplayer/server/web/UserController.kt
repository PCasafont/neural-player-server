package neuralplayer.server.web

import neuralplayer.server.dto.UserDto
import neuralplayer.server.mapper.UserMapper
import neuralplayer.server.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import javax.validation.Valid

@RestController
@RequestMapping("users")
class UserController(private val userService: UserService,
					 private val userMapper: UserMapper) {

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	fun getAll(): Flux<UserDto> {
		return Flux.fromIterable(userService.findAll().map { userMapper.createUserDto(it) })
	}

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	fun create(@RequestBody @Valid userDto: UserDto) {
		userService.create(userDto)
	}
}
