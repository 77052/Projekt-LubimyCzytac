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
        ksiazka.setTytul(duzaLitera(ksiazka.getTytul()));
        ksiazka.setAutor(duzaLitera(ksiazka.getAutor()));
        return repository.save(ksiazka);
    }

    // Edytuj książkę
    public Ksiazka edytujKsiazke(Long idKsiazki, Ksiazka nowaKsiazka) {

        Ksiazka ksiazka = repository.findById(idKsiazki)
                .orElseThrow(() -> new KsiazkaNotFoundException(idKsiazki));

        ksiazka.setTytul(duzaLitera(nowaKsiazka.getTytul()));
        ksiazka.setAutor(duzaLitera(nowaKsiazka.getAutor()));
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

    // Zamiana pierwszej litery na dużą
    private String duzaLitera(String tekst) {
        if (tekst == null || tekst.isBlank()) {
            return tekst;
        }

        return tekst.substring(0, 1).toUpperCase()
                + tekst.substring(1).toLowerCase();
    }
}