package com.student.eurosafe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.eurosafe.entity.Incident;
import com.student.eurosafe.entity.User;
import com.student.eurosafe.repository.IncidentRepository;
import com.student.eurosafe.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IncidentController.class) // Focus only on the IncidentController
public class IncidentControllerTest {

    @Autowired
    private MockMvc mockMvc; // This tool acts like Postman

    @MockBean
    private IncidentRepository incidentRepository; // Fake Database

    @MockBean
    private UserRepository userRepository; // Fake User DB

    @Autowired
    private ObjectMapper objectMapper; // Converts Java Objects to JSON

    @Test
    @WithMockUser(username = "testuser") // Pretend we are logged in
    public void shouldCreateIncidentSuccessfully() throws Exception {
        
        // 1. Prepare Data
        User mockUser = new User("testuser", "test@email.com", "hashedpass");
        Incident incident = new Incident(52.52, 13.40, "Fire!", mockUser);

        // 2. Teach the Fake DB what to do (Mocking)
        // "When someone asks for 'testuser', give them this mockUser"
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        // "When someone saves an incident, just return the incident"
        when(incidentRepository.save(any(Incident.class))).thenReturn(incident);

        // 3. Perform the Request (The Robot clicks Send)
        mockMvc.perform(post("/api/incidents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(incident)) // Turn object into JSON text
                .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()) // Handle security token
        )
        // 4. Verify the Result
        .andExpect(status().isOk()); // Expect 200 OK
    }
}