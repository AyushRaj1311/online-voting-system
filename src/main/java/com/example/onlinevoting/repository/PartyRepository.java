package com.example.onlinevoting.repository;

import com.example.onlinevoting.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // Add this import

public interface PartyRepository extends JpaRepository<Party, Long> {
    // --- ADD THIS METHOD ---
    // This allows the service to check for existing parties by name.
    Optional<Party> findByPartyName(String partyName);
}
