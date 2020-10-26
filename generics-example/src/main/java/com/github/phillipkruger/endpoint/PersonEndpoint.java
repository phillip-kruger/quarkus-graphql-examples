package com.github.phillipkruger.endpoint;

import com.github.phillipkruger.model.Alien;
import com.github.phillipkruger.model.Being;
import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.service.AlienService;
import com.github.phillipkruger.service.PersonService;
import java.util.List;
import javax.inject.Inject;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

/**
 * Person GraphQL endpoint
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@GraphQLApi
public class PersonEndpoint {

    @Inject
    PersonService personService;
    
    @Inject
    AlienService alienService;
    
    @Query
    public Being<Person> getPerson(int id){
        return new Being<>(personService.getPerson(id));
    }
    
    @Query
    public Being<Alien> getAlien(int id){
        return new Being<>(alienService.getAlien(id));
    }
    
}
