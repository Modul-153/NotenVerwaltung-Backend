package me.modul153.NotenVerwaltung.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class TeacherClassSubject implements Serializable {
    private Subject subject;
    private User teacher;
    private int classId;
}
