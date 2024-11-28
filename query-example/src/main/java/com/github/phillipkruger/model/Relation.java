package com.github.phillipkruger.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Relation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonbTransient
    private Integer id;
    
    private RelationType relationType;
    private String personURI;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public String getPersonURI() {
        return personURI;
    }

    public void setPersonURI(String personURI) {
        this.personURI = personURI;
    }
}
