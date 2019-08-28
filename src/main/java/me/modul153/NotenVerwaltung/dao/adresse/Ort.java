package me.modul153.NotenVerwaltung.dao.adresse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Ort implements Serializable {
    private int ortId;
    private int zipCode;
    private String name;
}
