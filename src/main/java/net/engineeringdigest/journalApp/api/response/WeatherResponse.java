package net.engineeringdigest.journalApp.api.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;



	// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
	// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
	/* ObjectMapper om = new ObjectMapper();
	Root root = om.readValue(myJsonString, Root.class); */
	
	public class WeatherResponse{
	
	    public Current getCurrent() {
			return current;
		}


		public void setCurrent(Current current) {
			this.current = current;
		}


		private Current current;
	    
	    
	public class Current{
	    
	    	public int getTemperature() {
			return temperature;
		}


		public void setTemperature(int temperature) {
			this.temperature = temperature;
		}


		public List<String> getWeatherDescriptions() {
			return weatherDescriptions;
		}


		public void setWeatherDescriptions(List<String> weatherDescriptions) {
			this.weatherDescriptions = weatherDescriptions;
		}


		public int getFeelslike() {
			return feelslike;
		}


		public void setFeelslike(int feelslike) {
			this.feelslike = feelslike;
		}


			private int temperature;
	    	
	    	
	    	@JsonProperty("weather_descriptions")
	    	private List<String> weatherDescriptions;
	    	
	    	
	    	private int feelslike;
	    	
		}

	}
	 
	
