package com.github.phillipkruger.service;

import com.github.phillipkruger.model.Address;
import com.github.phillipkruger.model.CurencyCode;
import com.github.phillipkruger.model.Gender;
import com.github.phillipkruger.model.PageInfo;
import com.github.phillipkruger.model.Person;
import com.github.phillipkruger.model.Phone;
import com.github.phillipkruger.model.Relation;
import com.github.phillipkruger.model.RelationType;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Service that facade the Person database.
 * For this example we just use memory.
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@ApplicationScoped
public class PersonService {
    
    @Inject PageInfoService pageInfoService;
    
    public int countPeople(){
        return PEOPLE.size();
    }
    
    public List<Person> getAllPeople(Integer itemsOnPage, Integer pageNumber){
        PageInfo pageInfo = new PageInfo();
        pageInfo.totalNumberOfItems = PEOPLE.size();
        
        Person[] all = PEOPLE.values().toArray(new Person[]{});
        
        // Defauls
        if(itemsOnPage==null)itemsOnPage = PEOPLE.size(); // By default we return everything
        if(pageNumber==null)pageNumber = 1; // By default there is only one page with everything on
            
        List<Person> subset = new ArrayList<>();
        
        int itemEnd = (itemsOnPage * pageNumber);
        int itemStart = itemEnd - itemsOnPage;
        
        for(int i=itemStart;i<itemEnd;i++){
            if(i<pageInfo.totalNumberOfItems){
                subset.add(all[i]);
            }
            
            if(i<pageInfo.totalNumberOfItems -1){
                pageInfo.nextPageNumber = pageNumber + 1;
                pageInfo.isLastPage = false;
            }else{
                pageInfo.nextPageNumber = -1;
                pageInfo.isLastPage = true;
            }
        }
        
        if(pageNumber==1){
            pageInfo.isFirstPage = true;
        }else{
            pageInfo.isFirstPage = false;
        }
        
        pageInfo.currentPageNumber = pageNumber;
        pageInfo.itemsOnPage = itemsOnPage;
        pageInfo.totalNumberOfPages = pageInfo.totalNumberOfItems / itemsOnPage;
        
        pageInfoService.setPageInfo(pageInfo);
        return subset;
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
            p1.addAddress(p1a1);
            
            Phone p1p1 = new Phone();
            p1p1.number = "123456789";
            p1p1.type = "Mobile";
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
            p2.addAddress(p1a1);
            
            Phone p2p1 = new Phone();
            p2p1.number = "987654321";
            p2p1.type = "Mobile";
            p2.addPhoneNumber(p1p1);
            
            p2.curencyCode = CurencyCode.AUD;
            
            
            Person p3 = new Person();
            p3.id = 3;
            p3.locale = Locale.ENGLISH.toString();
            p3.title = "Mr";
            p3.addName("John");
            p3.surname = "Doe";
            p3.gender = Gender.Male;
            p3.birthDate = LocalDate.of(1999, Month.APRIL, 12);
            p3.website = new URL("http://www.unknown.com");
            p3.addEmailAddress("john.doe@gmail.com");
            
            Address p3a1 = new Address();
            p3a1.addLine("1 Main street");
            p3a1.addLine("Melbourne");
            p3a1.addLine("VIC");
            p3a1.code = "3000";
            p3.addAddress(p3a1);
            
            Phone p3p1 = new Phone();
            p3p1.number = "123456789";
            p3p1.type = "Mobile";
            p3.addPhoneNumber(p3p1);
            
            p3.curencyCode = CurencyCode.AUD;
            
            
            Person p4 = new Person();
            p4.id = 4;
            p4.locale = Locale.ENGLISH.toString();
            p4.title = "Mrs";
            p4.addName("Jane");
            p4.surname = "Doe";
            p4.gender = Gender.Female;
            p4.birthDate = LocalDate.of(1999, Month.APRIL, 13);
            p4.website = new URL("http://www.unknown.com");
            p4.addEmailAddress("jane.doe@gmail.com");
            
            Address p4a1 = new Address();
            p4a1.addLine("2 Main street");
            p4a1.addLine("Melbourne");
            p4a1.addLine("VIC");
            p4a1.code = "3000";
            p4.addAddress(p4a1);
            
            Phone p4p1 = new Phone();
            p4p1.number = "123456789";
            p4p1.type = "Mobile";
            p4.addPhoneNumber(p4p1);
            
            p4.curencyCode = CurencyCode.GBP;
            
            
            Person p5 = new Person();
            p5.id = 5;
            p5.locale = Locale.GERMAN.toString();
            p5.title = "Mr";
            p5.addName("Fridrigh");
            p5.surname = "Goedtentag";
            p5.gender = Gender.Male;
            p5.birthDate = LocalDate.of(1998, Month.AUGUST, 9);
            p5.website = new URL("http://www.goedtentag.com");
            p5.addEmailAddress("goedtentag@gmail.com");
            
            Address p5a1 = new Address();
            p5a1.addLine("1 Main street");
            p5a1.addLine("Berlin");
            p5a1.addLine("Germany");
            p5a1.code = "9876";
            p5.addAddress(p5a1);
            
            Phone p5p1 = new Phone();
            p5p1.number = "123456789";
            p5p1.type = "Mobile";
            p5.addPhoneNumber(p5p1);
            
            p5.curencyCode = CurencyCode.EUR;
            
            
            Person p6 = new Person();
            p6.id = 6;
            p6.locale = Locale.ENGLISH.toString();
            p6.title = "Mr";
            p6.addName("Chuck");
            p6.surname = "Norris";
            p6.gender = Gender.Male;
            p6.birthDate = LocalDate.of(2000, Month.JANUARY, 1);
            p6.website = new URL("http://www.chuck.com");
            p6.addEmailAddress("chuck@gmail.com");
            
            Address p6a1 = new Address();
            p6a1.addLine("1 Chuck road");
            p6a1.addLine("Chuckville");
            p6a1.addLine("USA");
            p6a1.code = "0000";
            p6.addAddress(p6a1);
            
            Phone p6p1 = new Phone();
            p6p1.number = "123456789";
            p6p1.type = "Mobile";
            p6.addPhoneNumber(p5p1);
            
            p6.curencyCode = CurencyCode.USD;
            
            // Releationships
            
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
            PEOPLE.put(p3.id, p3);
            PEOPLE.put(p4.id, p4);
            PEOPLE.put(p5.id, p5);
            PEOPLE.put(p6.id, p6);
            
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
        
        
        
    }
    
}
