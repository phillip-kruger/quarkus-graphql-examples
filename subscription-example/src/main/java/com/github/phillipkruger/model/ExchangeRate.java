package com.github.phillipkruger.model;

/**
 * Exchange rate pojo
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
public class ExchangeRate {
    public CurencyCode base;
    public CurencyCode against;
    public Double rate;

    public ExchangeRate() {
    }

    public ExchangeRate(CurencyCode base, CurencyCode against, Double rate) {
        this.base = base;
        this.against = against;
        this.rate = rate;
    }

    @Override
    public String toString() {
        return against + "/" + base + " = " + rate + "/1";
    }
    
    
}
