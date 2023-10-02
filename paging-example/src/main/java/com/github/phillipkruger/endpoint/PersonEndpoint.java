package com.github.phillipkruger.endpoint;

import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.service.PersonService;
import java.util.List;
import jakarta.inject.Inject;
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
    
    @Query
    public List<Person> getPeople(Integer itemsOnPage, Integer pageNumber){
        return personService.getAllPeople(itemsOnPage, pageNumber);
    }
    
}
