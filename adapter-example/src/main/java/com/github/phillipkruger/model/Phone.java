package com.github.phillipkruger.model;

public class Phone {
    
    private String type;
    private String number;

    public Phone() {
    }

    public Phone(String type, String number) {
        this.type = type;
        this.number = number;
    }

    public Phone(String typeAndNumber){
        String[] a = typeAndNumber.split(": ");
        this.type = a[0];
        this.number = a[1];
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return type + ": " + number;
    }

    public static Phone fromString(String typeAndNumber){
        String[] a = typeAndNumber.split(": ");
        return new Phone(a[0], a[1]);
    }
    
}
