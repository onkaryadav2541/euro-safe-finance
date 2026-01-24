package com.student.eurosafe.repository;

import com.student.eurosafe.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
    
    // Find all incidents reported by a specific user (History)
    List<Incident> findByUserId(Long userId);

    // NEW: Find all incidents that are currently active (The Safe Map)
    // This allows us to get a list of everything with status="OPEN"
    List<Incident> findByStatus(String status);
}