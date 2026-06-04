package com.example.lubimyczytac.exception;

public class KsiazkaNotFoundException extends RuntimeException {

    public KsiazkaNotFoundException(Long idKsiazki) {
        super("Nie znaleziono książki o id: " + idKsiazki);
    }
}