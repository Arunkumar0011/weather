package com.company.weather.serviceimpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.weather.entity.WeatherData;
import com.company.weather.repository.WeatherRepository;
import com.company.weather.service.WeatherServiceI;

@Service
public class WeatherServiceImpl implements WeatherServiceI {

	@Autowired
	WeatherServiceAPI weatherServiceAPI;

	@Autowired
	WeatherRepository weatherRepository;

	/**
	 * This method fetch the data based location if there it will process from
	 * database or connect the third party service and return the data
	 */
	public WeatherData getWeatherForLocation(String locationName) {
		// Check if weather data exists in the database
		LocalDate todayDate = LocalDate.now();
		WeatherData exsWeatherDate = weatherRepository.findByLocationAndDate(locationName, todayDate);

		if (exsWeatherDate == null) {
			// Data not in the database, fetch from the API
			WeatherData newWeatherData = weatherServiceAPI.fetchWeatherData(locationName);
			return newWeatherData;
		} else {
			// Data already in the database, fetch it
			return exsWeatherDate;
		}

	}
}
