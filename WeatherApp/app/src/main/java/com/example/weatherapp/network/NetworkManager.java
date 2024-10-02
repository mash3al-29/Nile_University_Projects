package com.example.weatherapp.network;

import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.DailyWeather;
import com.example.weatherapp.model.Weather;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkManager {
    private DarkSkyAPIClient client;
    public MutableLiveData<CurrentWeather> currentWeather;
    public MutableLiveData<DailyWeather> dailyWeather;

    public NetworkManager() {
        client = new DarkSkyAPIClient();
        currentWeather = new MutableLiveData<>(new CurrentWeather());
        dailyWeather = new MutableLiveData<>(new DailyWeather());

        fetchWeather();
    }

    private void fetchWeather() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                String json = client.getWeatherData(Coordinate.newYorkCity());
                Weather weather = new Gson().fromJson(json, Weather.class);
                currentWeather.postValue(weather.currently);
                dailyWeather.postValue(weather.daily);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
