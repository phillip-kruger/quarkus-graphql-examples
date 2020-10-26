package com.github.phillipkruger.endpoint;

import com.github.phillipkruger.service.ExchangeRateService;
import com.github.phillipkruger.model.CurencyCode;
import com.github.phillipkruger.model.ExchangeRate;
import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.service.PersonService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public Person getPerson(int id){
        return personService.getPerson(id);
    }
    
    public List<ExchangeRate> getExchangeRate(@Source List<Person> people, CurencyCode against){
        Map<CurencyCode, Double> map = exchangeRateService.getExchangeRates(against);
        
        List<ExchangeRate> rates = new ArrayList<>();
        for(Person person : people){
            Double rate = map.get(person.curencyCode);
            rates.add(new ExchangeRate(person.curencyCode, against, rate));
        }
        
        return rates;
    }
}
