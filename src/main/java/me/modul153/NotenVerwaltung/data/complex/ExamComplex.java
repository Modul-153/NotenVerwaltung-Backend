package me.modul153.NotenVerwaltung.data.complex;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractExam;
import me.modul153.NotenVerwaltung.data.model.Exam;
import me.modul153.NotenVerwaltung.data.model.User;

import java.util.Date;

@Data
public class ExamComplex extends AbstractExam implements IComplexType {
    User user;

    public ExamComplex(int examId, int mark, Date date, User user) {
        super(examId, mark, date);
        this.user = user;
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.COMPLEX_TYPE;
    }

    @Override
    public Exam toSqlType() {
        return new Exam(getExamId(), getMark(), getDate(), getUser().getUserId());
    }
}
