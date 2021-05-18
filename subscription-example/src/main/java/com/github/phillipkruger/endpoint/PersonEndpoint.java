package com.github.phillipkruger.endpoint;

import com.github.phillipkruger.service.ExchangeRateService;
import com.github.phillipkruger.model.CurencyCode;
import com.github.phillipkruger.model.ExchangeRate;
import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.service.PersonService;
import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import javax.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
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
    public Person getPerson(int id) {
        return personService.getPerson(id);
    }
    
    @Mutation
    public Person addPerson(Person p){
        return personService.addPerson(p);
    }
    
    @Subscription
    public Multi<Person> personAdded(){
        return personService.personListener();
    }
    
    @Subscription
    public Multi<Person> randomPerson() {
        Multi<Long> ticks = Multi.createFrom().ticks().every(Duration.ofSeconds(2));
           
        return ticks.onItem().transformToMulti(new Function<Long,Multi<Person>>() {
            @Override
            public Multi<Person> apply(Long t) {
                if(t.intValue() < 10 ){
                    Person p = personService.getPerson(1);
                    p.id = t.intValue();
                    return Multi.createFrom().items(p);
                }else{
                    return Multi.createFrom().failure(new RuntimeException("Some big issue !"));
                }
            }
        }).merge();
        
    } 
    
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
