package com.example.gestionetudiant.integration;

import com.example.gestionetudiant.model.Etudiant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EtudiantIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetEtudiantsEndpoint() {
        ResponseEntity<Etudiant[]> response =
                restTemplate.getForEntity("/etudiants", Etudiant[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
