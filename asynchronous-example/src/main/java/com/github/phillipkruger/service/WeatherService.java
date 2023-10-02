package com.github.phillipkruger.service;

import com.github.phillipkruger.model.Weather;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Return the weather forecast for a certain city
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@ApplicationScoped
public class WeatherService {

    @Inject @ConfigProperty(name = "weather.api.key")
    String apiKey;
    
    @Inject
    Vertx vertx;
    
    public CompletionStage<Weather> getWeather(String city) {
        System.err.println(">>> Getting weather for " + city);
        WebClient client = WebClient.create(vertx);
        
        HttpRequest<Buffer> httpRequest = client.get(80, "api.openweathermap.org", "/data/2.5/weather")
                .addQueryParam("q", city.trim())
                .addQueryParam("appid", apiKey.trim())
                .addQueryParam("units","metric");
        
        Future<HttpResponse<Buffer>> futureResponse = httpRequest.send();

        Function<AsyncResult<HttpResponse<Buffer>>,Future<Weather>> f = new Function<>(){
            public Future<Weather> apply(AsyncResult<HttpResponse<Buffer>> futureResponse){
                HttpResponse<Buffer> result = futureResponse.result();
                JsonObject jsonResponse = result.bodyAsJsonObject();
                JsonObject weather = jsonResponse.getJsonArray("weather").getJsonObject(0);
                String description = weather.getString("description");
                JsonObject main = jsonResponse.getJsonObject("main");
                double current = main.getDouble("temp");
                double min = main.getDouble("temp_min");
                double max = main.getDouble("temp_max");
                double humidity = main.getDouble("humidity");
                JsonObject wind = jsonResponse.getJsonObject("wind");
                double windSpeed = wind.getDouble("speed");

                Weather w = new Weather(description, current, max, min, windSpeed, humidity);
                return Future.succeededFuture(w);
            }
        };
        return futureResponse.transform(f).toCompletionStage();
        
    }
}
