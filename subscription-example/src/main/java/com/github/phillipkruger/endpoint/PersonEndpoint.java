package com.github.phillipkruger.endpoint;

import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.service.PersonService;
import io.smallrye.graphql.api.Subscription;
import io.smallrye.mutiny.Multi;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import javax.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;

/**
 * Person GraphQL endpoint
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@GraphQLApi
public class PersonEndpoint {
    
    @Inject
    PersonService personService;
    
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
        return personService.personAddedListener();
    }
    
    @Subscription
    public Multi<Person> personQueried(){
        return personService.personQueriedListener();
    }
    
    @Subscription
    public Multi<Person> randomPerson() {
        Multi<Long> ticks = Multi.createFrom().ticks().every(Duration.ofSeconds(2));
           
        return ticks.onItem().transformToMulti(new Function<Long,Multi<Person>>() {
            @Override
            public Multi<Person> apply(Long t) {
                if ((t % 2) == 0) {
                    Person p = personService.getPerson(1);
                    return Multi.createFrom().items(p);
                } else {
                    Person p = personService.getPerson(2);
                    return Multi.createFrom().items(p);
                }
            }
        }).merge();
        
    } 
    

}
