package com.github.phillipkruger.endpoint.graphql;

import com.github.phillipkruger.ScoresNotAvailableException;
import com.github.phillipkruger.model.CurrencyCode;
import com.github.phillipkruger.model.ExchangeRate;
import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.model.PersonDatabaseEvent;
import com.github.phillipkruger.model.Score;
import com.github.phillipkruger.service.ExchangeRateService;
import com.github.phillipkruger.service.PersonService;
import com.github.phillipkruger.service.ScoreService;
import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import java.util.List;
import jakarta.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;

/**
 * Person GraphQL endpoint
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@GraphQLApi
public class PersonGraphQLApi {

    @Inject
    PersonService personService;
    
    @Inject 
    ScoreService scoreService;
    
    @Inject
    ExchangeRateService exchangeRateService;
    
    @Query
    public List<Person> getPeople(){
        return personService.getAllPeople();
    }
    
    @Query
    @Description("Get a person using the person's ID")
    public CompletionStage<Person> getPerson(long id){
        //return personService.getPerson(id);
        return CompletableFuture.supplyAsync(()-> personService.getPerson(id));
    }
    
    public List<List<Score>> getScores(@Source List<Person> people) throws ScoresNotAvailableException{
        List<String> ids = people.stream().map(Person::getIdNumber).collect(Collectors.toList());
        return scoreService.getScores(ids);
    }
    
    public CompletionStage<ExchangeRate> getExchangeRate(@Source Person person, CurrencyCode against){
        //return exchangeRateService.getExchangeRate(against, person.getCurrencyCode());
        return exchangeRateService.getFutureExchangeRate(against, person.getCurrencyCode());
        //return CompletableFuture.failedStage(new ScoresNotAvailableException("Exchange rate system is down"));
    }
    
    @Mutation
    public Person updateOrCreatePerson(Person person){
        return personService.updateOrCreate(person);
    }

    @Mutation
    public Person deletePerson(Long id){
        return personService.delete(id);
    }
    
    @Subscription
    public Multi<PersonDatabaseEvent> personDatabaseEvent(){
        return personService.getPersonDatabaseEventListener();
    }
}
