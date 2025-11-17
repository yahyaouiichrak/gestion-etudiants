package com.example.gestionetudiant.controller;

import com.example.gestionetudiant.model.Etudiant;
import com.example.gestionetudiant.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantService service;

    @GetMapping
    public List<Etudiant> getAll() {
        return service.getAllEtudiants();
    }

    @GetMapping("/{id}")
    public Optional<Etudiant> getById(@PathVariable Long id) {
        return service.getEtudiantById(id);
    }

    @PostMapping
    public Etudiant create(@RequestBody Etudiant e) {
        return service.createEtudiant(e);
    }

    @PutMapping("/{id}")
    public Etudiant update(@PathVariable Long id, @RequestBody Etudiant e) {
        return service.updateEtudiant(id, e);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteEtudiant(id);
    }
}
