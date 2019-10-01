package me.modul153.NotenVerwaltung.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
public class School implements Serializable {
    private int schoolId;
    private String name;
    private String street;
    private int number;
    private City city;
}
