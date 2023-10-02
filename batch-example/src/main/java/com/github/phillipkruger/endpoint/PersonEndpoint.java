package com.github.phillipkruger.endpoint;

import com.github.phillipkruger.service.ExchangeRateService;
import com.github.phillipkruger.model.CurencyCode;
import com.github.phillipkruger.model.ExchangeRate;
import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.service.PersonService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    
    public ExchangeRate getExchangeRate2(@Source Person person, CurencyCode against){
        Map<CurencyCode, ExchangeRate> map = getExchangeRate(against);
        List<ExchangeRate> rates = new ArrayList<>();
        return map.get(person.curencyCode);
    }
    
    public List<ExchangeRate> getExchangeRate(@Source List<Person> people, CurencyCode against){
        Map<CurencyCode, ExchangeRate> map = getExchangeRate(against);
        List<ExchangeRate> rates = new ArrayList<>();
        for(Person person : people){
            rates.add(map.get(person.curencyCode));
        }
        return rates;
    }
    
    private Map<CurencyCode, ExchangeRate> getExchangeRate(CurencyCode against){
        try {
            
            System.out.println(">>>>> Making backend call ...");
            
            return exchangeRateService.getExchangeRate(against);
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }
}
