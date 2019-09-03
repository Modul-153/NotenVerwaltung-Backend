package me.modul153.NotenVerwaltung.data.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.modul153.NotenVerwaltung.api.IAbstract;

import java.util.Date;

@AllArgsConstructor
@Data
public abstract class AbstractExam implements IAbstract {
    int examId;
    int mark;
    Date date;
}
