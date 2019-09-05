package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractClass;
import me.modul153.NotenVerwaltung.data.complex.ClassComplex;
import me.modul153.NotenVerwaltung.data.model.Class;
import me.modul153.NotenVerwaltung.managers.ClassManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/class")
public class ClassController {
    @GetMapping("/get/{id}")
    public Class get(@PathVariable Integer id) {
        AbstractClass cls = ClassManager.getInstance().get(id);

        if (cls == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with id " + id + " not found.");
        }

        if (cls.getType() == AbstractionType.SQL_TYPE) {
            return (Class) cls;
        } else if (cls.getType() == AbstractionType.COMPLEX_TYPE) {
            return ((ClassComplex) cls).toSqlType();
        } else {
            return null;
        }
    }

    @GetMapping("/getComplex/{id}")
    public ClassComplex getStudentComplex(@PathVariable Integer id) {
        AbstractClass cls = ClassManager.getInstance().get(id);

        if (cls == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with id " + id + " not found.");
        }

        if (cls.getType() == AbstractionType.SQL_TYPE) {
            return ((Class) cls).toComplexType();
        } else if (cls.getType() == AbstractionType.COMPLEX_TYPE) {
            return (ClassComplex) cls;
        } else {
            return null;
        }
    }

    @PutMapping("/add/")
    public void add(@RequestBody ClassComplex cls) {
        addAbstractClass(cls);
    }

    @PutMapping("/addComplex/")
    public void addComplex(@RequestBody ClassComplex cls) {
        addAbstractClass(cls);
    }

    @PutMapping("/addMultiple/")
    public void addMultiple(@Valid @RequestBody Class[] clss) {
        for (Class cls : clss) {
            addAbstractClass(cls);
        }
    }

    @PutMapping("/addMultipleComplex/")
    public void addUsersComplex(@RequestBody ClassComplex[] clss) {
        for (ClassComplex clsComplex : clss) {
            addAbstractClass(clsComplex);
        }
    }

    private void addAbstractClass(AbstractClass cls) {
        if (!ClassManager.getInstance().validate(cls)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Class with id " + cls.getClassId() + " not valid.");
        }

        ClassManager.getInstance().add(cls.getClassId(), cls);
    }
}
