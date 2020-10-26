package com.github.phillipkruger.service;

import com.github.phillipkruger.model.Weather;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Return the weather forecast for a certain city
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@ApplicationScoped
public class WeatherService {

    @Inject @ConfigProperty(name = "weather.api.key")
    private String apiKey;
    
    @Inject
    Vertx vertx;
    
    public CompletableFuture<Weather> getWeather(String city) {
        System.err.println(">>> Getting weather for " + city);
        Future<Weather> future = Future.future();
        WebClient client = WebClient.create(vertx);
        
        client.get(80, "api.openweathermap.org", "/data/2.5/weather")
                .addQueryParam("q", city.trim())
                .addQueryParam("appid", apiKey.trim())
                .addQueryParam("units","metric")
                .send((var e) -> {
                    if(e.succeeded()){
                        HttpResponse<Buffer> response = e.result();
                        JsonObject jsonResponse = response.bodyAsJsonObject();
                        
                        JsonObject weather = jsonResponse.getJsonArray("weather").getJsonObject(0);
                        String description = weather.getString("description");
                        JsonObject main = jsonResponse.getJsonObject("main");
                        double current = main.getDouble("temp");
                        double min = main.getDouble("temp_min");
                        double max = main.getDouble("temp_max");
                        double humidity = main.getDouble("humidity");
                        JsonObject wind = jsonResponse.getJsonObject("wind");
                        double windSpeed = wind.getDouble("speed");
                        
                        future.complete(new Weather(description, current, max, min, windSpeed, humidity));
                        System.err.println("<<< Got weather for " + city);
                    }else if(e.failed()){
                        future.failed();
                    }
                });
        
        return future.toCompletionStage().toCompletableFuture();
    }
}
