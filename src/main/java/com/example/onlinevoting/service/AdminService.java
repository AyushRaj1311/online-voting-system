package com.example.onlinevoting.service;

import com.example.onlinevoting.model.*;
import com.example.onlinevoting.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {

    private final ElectionRepository electionRepo;
    private final PartyRepository partyRepo;
    private final CandidateRepository candidateRepo;

    public AdminService(ElectionRepository e, PartyRepository p, CandidateRepository c) {
        this.electionRepo = e;
        this.partyRepo = p;
        this.candidateRepo = c;
    }

    public List<Party> getAllParties() {
        return partyRepo.findAll();
    }

    // ... The rest of the file is the same as the correct version you already have ...

    public Party addParty(String name) {
        if (partyRepo.findByPartyName(name).isPresent()) {
            throw new IllegalStateException("A party with the name '" + name + "' already exists.");
        }
        Party party = new Party();
        party.setPartyName(name);
        return partyRepo.save(party);
    }

    public Election createElection(String name) {
        Election election = new Election();
        election.setName(name);
        election.setLive(false);
        return electionRepo.save(election);
    }

    public Candidate addCandidate(String name, Long partyId, Long electionId) {
        Party party = partyRepo.findById(partyId).orElseThrow(() -> new RuntimeException("Party not found with ID: " + partyId));
        Election election = electionRepo.findById(electionId).orElseThrow(() -> new RuntimeException("Election not found with ID: " + electionId));
        Candidate candidate = new Candidate();
        candidate.setName(name);
        candidate.setParty(party);
        candidate.setElection(election);
        candidate.setVotes(0);
        return candidateRepo.save(candidate);
    }

    public Election startElection(Long id) {
        long candidateCount = candidateRepo.countByElectionId(id);
        if (candidateCount == 0) {
            throw new IllegalStateException("An election must have at least one candidate to go live.");
        }
        Election election = electionRepo.findById(id).orElseThrow(() -> new RuntimeException("Election not found"));
        election.setLive(true);
        return electionRepo.save(election);
    }

    public Election stopElection(Long id) {
        Election election = electionRepo.findById(id).orElseThrow();
        election.setLive(false);
        return electionRepo.save(election);
    }

    public List<Election> getAllElections() {
        return electionRepo.findByOrderByIdAsc();
    }
}
