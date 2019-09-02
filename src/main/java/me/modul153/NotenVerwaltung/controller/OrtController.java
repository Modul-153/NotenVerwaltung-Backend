package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.data.abstracts.Ort;
import me.modul153.NotenVerwaltung.managers.OrtManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/ort")
public class OrtController {
    @GetMapping("/getOrt")
    public Ort getOrt(@RequestParam(value = "ortId") Integer id) {
        Ort ort = OrtManager.getInstance().getSqlType(id);
        if (ort == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ort with id not found " + id + ".");
        }
        return ort;
    }

    @PutMapping("/addOrt/")
    public void addOrt(@RequestBody Ort ort) {
        OrtManager.getInstance().add(ort.getOrtId(), ort);
    }
}
