package com.example.weathertelegrambot.bot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Getter
@Entity(name = "user_location")
public class UserLocationEntity {
    @Id
    private Long id; // telegram id

    private Double latitude;

    private Double longitude;
}