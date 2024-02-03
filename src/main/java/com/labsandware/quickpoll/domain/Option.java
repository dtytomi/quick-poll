package com.labsandware.quickpoll.domain;

import jakarta.persistence.*;

@Entity
@Table(name="option")
public class Option {
    @Id
    @GeneratedValue
    @Column(name = "OPTION_ID")
    private long id;

    @Column(name = "OPTION_VALUE")
    private String value;

    public Option() {

    }

    public Option(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
