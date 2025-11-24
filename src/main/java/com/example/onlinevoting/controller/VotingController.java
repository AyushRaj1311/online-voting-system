package com.example.onlinevoting.controller;

import com.example.onlinevoting.model.*;
import com.example.onlinevoting.service.VoteService;
import com.example.onlinevoting.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class VotingController {

    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;
    private final ElectionRepository electionRepository;
    private final VoteService voteService;

    public VotingController(UserRepository u, CandidateRepository c, ElectionRepository e, VoteService v) {
        this.userRepository = u;
        this.candidateRepository = c;
        this.electionRepository = e;
        this.voteService = v;
    }

    /**
     * THIS IS THE FIX.
     * The return type is changed to ResponseEntity<?> which allows the method
     * to correctly return a User object on success and an error Map on failure.
     * This resolves the "Incompatible types" error.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
        Optional<User> userOpt = userRepository.findByUsername(credentials.get("username"));
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(credentials.get("password"))) {
            return ResponseEntity.ok(userOpt.get());
        }
        return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username is already taken."));
        }
        user.setRole("VOTER");
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
    }

    @GetMapping("/elections/active")
    public List<Election> getActiveElections() {
        return electionRepository.findByLiveTrue();
    }

    @GetMapping("/elections/{id}/candidates")
    public List<Candidate> getCandidatesForElection(@PathVariable Long id) {
        return candidateRepository.findByElectionId(id);
    }

    @PostMapping("/vote")
    public ResponseEntity<?> castVote(@RequestBody Map<String, String> payload) {
        try {
            Long userId = Long.parseLong(payload.get("userId"));
            Long candidateId = Long.parseLong(payload.get("candidateId"));
            voteService.castVote(userId, candidateId);
            return ResponseEntity.ok(Map.of("message", "Vote cast successfully!"));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
