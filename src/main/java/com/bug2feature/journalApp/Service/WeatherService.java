package com.bug2feature.journalApp.Service;

import com.bug2feature.journalApp.api.response.WeatherApiResponse;
import com.bug2feature.journalApp.cache.AppCache;
import com.bug2feature.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;
    @Autowired
    AppCache appCache;

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RedisService redisService;
    public WeatherApiResponse getWeather(String city){

        WeatherApiResponse weatherResponse = redisService.get("weather_of_"+city,WeatherApiResponse.class);
        if(weatherResponse!=null){
            return weatherResponse;
        }
        else{
            String finalApi = appCache.appCache.get(AppCache.keys.weather_api.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY,apiKey);
            ResponseEntity<WeatherApiResponse> weatherApiResponse=restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherApiResponse.class);
            WeatherApiResponse body = weatherApiResponse.getBody();
            if(body!=null){
                redisService.set("weather_of_"+city,body,300l);
            }
            return body;
        }

    }

}
