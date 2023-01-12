package br.com.lucasgomes.testautomation.testscheduler.configurations;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("br.com.lucasgomes.repository.repositories")
@EntityScan("br.com.lucasgomes.testautomation.model")
public class JpaConfigurations {

}
