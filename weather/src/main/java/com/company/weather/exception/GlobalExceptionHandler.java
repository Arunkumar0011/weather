package com.company.weather.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = WeatherApiException.class)
	public ResponseEntity<CommonException> InvalidQueryException() {
		LocalDateTime localDateTime = LocalDateTime.now();
		CommonException CommonException = new CommonException("An exception occurred while attempting to fetch weather data from the OpenWeather API",
				localDateTime);
		return new ResponseEntity<CommonException>(CommonException, HttpStatus.BAD_REQUEST);
	}

}
