package com.github.phillipkruger.endpoint;

import com.github.phillipkruger.model.Weather;
import com.github.phillipkruger.service.WeatherService;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

/**
 * Travel planning GraphQL endpoint
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@GraphQLApi
public class TravelEndpoint {

    @Inject
    WeatherService weatherService;
    
    @Query
    public CompletionStage<Weather> getWeather(String city){
        return weatherService.getWeather(city);
    }
    
}
