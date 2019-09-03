package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractExam;
import me.modul153.NotenVerwaltung.data.complex.ExamComplex;
import me.modul153.NotenVerwaltung.managers.UserManager;

import java.util.Date;

@Data
public class Exam extends AbstractExam implements ISqlType {
    int userId;

    public Exam(int examId, int mark, Date date, int userId) {
        super(examId, mark, date);
        this.userId = userId;
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }

    @Override
    public ExamComplex toComplexType() {
        return new ExamComplex(getExamId(), getMark(), getDate(), UserManager.getInstance().getSqlType(userId));
    }
}
