package me.modul153.NotenVerwaltung.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Class implements Serializable {
    private int classId;
    private String name;
    private int schoolId;
    private User primaryTeacher;
}
