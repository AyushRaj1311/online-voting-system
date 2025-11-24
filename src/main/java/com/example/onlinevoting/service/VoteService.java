package com.example.onlinevoting.service;

import com.example.onlinevoting.model.*;
import com.example.onlinevoting.repository.*;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;
    private final ElectionRepository electionRepository;
    private final VoteRepository voteRepository;

    public VoteService(UserRepository u, CandidateRepository c, ElectionRepository e, VoteRepository v) {
        this.userRepository = u;
        this.candidateRepository = c;
        this.electionRepository = e;
        this.voteRepository = v;
    }

    /**
     * THIS IS THE CORRECT METHOD.
     * It accepts a userId and a candidateId, and it handles all the logic
     * for validating the vote and saving it.
     */
    public void castVote(Long userId, Long candidateId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new IllegalStateException("Candidate not found"));
        Election election = candidate.getElection();

        if (!election.isLive()) {
            throw new IllegalStateException("This election is not currently live.");
        }

        if (voteRepository.existsByUserAndElection(user, election)) {
            throw new IllegalStateException("You have already voted in this election.");
        }

        Vote vote = new Vote(user, candidate, election);
        voteRepository.save(vote);

        candidate.setVotes(candidate.getVotes() + 1);
        candidateRepository.save(candidate);
    }
}
