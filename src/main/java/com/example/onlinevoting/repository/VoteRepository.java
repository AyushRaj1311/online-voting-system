package com.example.onlinevoting.repository;

import com.example.onlinevoting.model.Election;
import com.example.onlinevoting.model.User;
import com.example.onlinevoting.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    // Derived queries matching different usages in your code
    boolean existsByUserAndElection(User user, Election election);

    boolean existsByUserIdAndElectionId(Long userId, Long electionId);

    Optional<Vote> findByUserIdAndElectionId(Long userId, Long electionId);

    // Derived count by candidate id (simple)
    long countByCandidateId(Long candidateId);


    // JPQL returning [candidateId, count] rows (used by some versions of the service)
    @Query("SELECT v.candidate.id, COUNT(v) FROM Vote v WHERE v.election.id = :electionId GROUP BY v.candidate.id")
    List<Object[]> countVotesByCandidate(@Param("electionId") Long electionId);
}
