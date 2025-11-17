package com.example.gestionetudiant;

import com.example.gestionetudiant.controller.EtudiantController;
import com.example.gestionetudiant.model.Etudiant;
import com.example.gestionetudiant.repository.EtudiantRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EtudiantControllerTest {

    @Mock
    private EtudiantRepository repository;

    @InjectMocks
    private EtudiantController controller;

    @Test
    void testGetAllEtudiants() {
        when(repository.findAll()).thenReturn(List.of(new Etudiant()));
        List<Etudiant> result = controller.getAll(); // ✅ méthode corrigée
        assertFalse(result.isEmpty());
    }
}
