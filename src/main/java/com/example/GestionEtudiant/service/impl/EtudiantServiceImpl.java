package com.example.gestionetudiant.service.impl;

import com.example.gestionetudiant.model.Etudiant;
import com.example.gestionetudiant.repository.EtudiantRepository;
import com.example.gestionetudiant.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantServiceImpl implements EtudiantService {

    @Autowired
    private EtudiantRepository repository;

    @Override
    public List<Etudiant> getAllEtudiants() {
        return repository.findAll();
    }

    @Override
    public Optional<Etudiant> getEtudiantById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Etudiant createEtudiant(Etudiant e) {
        return repository.save(e);
    }

    @Override
    public Etudiant updateEtudiant(Long id, Etudiant e) {
        e.setId(id);
        return repository.save(e);
    }

    @Override
    public void deleteEtudiant(Long id) {
        repository.deleteById(id);
    }
}
