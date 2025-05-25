package com.github.phillipkruger.model;

public class PersonDatabaseEvent {
    private Person person;
    private Event event;

    public PersonDatabaseEvent() {
    }

    public PersonDatabaseEvent(Person person, Event event) {
        this.person = person;
        this.event = event;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public static enum Event {
        ADD, UPDATE, DELETE
    }
}
