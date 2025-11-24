package com.example.onlinevoting.repository;

import com.example.onlinevoting.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ElectionRepository extends JpaRepository<Election, Long> {

    // This finds all elections for the admin panel
    List<Election> findByOrderByIdAsc();

    // This now finds elections where the 'live' column is true
    List<Election> findByLiveTrue();
}
