package com.github.phillipkruger.model;

import io.smallrye.graphql.api.Adapter;

public class AddressAdapter implements Adapter<Address, Home> {

    @Override
    public Home to(Address address) throws Exception {
        Home home = new Home();
        home.setCode(address.code);
        home.setStreet(address.lines.get(0));
        home.setCity(address.lines.get(1));
        home.setProvince(address.lines.get(2));
        return home;
    }

    @Override
    public Address from(Home home) throws Exception {
        Address address = new Address();
        address.code = home.getCode();
        address.addLine(home.getStreet());
        address.addLine(home.getCity());       
        address.addLine(home.getProvince());
        
        return address;
    }

}
