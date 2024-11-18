package net.engineeringdigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;

@Service
public class WeatherService {
    
	@Value("${Weather_APIKEY}")
	private  String apiKey ;
	private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
	
	
	@Autowired
	private RestTemplate restTemple;
	
	@Autowired
	private RedisService redisService;
	
	public WeatherResponse getWeather(String city) {
		WeatherResponse weatherResponse = redisService.get("Weather_of_" + city, WeatherResponse.class);
		if(weatherResponse!=null) {
			return weatherResponse;
		}
		else {
			String finalApi = API.replace("CITY", city).replace("API_KEY", apiKey);
			ResponseEntity<WeatherResponse> response = restTemple.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class );
			WeatherResponse body = response.getBody();
			
			if(body!=null) {
				redisService.set("Weather_of_" + city, body, 300l);
			}
			return body;
		}
		
		
	}
}
