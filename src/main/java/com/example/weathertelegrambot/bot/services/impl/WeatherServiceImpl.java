package com.example.weathertelegrambot.bot.services.impl;

import com.example.weathertelegrambot.bot.models.Weather;
import com.example.weathertelegrambot.bot.services.WeatherService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
@Log4j2
public class WeatherServiceImpl implements WeatherService {
    private OkHttpClient client;

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${rapid.api.host}")
    private String apiHost;

    @Value("${rapid.api.key}")
    private String apiKey;

    public WeatherServiceImpl(OkHttpClient client) {
        this.client = client;
    }


    @Override
    public Weather getWeather(double latitude, double longitude) {
        try {
            String rawJson = sendRequestToApi(latitude, longitude);
            return parseJsonFromApi(rawJson);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public InputStream getWeatherIcon(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        return this.client.newCall(request).execute().body().byteStream();
    }

    private String sendRequestToApi(double latitude, double longitude) throws IOException {
        String locationInStringFormat = latitude + "," + longitude;
        String formattedUrl = String.format(weatherApiUrl + "?q=%s", locationInStringFormat);
        Request request = new Request.Builder()
                .url(formattedUrl)
                .addHeader("X-RapidAPI-Key", this.apiKey)
                .addHeader("X-RapidAPI-Host", this.apiHost)
                .build();
        Response resp = this.client.newCall(request).execute();
        try (ResponseBody responseBody = resp.body()) {
            return responseBody.string();
        }
    }

    private Weather parseJsonFromApi(String json) {
        Weather weather = new Weather();

        JsonObject parsedJson = JsonParser.parseString(json).getAsJsonObject();
        JsonObject locationJson = parsedJson.get("location").getAsJsonObject();
        JsonObject weatherJson = parsedJson.get("current").getAsJsonObject();
        JsonObject weatherConditionJson = weatherJson.get("condition").getAsJsonObject();

        weather.setLocationCountry(locationJson.get("country").getAsString());
        weather.setLocationRegion(locationJson.get("region").getAsString());
        weather.setLocationName(locationJson.get("name").getAsString());

        weather.setTemperature(weatherJson.get("temp_c").getAsDouble());
        weather.setConditionText(weatherConditionJson.get("text").getAsString());
        weather.setConditionIcon("http:" + weatherConditionJson.get("icon").getAsString());

        return weather;
    }
}
