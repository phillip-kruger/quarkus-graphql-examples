package com.github.phillipkruger.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.phillipkruger.model.Person;
import io.smallrye.graphql.api.Context;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonValue;

/**
 * Service that facade the Person database.
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
@ApplicationScoped
public class PersonService {
    
    public List<Person> getAllPeople(){
        return Person.listAll();
    }
    
    public Person getPerson(long id){
        return Person.findById(id);
    }
    
    
    @Inject Context context;
    
    public Person getPerson2(long id){
        
        StringWriter sw = new StringWriter();
        JsonArray selectedFields = context.getSelectedFields();
        List<String> fieldQueries = new ArrayList<>();
        
        for (JsonValue selectedField : selectedFields) {
            if (selectedField.getValueType() == JsonValue.ValueType.STRING) {
                fieldQueries.add(selectedField.toString().replace("\"", ""));
            } else {
                fieldQueries.add(selectedField.toString());
            }
        }
        String sqlpart = String.join(", ", fieldQueries);
        Object rawresponse = Person.find("select distinct " + sqlpart +" from Person p where p.id=" + id).firstResult();
        Object[] fieldValues = (Object[])rawresponse;
        Map<String,String> fieldsMap = new HashMap<>();
        int cnt = 0;
        for(Object fieldValue:fieldValues){
            fieldsMap.put(fieldQueries.get(cnt++), fieldValue.toString());
        }
        
        final ObjectMapper mapper = new ObjectMapper();
        final Person person = mapper.convertValue(fieldsMap, Person.class);
        
        return person;
    }
    
    
    public Person createPerson(Person person) {
        person.persistAndFlush();
        return person;
    }
    
}
