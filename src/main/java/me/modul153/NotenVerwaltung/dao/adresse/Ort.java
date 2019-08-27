package me.modul153.NotenVerwaltung.dao.adresse;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Ort {
    private int ortId;
    private int zipCode;
    private String name;
}
