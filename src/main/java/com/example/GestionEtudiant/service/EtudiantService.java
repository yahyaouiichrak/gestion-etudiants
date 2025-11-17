package com.example.gestionetudiant.service;

import com.example.gestionetudiant.model.Etudiant;

import java.util.List;
import java.util.Optional;

public interface EtudiantService {
    List<Etudiant> getAllEtudiants();
    Optional<Etudiant> getEtudiantById(Long id);
    Etudiant createEtudiant(Etudiant e);
    Etudiant updateEtudiant(Long id, Etudiant e);
    void deleteEtudiant(Long id);
}
