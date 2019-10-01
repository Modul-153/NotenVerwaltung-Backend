package me.modul153.NotenVerwaltung.data;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Student implements Serializable {
    int studentId;
    int userId;
}
