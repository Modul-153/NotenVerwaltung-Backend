package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.data.abstracts.Subject;
import me.modul153.NotenVerwaltung.managers.SubjectManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    @GetMapping("/get/{id}")
    public Subject get(@PathVariable Integer id) {
        Subject subject = null;
        try {
            subject = SubjectManager.getInstance().getComplex(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting Subject with id " + id + "\n" + e.getMessage());
        }
        if (subject == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject with id '" + id + "' not found.");
        }
        return subject;
    }

    @PutMapping("/add/")
    public void add(@RequestBody Subject subject) {
        try {
            SubjectManager.getInstance().updateComplex(subject);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving Subject with id " + subject.getSubjectId() + "\n" + e.getMessage());
        }
    }

    @PutMapping("/addMultiple/")
    public void addMultiple(@RequestBody Subject[] cities) {
        for (Subject subject : cities) {
            try {
                SubjectManager.getInstance().updateComplex(subject);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving Subject with id " + subject.getSubjectId() + "\n" + e.getMessage());
            }
        }
    }
}
