package com.company.weather.serviceimpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.company.weather.entity.WeatherData;
import com.company.weather.exception.WeatherApiException;
import com.company.weather.repository.WeatherRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherServiceAPI {

	@Autowired
	WeatherRepository weatherRepository;

	 @Value("${api.key}")
	 private String API_KEY;

	 @Value("${api.base.url}")
	 private String API_BASE_URL;
	 
	private final RestTemplate restTemplate = new RestTemplate();

	/**
	 * This method connect the third party service and fetch the data from that service and store 
	 * our database and return the response
	 * 
	 * @param locationName
	 * @return WeatherData
	 * @exception WeatherApiException - If any exception occured in third party api's catch and process
	 */
	public WeatherData fetchWeatherData(String locationName) {
		String apiUrl = API_BASE_URL + "/weather?q=" + locationName + "&appid=" + API_KEY;

		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);
		} catch (RestClientException e) {
			throw new WeatherApiException("An exception occurred while attempting to fetch weather data from the OpenWeather API");
		}

		WeatherData weatherData = new WeatherData();
		if (response.getStatusCode() == HttpStatus.OK) {
			String responseBody = response.getBody();
			ObjectMapper objectMapper = new ObjectMapper();
			try {

				JsonNode jsonBody = objectMapper.readTree(responseBody);

				double temperature = jsonBody.path("main").path("temp").asDouble();
				double humidity = jsonBody.path("main").path("humidity").asDouble();
				double windSpeed = jsonBody.path("wind").path("speed").asDouble();
				String description = jsonBody.get("weather").get(0).get("description").asText();

				weatherData.setTemperature(temperature);
				weatherData.setHumidity(humidity);
				weatherData.setWindSpeed(windSpeed);
				weatherData.setDescription(description);
				weatherData.setLocation(locationName);
				weatherData.setDate(LocalDate.now());
				weatherRepository.save(weatherData);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			throw new WeatherApiException("An exception occurred while attempting to fetch weather data from the OpenWeather API");
		}
		return weatherData;
	}

}
