package com.github.phillipkruger.endpoint;

import com.github.phillipkruger.service.ExchangeRateService;
import com.github.phillipkruger.model.CurencyCode;
import com.github.phillipkruger.model.ExchangeRate;
import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.service.PersonService;
import io.quarkus.security.identity.SecurityIdentity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;
import org.eclipse.microprofile.jwt.JsonWebToken;

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
    SecurityIdentity securityIdentity;
    
    @Inject
    JsonWebToken jwt; 
    
    @Query
    @RolesAllowed("user")
    public Person getPerson(int id){
        return personService.getPerson(id);
    }
    
    @RolesAllowed("admin")
    public ExchangeRate getExchangeRate(@Source Person person, @DefaultValue("USD") CurencyCode against){
        Map<CurencyCode, ExchangeRate> map = getExchangeRate(against);
        List<ExchangeRate> rates = new ArrayList<>();
        return map.get(person.curencyCode);
    }
    
    @Query
    public Map<String,String> details(){
        return Map.of("principal", securityIdentity.getPrincipal().getName(),
                "jwtname", jwt.getName());
        
    }
    
    private Map<CurencyCode, ExchangeRate> getExchangeRate(CurencyCode against){
        try {
            return exchangeRateService.getExchangeRate(against);
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }
}
