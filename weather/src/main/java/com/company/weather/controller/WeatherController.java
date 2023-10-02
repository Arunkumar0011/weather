package com.company.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.company.weather.entity.WeatherData;
import com.company.weather.service.WeatherServiceI;

@RestController
public class WeatherController {

	@Autowired
	WeatherServiceI weatherServiceI;

	@GetMapping("/{location}")
	public WeatherData getWeather(@PathVariable String location) {
		return weatherServiceI.getWeatherForLocation(location);
	}

}
