package com.github.phillipkruger.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.eclipse.microprofile.graphql.Ignore;

@Entity
public class ImClient extends PanacheEntityBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Ignore
    public Integer id;
    
    public String im;
    public String identifier;

}
