package me.modul153.NotenVerwaltung.data;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Administrator implements Serializable {
    int administratorId;
    User userId;
}
