package me.modul153.NotenVerwaltung.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class City implements Serializable {
    int cityId;
    String name;
    int zipcode;
}
