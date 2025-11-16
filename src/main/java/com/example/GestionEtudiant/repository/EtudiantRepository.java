package com.example.gestionetudiant.repository;

import com.example.gestionetudiant.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {}