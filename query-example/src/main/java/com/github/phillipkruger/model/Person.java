package com.github.phillipkruger.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Person extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String locale;

    public String title;

    @ElementCollection(fetch = FetchType.LAZY, targetClass=String.class)
    public List<String> names;

    @ElementCollection(fetch = FetchType.LAZY, targetClass=String.class)
    public List<String> nicknames;

    public String surname;

    public String username;

    public String idNumber;

    @ElementCollection(fetch = FetchType.LAZY, targetClass=URL.class)
    public List<URL> coverphotos;

    @ElementCollection(fetch = FetchType.LAZY, targetClass=URL.class)
    public List<URL> profilePictures;

    public Gender gender;

    public LocalDate birthDate;

    private String favColor;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Address> addresses;

    @ElementCollection(fetch = FetchType.LAZY,targetClass=String.class)
    public List<String> emailAddresses;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<Phone> phoneNumbers;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<ImClient> imClients;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<SocialMedia> socialMedias;

    private URL website;

    @ElementCollection(fetch = FetchType.LAZY, targetClass=String.class)
    public List<String> taglines;

    public String biography;

    public String organization;

    public String occupation;

    @ElementCollection(fetch = FetchType.LAZY, targetClass=String.class)
    public List<String> interests;

    @ElementCollection(fetch = FetchType.LAZY, targetClass=String.class)
    public List<String> skills;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    public List<Relation> relations;

    public LocalDate joinDate;

    public String maritalStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<CreditCard> creditCards;

    public String userAgent;

    public CurrencyCode currencyCode;
}