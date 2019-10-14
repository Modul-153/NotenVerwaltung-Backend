package me.modul153.NotenVerwaltung.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
@AllArgsConstructor
public class Exam implements Serializable {
    int examId;
    TeacherClassSubject teacherClassSubject;
    Date date;

}
