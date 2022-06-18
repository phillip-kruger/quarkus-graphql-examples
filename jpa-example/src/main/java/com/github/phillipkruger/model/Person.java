package com.github.phillipkruger.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    public CurencyCode curencyCode;
}