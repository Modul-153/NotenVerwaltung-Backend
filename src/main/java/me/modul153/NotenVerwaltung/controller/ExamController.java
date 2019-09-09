package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractExam;
import me.modul153.NotenVerwaltung.data.complex.ExamComplex;
import me.modul153.NotenVerwaltung.data.model.Exam;
import me.modul153.NotenVerwaltung.managers.ExamManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/exam")
public class ExamController {
    @GetMapping("/get/{id}")
    public Exam get(@PathVariable Integer id) {
        AbstractExam exam = ExamManager.getInstance().get(id);

        if (exam == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exam with id '" + id + "' not found.");
        }

        if (exam.getType() == AbstractionType.SQL_TYPE) {
            return (Exam) exam;
        } else if (exam.getType() == AbstractionType.COMPLEX_TYPE) {
            return ((ExamComplex) exam).toSqlType();
        } else {
            return null;
        }
    }

    @GetMapping("/getComplex/{id}")
    public ExamComplex getComplex(@PathVariable Integer id) {
        AbstractExam exam = ExamManager.getInstance().get(id);

        if (exam == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exam with id " + id + " not found.");
        }

        if (exam.getType() == AbstractionType.SQL_TYPE) {
            return ((Exam) exam).toComplexType();
        } else if (exam.getType() == AbstractionType.COMPLEX_TYPE) {
            return (ExamComplex) exam;
        } else {
            return null;
        }
    }

    @PutMapping("/add/")
    public void add(@RequestBody Exam exam) {
        addAbstractExam(exam);
    }

    @PutMapping("/addMultiple/")
    public void addMultiple(@RequestBody Exam[] exames) {
        for (Exam exam : exames) {
            addAbstractExam(exam);
        }
    }

    @PutMapping("/addComplex/")
    public void addComplex(@RequestBody ExamComplex exam) {
        addAbstractExam(exam);
    }

    @PutMapping("/addMultipleComplex/")
    public void addMultipleComplex(@RequestBody ExamComplex[] exames) {
        for (ExamComplex exam : exames) {
            addAbstractExam(exam);
        }
    }

    private void addAbstractExam(AbstractExam exam) {
        if (exam == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "exam can not be null.");
        }

        if (exam instanceof IComplexType) {
            if (!ExamManager.getInstance().validate(exam)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid exam.");
            }
        }

        ExamManager.getInstance().add(exam.getExamId(), exam);
    }
}
