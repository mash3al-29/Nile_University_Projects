package com.example.weatherapp.model;

public class Weather {
    public CurrentWeather currently;
    public DailyWeather daily;

    public Weather() {
        this.currently = new CurrentWeather();
        this.daily = new DailyWeather();
    }
}
