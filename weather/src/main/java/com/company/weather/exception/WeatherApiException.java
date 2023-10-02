package com.company.weather.exception;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class WeatherApiException extends RuntimeException {

	public WeatherApiException(String msg) {
		super(msg);
	}
}
