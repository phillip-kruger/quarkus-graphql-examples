package com.github.phillipkruger.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.eclipse.microprofile.graphql.Ignore;

@Entity
public class Relation extends PanacheEntityBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Ignore
    public Integer id;
    
    public RelationType relationType;
    public String personURI;

}
