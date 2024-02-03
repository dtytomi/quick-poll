package com.labsandware.quickpoll.domain;


import jakarta.persistence.*;

@Entity
@Table(name = "VOTE")
public class Vote {

    @Id
    @GeneratedValue
    @Column(name="VOTE_ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="OPTION_ID")
    private Option option;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }
}
