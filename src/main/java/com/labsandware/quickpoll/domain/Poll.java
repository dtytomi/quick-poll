package com.labsandware.quickpoll.domain;

import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="POLL")
public class Poll {
    @Id
    @GeneratedValue
    @Column(name="POLL_ID")
    private Long id;

    @Column(name="QUESTION")
    @NotEmpty()
    private String question;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="POLL_ID")
    @OrderBy
    @Size(min = 2, max = 6)
    private Set<Option> options;

    public Poll() {

    }

    public Poll(String question) {
        this.question = question;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }
}
