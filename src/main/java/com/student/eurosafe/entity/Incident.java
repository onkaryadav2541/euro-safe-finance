package com.student.eurosafe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- NEW VALIDATION RULES ADDED BELOW ---

    @NotNull(message = "Latitude is required")
    @Column(nullable = false)
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @Column(nullable = false)
    private Double longitude;

    @NotBlank(message = "Description cannot be empty")
    @Column(nullable = false)
    private String description; 
    
    // ----------------------------------------

    // Valid values: "OPEN", "RESOLVED"
    private String status = "OPEN"; 

    private LocalDateTime createdAt = LocalDateTime.now();

    // Relationship: Many Incidents belong to One User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // --- CONSTRUCTORS ---
    public Incident() {}

    public Incident(Double latitude, Double longitude, String description, User user) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.user = user;
    }

    // --- GETTERS AND SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}