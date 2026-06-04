package com.example.lubimyczytac.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "ksiazki")
public class Ksiazka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKsiazki;

    @NotBlank(message = "Tytuł jest wymagany")
    @Column(nullable = false)
    private String tytul;

    @NotBlank(message = "Autor jest wymagany")
    @Column(nullable = false)
    private String autor;

    @Column(length = 2000)
    private String opis;

    @Min(value = 1, message = "Ocena musi być z zakresu 1-10")
    @Max(value = 10, message = "Ocena musi być z zakresu 1-10")
    private Integer ocena;

    public Ksiazka() {
    }

    public Ksiazka(Long idKsiazki, String tytul, String autor, String opis, Integer ocena) {
        this.idKsiazki = idKsiazki;
        this.tytul = tytul;
        this.autor = autor;
        this.opis = opis;
        this.ocena = ocena;
    }

    public Long getIdKsiazki() {
        return idKsiazki;
    }

    public void setIdKsiazki(Long idKsiazki) {
        this.idKsiazki = idKsiazki;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }
}


