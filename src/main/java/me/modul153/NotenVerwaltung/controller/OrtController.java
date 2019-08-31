package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.data.abstracts.Ort;
import me.modul153.NotenVerwaltung.exceptions.NotFoundException;
import me.modul153.NotenVerwaltung.managers.OrtManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ort")
public class OrtController {
    @GetMapping("/getOrt/{ortId}")
    public Ort getOrt(@PathVariable(value = "ortId") Integer id) {
        Ort ort = OrtManager.getInstance().getBuissnesObject(id);
        if (ort == null) {
            throw new NotFoundException();
        }
        return ort;
    }

    @PutMapping("/addOrt/")
    public void addOrt(@RequestBody Ort ort) {
        if (ort == null) {
            throw new NotFoundException();
        }
        OrtManager.getInstance().add(ort.getOrtId(), ort);
    }
}
