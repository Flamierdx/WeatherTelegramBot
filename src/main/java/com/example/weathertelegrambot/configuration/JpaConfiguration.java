package com.example.weathertelegrambot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = "com.example.weathertelegrambot.bot.dao")
@Configuration
public class JpaConfiguration {
}
