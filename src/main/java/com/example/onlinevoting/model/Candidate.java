package com.example.onlinevoting.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // This correctly maps to the 'name' column in your database
    private String name;

    // This correctly maps to the 'votes' column
    private int votes = 0;

    /**
     * This sets up the relationship to the Party.
     * The @JoinColumn tells the database that the 'party_id' column
     * in the 'candidate' table is the foreign key.
     */
    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;

    /**
     * This sets up the relationship to the Election.
     * nullable = false means a candidate MUST belong to an election.
     */
    @ManyToOne
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    // --- Constructors ---

    public Candidate() {}

    public Candidate(String name, Party party, Election election) {
        this.name = name;
        this.party = party;
        this.election = election;
        this.votes = 0;
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Election getElection() {
        return election;
    }

    public void setElection(Election election) {
        this.election = election;
    }
}
