package com.github.phillipkruger.endpoint;

import com.github.phillipkruger.service.ExchangeRateService;
import com.github.phillipkruger.model.CurencyCode;
import com.github.phillipkruger.model.ExchangeRate;
import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.service.PersonService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;
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
    public CompletableFuture<Person> getPerson(int id){
        return CompletableFuture.supplyAsync(() -> personService.getPerson(id));
    }
    
    public CompletableFuture<ExchangeRate> getExchangeRate(@Source Person person, CurencyCode against){
        return CompletableFuture.supplyAsync(() -> exchangeRateService.getExchangeRate(against,person.curencyCode));    
    }
}
