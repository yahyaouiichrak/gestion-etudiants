package com.example.gestionetudiant;

import com.example.gestionetudiant.model.Etudiant;
import com.example.gestionetudiant.repository.EtudiantRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EtudiantServiceTest {

    @Autowired
    private EtudiantRepository repository;

    @Test
    void testCreateEtudiant() {
        Etudiant e = new Etudiant();
        e.setNom("Ali");
        e.setPrenom("Ahmed");
        e.setEmail("ali@example.com");
        e.setNiveau("Licence");

        Etudiant saved = repository.save(e);
        assertNotNull(saved.getId());
    }
}