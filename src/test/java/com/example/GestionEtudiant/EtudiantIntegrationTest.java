import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EtudiantIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetEtudiantsEndpoint() {
        ResponseEntity<String> response = restTemplate.getForEntity("/etudiants", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}