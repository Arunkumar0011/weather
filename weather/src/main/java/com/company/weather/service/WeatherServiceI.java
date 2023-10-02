package com.company.weather.service;

import com.company.weather.entity.WeatherData;

public interface WeatherServiceI {

	WeatherData getWeatherForLocation(String location);

}
