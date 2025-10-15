package com.github.phillipkruger.resource;

import com.github.phillipkruger.ExchangeRateNotAvailableException;
import com.github.phillipkruger.model.CurrencyCode;
import com.github.phillipkruger.model.ExchangeRate;
import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.model.Score;
import com.github.phillipkruger.service.ExchangeRateService;
import com.github.phillipkruger.service.PersonService;
import com.github.phillipkruger.service.ScoreService;
import java.util.List;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;

/**
 * Person GraphQL endpoint
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@GraphQLApi
public class PersonResource {

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
    public Person getPerson(long id){
        return personService.getPerson(id);
    }
    
//    @Query
//    @Description("Get a person using the person's ID")
//    public CompletionStage<Person> getPerson(long id){
//        return CompletableFuture.supplyAsync(()-> personService.getPerson(id));
//    }
    
    public List<Score> getScores(@Source Person people) {
        return scoreService.getScores(people.idNumber);
    }
    
    
//    public List<List<Score>> getScores(@Source List<Person> people) {
//        List<String> ids = people.stream().map(person -> person.idNumber).collect(Collectors.toList());
//        return scoreService.getScores(ids);
//    }
    
    public ExchangeRate getExchangeRate(@Source Person person, CurrencyCode against) throws ExchangeRateNotAvailableException{
        return exchangeRateService.getExchangeRate(against, person.currencyCode);
    }
    
//    public CompletionStage<ExchangeRate> getExchangeRate(@Source Person person, CurrencyCode against) throws ExchangeRateNotAvailableException{
//        return exchangeRateService.getFutureExchangeRate(against, person.currencyCode);
//    }
    
//    public CompletionStage<ExchangeRate> getExchangeRate(@Source Person person, CurrencyCode against) throws ExchangeRateNotAvailableException{
//        return CompletableFuture.failedStage(new ScoresNotAvailableException("Exchange rate system is down"));
//    }
    
    
}
