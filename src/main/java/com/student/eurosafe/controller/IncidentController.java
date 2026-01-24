package com.student.eurosafe.controller;

import com.student.eurosafe.entity.Incident;
import com.student.eurosafe.entity.User;
import com.student.eurosafe.repository.IncidentRepository;
import com.student.eurosafe.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;

    public IncidentController(IncidentRepository incidentRepository, UserRepository userRepository) {
        this.incidentRepository = incidentRepository;
        this.userRepository = userRepository;
    }

    // 1. SOS Endpoint: Create a new alert
    @PostMapping
    public ResponseEntity<?> createIncident(@RequestBody Incident incident, Principal principal) {
        String username = principal.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            incident.setUser(user);
            incident.setStatus("OPEN");
            
            Incident savedIncident = incidentRepository.save(incident);
            return ResponseEntity.ok(savedIncident);
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    // 2. Get My Incidents: See previous alerts
    @GetMapping
    public ResponseEntity<List<Incident>> getMyIncidents(Principal principal) {
        String username = principal.getName();
        Optional<User> user = userRepository.findByUsername(username);
        
        if (user.isPresent()) {
            List<Incident> incidents = incidentRepository.findByUserId(user.get().getId());
            return ResponseEntity.ok(incidents);
        }
        return ResponseEntity.status(404).build();
    }

    // 3. Resolve Incident: Mark as "Safe"
    @PatchMapping("/{id}/resolve")
    public ResponseEntity<?> resolveIncident(@PathVariable Long id, Principal principal) {
        String username = principal.getName();
        
        Optional<Incident> incidentOptional = incidentRepository.findById(id);

        if (incidentOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Incident not found");
        }

        Incident incident = incidentOptional.get();

        // Check ownership
        if (!incident.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(403).body("You are not allowed to modify this incident");
        }

        incident.setStatus("RESOLVED");
        incidentRepository.save(incident);

        return ResponseEntity.ok("Incident marked as RESOLVED. You are safe.");
    }

    // 4. Community Safety: See all ACTIVE alerts (NEW for Day 11)
    @GetMapping("/active")
    public ResponseEntity<List<Incident>> getActiveIncidents() {
        // Fetch only the incidents that are still happening
        List<Incident> activeIncidents = incidentRepository.findByStatus("OPEN");
        return ResponseEntity.ok(activeIncidents);
    }
}