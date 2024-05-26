package ru.taskManager.taskManager

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaRepositories(basePackages = ["ru.taskManager.taskManager.repository"])
@TestPropertySource("/application.yml")
@EnableTransactionManagement
class TestConfig