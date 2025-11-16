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