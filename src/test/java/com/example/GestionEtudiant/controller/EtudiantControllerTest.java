package com.example.gestionetudiant.controller;

import com.example.gestionetudiant.model.Etudiant;
import com.example.gestionetudiant.service.EtudiantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EtudiantControllerTest {

    @Mock
    private EtudiantService service;

    @InjectMocks
    private EtudiantController controller;

    @Test
    void testGetAllEtudiants() {
        Etudiant e = new Etudiant();
        e.setId(1L);
        e.setNom("Test");
        e.setPrenom("Unit");
        when(service.getAllEtudiants()).thenReturn(Collections.singletonList(e));

        List<Etudiant> result = controller.getAll();
        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getNom());
    }
}
