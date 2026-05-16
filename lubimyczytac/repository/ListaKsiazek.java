package com.example.lubimyczytac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.lubimyczytac.model.Ksiazka;

import java.util.List;

public interface ListaKsiazek extends JpaRepository<Ksiazka, Long> {

    List<Ksiazka> findByTytulContainingIgnoreCaseOrAutorContainingIgnoreCase(
            String tytul,
            String autor);
}