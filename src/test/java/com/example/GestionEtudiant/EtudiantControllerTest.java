import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

importudiantController;
import com.example.gestionetudiant.repository.EtudiantRepository;
import com.example.gestionetudiant.model.Etudiant;
@ExtendWith(MockitoExtension.class)
class EtudiantControllerTest {

    @Mock
    private EtudiantRepository repository;

    @InjectMocks
    private EtudiantController controller;

    @Test
    void testGetEtudiants() {
        when(repository.findAll()).thenReturn(List.of(new Etudiant()));
        List<Etudiant> result = controller.getEtudiants();
        assertFalse(result.isEmpty());
    }
}