package com.example.weathertelegrambot.bot.models;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Getter
@Entity(name = "users")
public class UserEntity {
    @Id
    private Long id; // telegram id

    @Column(name = "location_latitude")
    private Double locationLatitude;

    @Enumerated(EnumType.STRING)
    private UserState state;

    @Column(name = "location_longitude")
    private Double locationLongitude;
}