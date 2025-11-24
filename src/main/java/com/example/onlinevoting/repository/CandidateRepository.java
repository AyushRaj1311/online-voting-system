package com.example.onlinevoting.repository;

import com.example.onlinevoting.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    // --- ADD THESE TWO METHODS ---

    /**
     * Finds all candidates associated with a specific election ID.
     * This is needed for the user dashboard.
     */
    List<Candidate> findByElectionId(Long electionId);

    /**
     * Efficiently counts how many candidates are in a given election.
     * This is the key to our new business rule.
     */
    long countByElectionId(Long electionId);
    
}

