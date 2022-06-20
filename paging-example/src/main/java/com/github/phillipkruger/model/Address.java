package com.github.phillipkruger.model;

import java.util.LinkedList;
import java.util.List;

public class Address {
    public List<String> lines;
    public String code;
    
    public void addLine(String line){
        if(lines==null)lines = new LinkedList<>();
        lines.add(line);
    }
}
