package me.modul153.NotenVerwaltung.data;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Teacher implements Serializable {
    int teacherId;
    User user;
}
