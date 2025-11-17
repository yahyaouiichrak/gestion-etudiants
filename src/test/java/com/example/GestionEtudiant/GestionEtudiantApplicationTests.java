package com.example.gestionetudiant;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test") // Utilise H2 pour les tests
class GestionEtudiantApplicationTests {

    @Test
    void contextLoads() {
    }
}
