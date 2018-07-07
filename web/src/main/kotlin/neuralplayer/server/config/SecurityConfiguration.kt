package neuralplayer.server.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@EnableWebFluxSecurity
class SecurityConfiguration {

	@Bean
	fun springWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
		return http
				.authorizeExchange()
				.anyExchange().permitAll()
				//.anyExchange().authenticated()
				.and()
				.httpBasic()
				.and()
				.csrf().disable()
				.build()
	}
}
