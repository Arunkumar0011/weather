package com.company.weather.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.weather.entity.WeatherData;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherData, Long>{
	
    WeatherData findByLocationAndDate(String date, LocalDate todayDate);

}
