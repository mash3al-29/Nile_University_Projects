package com.example.weatherapp.network;

import com.example.weatherapp.model.Coordinate;
import com.example.weatherapp.model.Weather;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DarkSkyAPIClient {
    private static final String DARK_SKY_API_KEY = "YOURAPIKEY";
    private static final String BASE_URL = "https://api.darksky.net/forecast/" + DARK_SKY_API_KEY + "/";

    private OkHttpClient client;

    public DarkSkyAPIClient() {
        this.client = new OkHttpClient();
    }

    public String getWeatherData(Coordinate coordinate) throws IOException {
        String url = BASE_URL + coordinate.toString();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
