package com.example.weathertelegrambot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(JpaConfiguration.class)
@Configuration
public class ApplicationConfiguration {}
