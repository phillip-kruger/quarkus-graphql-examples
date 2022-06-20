package com.github.phillipkruger.model;

public class Home {
    private String street;
    private String city;
    private String province;
    private String code;

    public Home() {
    }

    public Home(String street, String city, String province, String code) {
        this.street = street;
        this.city = city;
        this.province = province;
        this.code = code;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    
    
}
