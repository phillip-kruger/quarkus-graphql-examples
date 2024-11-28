package com.github.phillipkruger.endpoint;

import com.github.phillipkruger.service.ExchangeRateService;
import com.github.phillipkruger.model.CurencyCode;
import com.github.phillipkruger.model.ExchangeRate;
import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.service.PersonService;
import io.quarkus.vertx.http.runtime.CurrentVertxRequest;
import io.smallrye.graphql.api.Context;
import java.util.List;
import java.util.concurrent.ExecutionException;
import jakarta.inject.Inject;
import java.util.Map;
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
    
    @Inject
    Context context;
    
    @Inject
    public CurrentVertxRequest request;
    
    @Query
    public List<Person> getPeople(){
        return personService.getAllPeople();
    }
    
    @Query
    public Person getPerson(int id){
        return personService.getPerson(id);
    }
    
    @Query
    public Map<String, String> details(){
        
        String headerValue = request.getCurrent().request().getHeader("My-Header");
        if(headerValue==null){
            headerValue = "You need to set the my-header value to see it here";
        }
        
        return Map.of("executionId", context.getExecutionId(),
                    "query",context.getQuery(),
                    "selected fields", context.getSelectedFields().toString(),
                    "my-header", headerValue);
        
    }
    
    public ExchangeRate getExchangeRate(@Source Person person, Context context, CurencyCode against){
        System.err.println("context in PersonService = " + context);
        try {
            ExchangeRate exchangeRate = exchangeRateService.getFutureExchangeRate(against,person.curencyCode).toCompletableFuture().get();
            System.err.println("exchangeRate = " + exchangeRate);
            return exchangeRate;
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }
}
