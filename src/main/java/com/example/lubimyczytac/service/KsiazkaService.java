package com.example.lubimyczytac.service;

import org.springframework.stereotype.Service;
import com.example.lubimyczytac.model.Ksiazka;
import com.example.lubimyczytac.repository.ListaKsiazek;
import com.example.lubimyczytac.exception.KsiazkaNotFoundException;

import java.util.List;

@Service
public class KsiazkaService {

    private final ListaKsiazek repository;

    public KsiazkaService(ListaKsiazek repository) {
        this.repository = repository;
    }

    // Pobierz wszystkie książki
    public List<Ksiazka> pobierzWszystkieKsiazki() {
        return repository.findAll();
    }

    // Pobierz jedną książkę po ID
    public Ksiazka pobierzKsiazke(Long idKsiazki) {
        return repository.findById(idKsiazki)
                .orElseThrow(() -> new KsiazkaNotFoundException(idKsiazki));
    }

    // Dodaj książkę
    public Ksiazka dodajKsiazke(Ksiazka ksiazka) {
        return repository.save(ksiazka);
    }

    // Edytuj książkę
    public Ksiazka edytujKsiazke(Long idKsiazki, Ksiazka nowaKsiazka) {

        Ksiazka ksiazka = repository.findById(idKsiazki)
                .orElseThrow(() -> new KsiazkaNotFoundException(idKsiazki));

        ksiazka.setTytul(nowaKsiazka.getTytul());
        ksiazka.setAutor(nowaKsiazka.getAutor());
        ksiazka.setOpis(nowaKsiazka.getOpis());
        ksiazka.setOcena(nowaKsiazka.getOcena());

        return repository.save(ksiazka);
    }

    // Usuń książkę
    public void usunKsiazke(Long idKsiazki) {

        if (!repository.existsById(idKsiazki)) {
            throw new KsiazkaNotFoundException(idKsiazki);
        }

        repository.deleteById(idKsiazki);
    }

    // Wyszukaj ksiązki po frazie
    public List<Ksiazka> wyszukajKsiazki(String fraza) {

        return repository
                .findByTytulContainingIgnoreCaseOrAutorContainingIgnoreCase(
                        fraza,
                        fraza);
    }
}