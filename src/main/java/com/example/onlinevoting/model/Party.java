package com.example.onlinevoting.model;

import javax.persistence.Column; // Make sure this import is here
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * THIS IS THE FIX.
     * The @Column annotation tells Hibernate that the 'partyName' field
     * in our Java code corresponds to the 'name' column in the database table.
     * This resolves the "Field 'name' doesn't have a default value" error.
     */
    @Column(name = "name")
    private String partyName;

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
}
