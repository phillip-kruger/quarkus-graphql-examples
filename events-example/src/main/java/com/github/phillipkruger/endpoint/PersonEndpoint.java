package com.github.phillipkruger.endpoint;

import com.github.phillipkruger.service.ExchangeRateService;
import com.github.phillipkruger.model.CurencyCode;
import com.github.phillipkruger.model.ExchangeRate;
import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.service.PersonService;
import graphql.schema.GraphQLSchema;
import io.smallrye.graphql.api.Context;
import io.smallrye.graphql.cdi.event.AfterDataFetch;
import io.smallrye.graphql.cdi.event.AfterExecute;
import io.smallrye.graphql.cdi.event.BeforeDataFetch;
import io.smallrye.graphql.cdi.event.BeforeExecute;
import io.smallrye.graphql.cdi.event.ErrorDataFetch;
import io.smallrye.graphql.cdi.event.ErrorExecute;
import io.smallrye.graphql.cdi.event.ErrorInfo;
import io.smallrye.graphql.execution.event.InvokeInfo;
import io.smallrye.graphql.schema.model.Operation;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.enterprise.event.Observes;
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
    
    public ExchangeRate getExchangeRate(@Source Person person, CurencyCode against){
        try {
            ExchangeRate exchangeRate = exchangeRateService.getFutureExchangeRate(against,person.curencyCode).toCompletableFuture().get();
            System.err.println("exchangeRate = " + exchangeRate);
            return exchangeRate;
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public GraphQLSchema.Builder beforeSchemaBuild(@Observes GraphQLSchema.Builder builder) {
        // Here add you own, like for example a subscription
        System.err.println(">>>>> Received beforeSchemaBuild event");
        return builder;
    }
    
    public Operation createOperation(@Observes Operation operation) {
        // Here manipulate the model we ise to build the schema
        System.err.println(">>>>> Received createOperation event [" + operation.getName() + "]");
        return operation;
    }
    
    public void beforeExecute(@Observes @BeforeExecute Context context) {
        System.err.println(">>>>> Received beforeExecute event [" + context.getQuery() + "]");
    }

    public void beforeDataFetch(@Observes @BeforeDataFetch Context context) {
        System.err.println(">>>>> Received beforeDataFetch event [" + context.getQuery() + "]");
    }

    public void beforeInvoke(@Observes InvokeInfo invokeInfo) {
        System.err.println(">>>>> Received beforeInvoke event [" + invokeInfo.getOperationMethod().getName() + "]");
    }

    public void afterDataFetch(@Observes @AfterDataFetch Context context) {
        System.err.println(">>>>> Received afterDataFetch event [" + context.getQuery() + "]");
    }
    
    public void afterExecute(@Observes @AfterExecute Context context) {
        System.err.println(">>>>> Received afterExecute event [" + context.getQuery() + "]");
    }

    

    public void errorExecute(@Observes @ErrorExecute ErrorInfo errorInfo) {
        System.err.println(">>>>> Received errorExecute event [" + errorInfo.getT() + "]");
    }

    public void errorDataFetch(@Observes @ErrorDataFetch ErrorInfo errorInfo) {
        System.err.println(">>>>> Received errorDataFetch event [" + errorInfo.getT() + "]");
    }
}
