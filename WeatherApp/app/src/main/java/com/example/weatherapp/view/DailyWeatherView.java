package com.example.weatherapp.viewmodel;

import com.example.weatherapp.model.DailyWeather;

import java.util.ArrayList;
import java.util.List;

public class DailyWeatherViewModel {
    private List<Data> data;

    public static class Data {
        private String day;
        private String temperatureHigh;
        private String temperatureLow;
        private String icon;

        public Data(String day, String temperatureHigh, String temperatureLow, String icon) {
            this.day = day;
            this.temperatureHigh = temperatureHigh;
            this.temperatureLow = temperatureLow;
            this.icon = icon;
        }

        // Add getters here
    }

    public DailyWeatherViewModel(DailyWeather model) {
        // Implement the logic to convert DailyWeather to DailyWeatherViewModel
    }

    public List<Data> getData() {
        return data;
    }
}
