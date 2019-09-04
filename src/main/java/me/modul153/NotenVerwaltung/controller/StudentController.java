package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractStudent;
import me.modul153.NotenVerwaltung.data.complex.StudentComplex;
import me.modul153.NotenVerwaltung.data.model.Student;
import me.modul153.NotenVerwaltung.managers.StudentManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @GetMapping("/get")
    public Student getStudent(@RequestParam(value = "studentId") Integer id) {
        AbstractStudent student = StudentManager.getInstance().get(id);

        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + id + " not found.");
        }

        if (student.getType() == AbstractionType.SQL_TYPE) {
            return (Student) student;
        }else if(student.getType() == AbstractionType.COMPLEX_TYPE) {
            return ((StudentComplex) student).toSqlType();
        }else {
            return null;
        }
    }
    @GetMapping("/getComplex")
    public StudentComplex getStudentComplex(@RequestParam(value = "studentId") Integer id) {
        AbstractStudent student = StudentManager.getInstance().get(id);

        if (student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with id " + id + " not found.");
        }

        if (student.getType() == AbstractionType.SQL_TYPE) {
            return ((Student) student).toComplexType();
        }else if(student.getType() == AbstractionType.COMPLEX_TYPE) {
            return (StudentComplex) student;
        }else {
            return null;
        }
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

        StudentManager.getInstance().add(student.getStudentId(), student);
    }
}
