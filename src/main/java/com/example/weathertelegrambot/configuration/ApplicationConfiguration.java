package com.example.weathertelegrambot.configuration;

import com.squareup.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(JpaConfiguration.class)
@Configuration
public class ApplicationConfiguration {
    @Bean
    protected OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}
