package com.example.gestionetudiant.controller;

import com.example.gestionetudiant.model.Etudiant;
import com.example.gestionetudiant.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantRepository repo;

    @GetMapping
    public List<Etudiant> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Etudiant getById(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PostMapping
    public Etudiant create(@RequestBody Etudiant e) {
        return repo.save(e);
    }

    @PutMapping("/{id}")
    public Etudiant update(@PathVariable Long id, @RequestBody Etudiant e) {
        e.setId(id);
        return repo.save(e);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}