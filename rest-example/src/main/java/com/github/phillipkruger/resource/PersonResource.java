package com.github.phillipkruger.resource;

import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.service.PersonService;
import java.util.List;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/person")
public class PersonResource {

    @Inject
    PersonService personService;
    
    @GET
    public List<Person> getPeople(){
        return personService.getAllPeople();
    }
    
    @GET
    @Path("/{id}")
    @Operation(description = "Get a person using the person's ID")
    public Person getPerson(@PathParam("id") long id){
        return personService.getPerson(id);
    }
    
    @POST
    public Person addPerson(Person person){
        return personService.updateOrCreate(person);
    }
}