package io.pivotal;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Profile("production-worker")
@EnableScheduling
public class ProductionWorkerConfig {
}