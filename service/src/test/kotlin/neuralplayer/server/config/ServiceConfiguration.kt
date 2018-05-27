package neuralplayer.server.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(PasswordEncoderConfiguration::class)
@ComponentScan("neuralplayer.server.mapper", "neuralplayer.server.service")
class ServiceConfiguration
