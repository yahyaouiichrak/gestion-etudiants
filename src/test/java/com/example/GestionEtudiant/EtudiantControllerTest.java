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