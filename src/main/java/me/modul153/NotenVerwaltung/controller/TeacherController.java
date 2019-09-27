package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractTeacher;
import me.modul153.NotenVerwaltung.data.complex.TeacherComplex;
import me.modul153.NotenVerwaltung.data.model.Teacher;
import me.modul153.NotenVerwaltung.managers.TeacherManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @GetMapping("/get/{id}")
    public Teacher getTeacher(@PathVariable Integer id) {
        Teacher teacher = null;
        try {
            teacher = TeacherManager.getInstance().getSimple(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting Teacher with id " + id + "\n" + e.getMessage());
        }

        if (teacher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher with id " + id + " not found.");
        }

        return teacher;
    }

    @GetMapping("/getComplex/{id}")
    public TeacherComplex getTeacherComplex(@PathVariable Integer id) {
        TeacherComplex teacher = null;
        try {
            teacher = TeacherManager.getInstance().getComplex(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting Teacher with id " + id + "\n" + e.getMessage());
        }

        if (teacher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher with id " + id + " not found.");
        }

        return teacher;
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

        try {
            if (teacher instanceof ISqlType) {
                    TeacherManager.getInstance().updateComplex((TeacherComplex) teacher);
            }else if (teacher instanceof IComplexType) {
                TeacherManager.getInstance().updateComplex((TeacherComplex) teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving Teacher with id " + teacher.getTeacherId() + "\n" + e.getMessage());
        }
    }
}
