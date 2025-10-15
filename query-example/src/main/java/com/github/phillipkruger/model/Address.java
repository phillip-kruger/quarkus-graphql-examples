package com.github.phillipkruger.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.List;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OrderColumn;
import org.eclipse.microprofile.graphql.Ignore;

@Entity
public class Address extends PanacheEntityBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Ignore
    public Integer id;
    
    @ElementCollection(fetch = FetchType.LAZY,targetClass=String.class) 
    @OrderColumn
    public List<String> lines;
    
    public String code;

}
