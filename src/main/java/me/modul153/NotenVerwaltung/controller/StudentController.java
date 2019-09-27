package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractStudent;
import me.modul153.NotenVerwaltung.data.complex.StudentComplex;
import me.modul153.NotenVerwaltung.data.model.Student;
import me.modul153.NotenVerwaltung.managers.StudentManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @GetMapping("/get/{id}")
    public Student getStudent(@PathVariable Integer id) {
        Student student = null;
        try {
            student = StudentManager.getInstance().getSimple(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting Teacher with id " + id + "\n" + e.getMessage());
        }

        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + id + " not found.");
        }

        return student;
    }

    @GetMapping("/getComplex/{id}")
    public StudentComplex getStudentComplex(@PathVariable Integer id) {
        StudentComplex student = null;
        try {
            student = StudentManager.getInstance().getComplex(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting Student with id " + id + "\n" + e.getMessage());
        }

        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + id + " not found.");
        }

        return student;
    }

    @PutMapping("/add/")
    public void addStudent(@RequestBody Student user) {
        addAbstractStudent(user);
    }

    @PutMapping("/addComplex/")
    public void addStudentComplex(@RequestBody StudentComplex user) {
        addAbstractStudent(user);
    }

    @PutMapping("/addMultiple/")
    public void addUsers(@Valid @RequestBody Student[] students) {
        for (Student student : students) {
            addAbstractStudent(student);
        }
    }

    @PutMapping("/addMultipleComplex/")
    public void addUsersComplex(@RequestBody StudentComplex[] students) {
        for (StudentComplex student : students) {
            addAbstractStudent(student);
        }
    }

    private void addAbstractStudent(AbstractStudent student) {
        if (!StudentManager.getInstance().validate(student)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student with id " + student.getStudentId() + " not valid.");
        }
        try {
            if (student instanceof ISqlType) {
                StudentManager.getInstance().updateSimple((Student) student);


            } else if (student instanceof IComplexType) {
                StudentManager.getInstance().updateComplex((StudentComplex) student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving Student with id " + student.getStudentId() + "\n" + e.getMessage());
        }
    }
}
