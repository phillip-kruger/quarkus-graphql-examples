package com.github.phillipkruger.model;

import java.util.List;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OrderColumn;

@Entity
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonbTransient
    private Integer id;
    
    @ElementCollection(fetch = FetchType.LAZY,targetClass=String.class) 
    @OrderColumn
    private List<String> lines;
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
