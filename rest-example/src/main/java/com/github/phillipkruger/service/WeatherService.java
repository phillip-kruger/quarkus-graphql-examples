package com.github.phillipkruger.service;

import com.github.phillipkruger.model.Weather;
import io.quarkus.logging.Log;
import java.util.concurrent.CompletionStage;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * Service that return the exchange rate against a certain base
 *
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@ApplicationScoped
public class WeatherService {

    private static final Map<String, Weather> weatherMap = new HashMap<>();
    private static final Random random = new Random();

    static {
        // Dummy data
        weatherMap.put("New York", new Weather("Sunny", 25.0, 20.0, 30.0));
        weatherMap.put("London", new Weather("Cloudy", 18.0, 15.0, 22.0));
        weatherMap.put("Tokyo", new Weather("Rainy", 22.0, 20.0, 25.0));
        weatherMap.put("Sydney", new Weather("Windy", 28.0, 24.0, 33.0));
        weatherMap.put("Mumbai", new Weather("Humid", 30.0, 27.0, 34.0));
    }
    
    public CompletionStage<Weather> getFutureWeather(String city) {
        return CompletableFuture.supplyAsync(() -> 
            getWeather(city)
        );
    }
    
    public Weather getWeather(String city){
        Log.info(">>>>> Getting weather for " + city);
        Weather w = weatherMap.computeIfAbsent(city, this::generateRandomWeather);
        Log.info("<<<<< Got weather for " + city);
        return w;
    }
    
    private Weather generateRandomWeather(String city) {
        String[] descriptions = {"Sunny", "Cloudy", "Rainy", "Windy", "Humid", "Stormy", "Snowy", "Foggy"};
        String description = descriptions[random.nextInt(descriptions.length)];
        double currentTemp = round(random.nextDouble() * 40 - 10);
        double minTemp = round(currentTemp - random.nextDouble() * 5);
        double maxTemp = round(currentTemp + random.nextDouble() * 5);

        return new Weather(description, currentTemp, minTemp, maxTemp);
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
