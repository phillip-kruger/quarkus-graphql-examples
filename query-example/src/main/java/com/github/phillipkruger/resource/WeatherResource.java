package com.github.phillipkruger.resource;

import com.github.phillipkruger.model.Weather;
import com.github.phillipkruger.service.WeatherService;
import jakarta.inject.Inject;
import java.util.concurrent.CompletionStage;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

/**
 * Weather GraphQL endpoint
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@GraphQLApi
public class WeatherResource {

    @Inject
    WeatherService weatherService;
    
//    @Query
//    public Weather getWeather(String city){
//        return weatherService.getWeather(city);
//    }
    
    @Query
    public CompletionStage<Weather> getWeather(String city){
        return weatherService.getFutureWeather(city);
    }
}
