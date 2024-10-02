package com.example.weatherapp.model;

import java.util.ArrayList;
import java.util.List;

public class DailyWeather {
    public List<Data> data;

    public static class Data {
        public double time;
        public double temperatureHigh;
        public double temperatureLow;
        public String icon;
    }

    public DailyWeather() {
        this.data = new ArrayList<>();
    }
}
