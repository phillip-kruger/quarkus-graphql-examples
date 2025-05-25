package com.github.phillipkruger.service;

import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.model.PersonDatabaseEvent;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.BroadcastProcessor;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Service that facade the Person database.
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@ApplicationScoped
public class PersonService {
    
    BroadcastProcessor<PersonDatabaseEvent> broadcaster = BroadcastProcessor.create();
    
    @PersistenceContext
    EntityManager em;
    
    public List<Person> getAllPeople(){
        Log.info(">> Getting all people");
        List<Person> people = (List<Person>)em.createQuery("SELECT p FROM Person p",Person.class)
                .getResultList();
        Log.info("<< Got all people");
        return people;
    }
    
    public Person getPerson(long id){
        Log.info(">> Getting person [" + id +"]");
        Person p = em.find(Person.class,id);
        Log.info("<< Got person [" + id +"]");
        return p;
    }
    
    @Transactional
    public Person updateOrCreate(Person person) {
        
        System.out.println("Creating person " + person);
        
        if(person.getId()==null){
            em.persist(person);
            broadcaster.onNext(new PersonDatabaseEvent(person, PersonDatabaseEvent.Event.ADD));
            return person;
        }else{
            Person existing = em.find(Person.class, person.getId());
            if(existing!=null){
                broadcaster.onNext(new PersonDatabaseEvent(person, PersonDatabaseEvent.Event.UPDATE));
                return em.merge(person);
            }else {
                em.persist(person);
                broadcaster.onNext(new PersonDatabaseEvent(person, PersonDatabaseEvent.Event.ADD));
                return person;
            }
        }
    }

    @Transactional
    public Person delete(Long id) {
        Person p = em.find(Person.class,id);

        if(p!=null){
            em.remove(p);
            broadcaster.onNext(new PersonDatabaseEvent(p, PersonDatabaseEvent.Event.DELETE));
        }
        return p;
    }
    
    public Multi<PersonDatabaseEvent> getPersonDatabaseEventListener() {
        return broadcaster;
    }
}