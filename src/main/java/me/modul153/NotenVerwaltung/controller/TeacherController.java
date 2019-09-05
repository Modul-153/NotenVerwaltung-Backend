package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractTeacher;
import me.modul153.NotenVerwaltung.data.complex.TeacherComplex;
import me.modul153.NotenVerwaltung.data.model.Teacher;
import me.modul153.NotenVerwaltung.managers.TeacherManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @GetMapping("/get/{id}")
    public Teacher getTeacher(@PathVariable Integer id) {
        AbstractTeacher teacher = TeacherManager.getInstance().get(id);

        if (teacher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher with id " + id + " not found.");
        }

        if (teacher.getType() == AbstractionType.SQL_TYPE) {
            return (Teacher) teacher;
        } else if (teacher.getType() == AbstractionType.COMPLEX_TYPE) {
            return ((TeacherComplex) teacher).toSqlType();
        } else {
            return null;
        }
    }

    @GetMapping("/getComplex/{id}")
    public TeacherComplex getTeacherComplex(@PathVariable Integer id) {
        AbstractTeacher teacher = TeacherManager.getInstance().get(id);

        if (teacher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher with id " + id + " not found.");
        }

        if (teacher.getType() == AbstractionType.SQL_TYPE) {
            return ((Teacher) teacher).toComplexType();
        } else if (teacher.getType() == AbstractionType.COMPLEX_TYPE) {
            return (TeacherComplex) teacher;
        } else {
            return null;
        }
    }

    @PutMapping("/add/")
    public void addTeacher(@RequestBody Teacher teacher) {
        addAbstractTeacher(teacher);
    }

    @PutMapping("/addComplex/")
    public void addTeacherComplex(@RequestBody TeacherComplex teacher) {
        addAbstractTeacher(teacher);
    }

    @PutMapping("/addMultiple/")
    public void addTeachers(@Valid @RequestBody Teacher[] teachers) {
        for (Teacher teacher : teachers) {
            addAbstractTeacher(teacher);
        }
    }

    @PutMapping("/addMultipleComplex/")
    public void addTeachersComplex(@RequestBody TeacherComplex[] teachers) {
        for (TeacherComplex teacher : teachers) {
            addAbstractTeacher(teacher);
        }
    }

    private void addAbstractTeacher(AbstractTeacher teacher) {
        if (!TeacherManager.getInstance().validate(teacher)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Teacher with id " + teacher.getTeacherId() + " not valid.");
        }

        TeacherManager.getInstance().add(teacher.getTeacherId(), teacher);
    }
}
