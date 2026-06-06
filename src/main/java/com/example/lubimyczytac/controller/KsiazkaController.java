package com.example.lubimyczytac.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.example.lubimyczytac.model.Ksiazka;
import com.example.lubimyczytac.service.KsiazkaService;

import java.util.List;

@RestController
@RequestMapping("/api/ksiazki")
@CrossOrigin

public class KsiazkaController {

    private final KsiazkaService service;

    public KsiazkaController(KsiazkaService service) {
        this.service = service;
    }

    // Pobierz wszystkie książki
    @GetMapping
    public List<Ksiazka> pobierzWszystkieKsiazki() {
        return service.pobierzWszystkieKsiazki();
    }

    // Pobierz jedną książkę po identyfikatorze książki: idKsiazki
    @GetMapping("/{idKsiazki}")
    public Ksiazka pobierzKsiazke(@PathVariable Long idKsiazki) {
        return service.pobierzKsiazke(idKsiazki);
    }

    // Dodaj książkę
    @PostMapping
    public Object dodajKsiazke(@Valid @RequestBody Ksiazka ksiazka,
                               org.springframework.validation.BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(result.getFieldError().getDefaultMessage());
        }

        return service.dodajKsiazke(ksiazka);
    }

    // Edytuj książkę po identyfikatorze książki: idKsiazki
    @PutMapping("/{idKsiazki}")
    public Ksiazka edytujKsiazke(
            @PathVariable Long idKsiazki,
            @Valid @RequestBody Ksiazka ksiazka) {

        return service.edytujKsiazke(idKsiazki, ksiazka);
    }

    // Usuń książkę po identyfikatorze książki: idKsiazki
    @DeleteMapping("/{idKsiazki}")
    public void usunKsiazke(@PathVariable Long idKsiazki) {
        service.usunKsiazke(idKsiazki);
    }

    // Wyszukaj księżki po frazie
    @GetMapping("/search")
    public List<Ksiazka> wyszukajKsiazki(@RequestParam String fraza) {
        return service.wyszukajKsiazki(fraza);
    }
}