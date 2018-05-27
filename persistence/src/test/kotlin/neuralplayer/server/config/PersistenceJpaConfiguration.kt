package neuralplayer.server.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EntityScan("neuralplayer.server.model")
@EnableJpaRepositories("neuralplayer.server.repository")
class PersistenceJpaConfiguration
