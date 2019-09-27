package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractStudent;
import me.modul153.NotenVerwaltung.data.complex.StudentComplex;
import me.modul153.NotenVerwaltung.managers.UserManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

@Data
public class Student extends AbstractStudent implements ISqlType {
    int userId;

    public Student(int studentId, int userId) {
        super(studentId);
        this.userId = userId;
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }

    @Override
    public StudentComplex toComplexType() {
        try {
            return new StudentComplex(getStudentId(), UserManager.getInstance().getSimple(getUserId()));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading user with id " + getUserId() + "\n" + e.getMessage());
        }
    }
}
