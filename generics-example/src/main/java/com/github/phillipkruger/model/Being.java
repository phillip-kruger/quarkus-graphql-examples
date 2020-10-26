package com.github.phillipkruger.model;

/**
 * Some Being
 * @author Phillip Kruger (phillip.kruger@redhat.com)
 */
public class Being<T> {

    private T being;

    public Being() {
    }

    public Being(T being) {
        this.being = being;
    }

    public T getBeing() {
        return being;
    }

    public void setBeing(T being) {
        this.being = being;
    }
}
