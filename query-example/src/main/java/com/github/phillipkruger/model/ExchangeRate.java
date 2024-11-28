package com.github.phillipkruger.model;

/**
 * Exchange rate pojo
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
public class ExchangeRate {
    public CurrencyCode base;
    public CurrencyCode against;
    public Double rate;

    public ExchangeRate() {
    }

    public ExchangeRate(CurrencyCode base, CurrencyCode against, Double rate) {
        this.base = base;
        this.against = against;
        this.rate = rate;
    }

    @Override
    public String toString() {
        return against + "/" + base + " = " + rate + "/1";
    }
    
    
}
