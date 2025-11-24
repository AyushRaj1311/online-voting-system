package com.example.onlinevoting.controller;

import com.example.onlinevoting.model.*;
import com.example.onlinevoting.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // --- GET (Read) Endpoints ---
    @GetMapping("/elections")
    public List<Election> getAllElections() { return adminService.getAllElections(); }

    @GetMapping("/parties")
    public List<Party> getAllParties() { return adminService.getAllParties(); }

    // --- POST (Create) Endpoints ---
    @PostMapping("/elections")
    public Election createElection(@RequestBody Map<String, String> payload) {
        return adminService.createElection(payload.get("name"));
    }

    @PostMapping("/parties")
    public ResponseEntity<?> addParty(@RequestBody Map<String, String> payload) {
        try {
            return ResponseEntity.ok(adminService.addParty(payload.get("name")));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/candidates")
    public ResponseEntity<?> addCandidate(@RequestBody Map<String, String> payload) {
        try {
            String name = payload.get("name");
            Long partyId = Long.parseLong(payload.get("partyId"));
            Long electionId = Long.parseLong(payload.get("electionId"));
            return ResponseEntity.ok(adminService.addCandidate(name, partyId, electionId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // --- PUT (Update) Endpoints ---
    @PutMapping("/elections/{id}/start")
    public ResponseEntity<?> startElection(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(adminService.startElection(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/elections/{id}/stop")
    public ResponseEntity<?> stopElection(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.stopElection(id));
    }
}
