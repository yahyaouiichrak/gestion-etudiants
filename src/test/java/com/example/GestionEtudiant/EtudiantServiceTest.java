import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.gestionetudiant.model.Etudiant;
import com.example.gestionetudiant.repository.EtudiantRepository;
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