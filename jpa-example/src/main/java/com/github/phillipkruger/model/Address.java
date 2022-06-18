package com.github.phillipkruger.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderColumn;
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
