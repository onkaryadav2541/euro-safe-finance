package com.student.eurosafe.controller;

import com.student.eurosafe.entity.Incident;
import com.student.eurosafe.entity.User;
import com.student.eurosafe.repository.IncidentRepository;
import com.student.eurosafe.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

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
        // 'Principal' contains the info of the currently logged-in user.
        // We use it to find out WHO is sending the SOS.
        String username = principal.getName();
        
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            incident.setUser(user); // Link the incident to the user
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
}