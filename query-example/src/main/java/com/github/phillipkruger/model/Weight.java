package com.github.phillipkruger.model;

public class Weight implements Measurable {
    private Long value;

    @Override
    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
    
}