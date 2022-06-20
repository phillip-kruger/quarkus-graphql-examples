package com.github.phillipkruger.service;

import com.github.phillipkruger.model.Address;
import com.github.phillipkruger.model.CurencyCode;
import com.github.phillipkruger.model.Gender;
import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.model.Phone;
import com.github.phillipkruger.model.Relation;
import com.github.phillipkruger.model.RelationType;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;

/**
 * Service that facade the Person database.
 * For this example we just use memory.
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@ApplicationScoped
public class PersonService {
    
    public List<Person> getAllPeople(){
        return List.copyOf(PEOPLE.values());
    }
    
    public Person getPerson(int id){
        return PEOPLE.get(id);
    }
    
    private static final Map<Integer,Person> PEOPLE = new ConcurrentHashMap<>();
    static {
        
        try {
            Person p1 = new Person();
            p1.id = 1;
            p1.locale = Locale.ENGLISH.toString();
            p1.title = "Mr";
            p1.addName("Natus");
            p1.addName("Phillip");
            p1.surname = "Kruger";
            p1.gender = Gender.Male;
            p1.birthDate = LocalDate.of(1978, Month.JULY, 3);
            p1.website = new URL("http://www.phillip-kruger.com");
            p1.addEmailAddress("phillip.kruger@redhat.com");
            p1.addEmailAddress("phillip.kruger@gmail.com");
            
            Address p1a1 = new Address();
            p1a1.addLine("1 Kerk straat");
            p1a1.addLine("Pretoria");
            p1a1.addLine("Gauteng");
            p1a1.code = "1234";
            p1.address = p1a1;
            
            Phone p1p1 = new Phone();
            p1p1.setNumber("123456789");
            p1p1.setType("Mobile");
            p1.addPhoneNumber(p1p1);
            
            p1.curencyCode = CurencyCode.ZAR;
            
            Person p2 = new Person();
            p2.id = 2;
            p2.locale = Locale.ENGLISH.toString();
            p2.title = "Mrs";
            p2.addName("Charmaine");
            p2.addName("Juliet");
            p2.surname = "Kruger";
            p2.gender = Gender.Female;
            p2.birthDate = LocalDate.of(1979, Month.SEPTEMBER, 18);
            p2.website = new URL("http://www.charmaine-kruger.com");
            p2.addEmailAddress("charmainw.kruger@gmail.com");
            
            Address p2a1 = new Address();
            p2a1.addLine("1 Kerk straat");
            p2a1.addLine("Pretoria");
            p2a1.addLine("Gauteng");
            p2a1.code = "1234";
            p2.address = p2a1;
            
            Phone p2p1 = new Phone();
            p2p1.setNumber("987654321");
            p2p1.setType("Mobile");
            p2.addPhoneNumber(p2p1);
            
            p2.curencyCode = CurencyCode.AUD;
            
            Relation p1r1 = new Relation();
            p1r1.relationType = RelationType.Spouse;
            p1r1.person = p2;
            p1.addRelationship(p1r1);
            
            Relation p2r1 = new Relation();
            p2r1.relationType = RelationType.Spouse;
            p2r1.person = p1;
            p2.addRelationship(p2r1);
            
            PEOPLE.put(p1.id, p1);
            PEOPLE.put(p2.id, p2);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        
        
        
    }
    
}
