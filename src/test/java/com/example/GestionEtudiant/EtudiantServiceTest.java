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