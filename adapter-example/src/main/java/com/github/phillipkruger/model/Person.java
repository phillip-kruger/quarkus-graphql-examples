package com.github.phillipkruger.model;

import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Person {
    //@AdaptToScalar(Scalar.String.class)
    public int id;
    public String locale;
    public String title;
    public List<String> names;
    public String surname;
    public Gender gender;
    public LocalDate birthDate;
    //@AdaptWith(AddressAdapter.class)
    public Address address;
    public List<String> emailAddresses;
    //@AdaptToScalar(Scalar.String.class)
    public List<Phone> phoneNumbers;
    public URL website;
    public List<Relation> relations;
    public CurencyCode curencyCode;
    
    public void addName(String name){
        if(names==null)names = new LinkedList<>();
        names.add(name);
    }

    public void addEmailAddress(String emailAddress){
        if(emailAddresses==null)emailAddresses = new LinkedList<>();
        emailAddresses.add(emailAddress);
    }

    public void addPhoneNumber(Phone phoneNumber){
        if(phoneNumbers==null)phoneNumbers = new LinkedList<>();
        phoneNumbers.add(phoneNumber);
    }

    public void addRelationship(Relation relation){
        if(relations==null)relations = new LinkedList<>();
        relations.add(relation);
    }

}