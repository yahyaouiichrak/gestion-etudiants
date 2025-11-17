package com.example.gestionetudiant.service;

import com.example.gestionetudiant.model.Etudiant;
import com.example.gestionetudiant.repository.EtudiantRepository;
import com.example.gestionetudiant.service.impl.EtudiantServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {

    @Mock
    private EtudiantRepository repository;

    @InjectMocks
    private EtudiantServiceImpl service;

    @Test
    void testGetAllEtudiants() {
        when(repository.findAll()).thenReturn(List.of(new Etudiant()));
        List<Etudiant> result = service.getAllEtudiants();
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetEtudiantById() {
        Etudiant e = new Etudiant();
        e.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(e));
        Optional<Etudiant> result = service.getEtudiantById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testCreateEtudiant() {
        Etudiant e = new Etudiant();
        when(repository.save(e)).thenReturn(e);
        Etudiant result = service.createEtudiant(e);
        assertNotNull(result);
    }

    @Test
    void testUpdateEtudiant() {
        Etudiant e = new Etudiant();
        e.setId(1L);
        when(repository.save(e)).thenReturn(e);
        Etudiant result = service.updateEtudiant(1L, e);
        assertEquals(1L, result.getId());
    }

    @Test
    void testDeleteEtudiant() {
        service.deleteEtudiant(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}
