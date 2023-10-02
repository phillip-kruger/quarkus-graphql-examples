package com.github.phillipkruger.endpoint;

import com.github.phillipkruger.service.ExchangeRateService;
import com.github.phillipkruger.model.CurencyCode;
import com.github.phillipkruger.model.ExchangeRate;
import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.service.PersonService;
import io.quarkus.cache.CacheResult;
import java.util.List;
import java.util.concurrent.ExecutionException;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;

/**
 * Person GraphQL endpoint
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@GraphQLApi
public class PersonEndpoint {

    @Inject
    PersonService personService;
    
    @Inject
    ExchangeRateService exchangeRateService;
    
    @Query
    public List<Person> getPeople(){
        return personService.getAllPeople();
    }
    
    @Query
    public Person getPerson(int id){
        return personService.getPerson(id);
    }
    
    @CacheResult(cacheName = "exchange-rate-cache")
    public ExchangeRate getExchangeRate(@Source Person person, CurencyCode against){
        try {
            ExchangeRate exchangeRate = exchangeRateService.getFutureExchangeRate(against,person.curencyCode).toCompletableFuture().get();
            System.err.println("exchangeRate = " + exchangeRate);
            return exchangeRate;
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }
}
