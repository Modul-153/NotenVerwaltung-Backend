package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractSchool;
import me.modul153.NotenVerwaltung.data.complex.SchoolComplex;
import me.modul153.NotenVerwaltung.data.model.School;
import me.modul153.NotenVerwaltung.managers.SchoolManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/school")
public class SchoolController {
    @GetMapping("/get/{id}")
    public School get(@PathVariable Integer id) {
        AbstractSchool school = SchoolManager.getInstance().get(id);

        if (school == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "School with id " + id + " not found.");
        }

        if (school.getType() == AbstractionType.SQL_TYPE) {
            return (School) school;
        } else if (school.getType() == AbstractionType.COMPLEX_TYPE) {
            return ((SchoolComplex) school).toSqlType();
        } else {
            return null;
        }
    }

    @GetMapping("/getComplex/{id}")
    public SchoolComplex getStudentComplex(@PathVariable Integer id) {
        AbstractSchool school = SchoolManager.getInstance().get(id);

        if (school == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "School with id " + id + " not found.");
        }

        if (school.getType() == AbstractionType.SQL_TYPE) {
            return ((School) school).toComplexType();
        } else if (school.getType() == AbstractionType.COMPLEX_TYPE) {
            return (SchoolComplex) school;
        } else {
            return null;
        }
    }

    @PutMapping("/add/")
    public void add(@RequestBody SchoolComplex school) {
        addAbstractSchool(school);
    }

    @PutMapping("/addComplex/")
    public void addComplex(@RequestBody SchoolComplex school) {
        addAbstractSchool(school);
    }

    @PutMapping("/addMultiple/")
    public void addMultiple(@Valid @RequestBody School[] schools) {
        for (School school : schools) {
            addAbstractSchool(school);
        }
    }

    @PutMapping("/addMultipleComplex/")
    public void addUsersComplex(@RequestBody SchoolComplex[] schools) {
        for (SchoolComplex schoolComplex : schools) {
            addAbstractSchool(schoolComplex);
        }
    }

    private void addAbstractSchool(AbstractSchool school) {
        if (!SchoolManager.getInstance().validate(school)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "School with id " + school.getSchoolId() + " not valid.");
        }

        SchoolManager.getInstance().add(school.getSchoolId(), school);
    }
}
