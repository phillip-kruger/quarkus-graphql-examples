package com.github.phillipkruger.jaxrs;

import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.service.PersonService;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Person REST endpoint
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@Path("/person")
public class PersonEndpoint {

    @Inject
    PersonService personService;
    
    @GET
    public List<Person> getPeople(){
        return personService.getAllPeople();
    }
    
    @GET
    @Path("/{id}")
    public Person getPerson(@PathParam("id") long id){
        return personService.getPerson(id);
    }
    
    @POST
    public Person addPerson(Person person){
        return personService.createPerson(person);
    }
}
