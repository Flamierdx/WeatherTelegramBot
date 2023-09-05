package com.example.weathertelegrambot.bot.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Weather {
    private String locationName;
    private String locationRegion;
    private String locationCountry;
    private double temperature;
    private String conditionText;
    private String conditionIcon;
}
