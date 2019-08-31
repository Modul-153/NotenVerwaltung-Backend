package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.data.model.Adresse;
import me.modul153.NotenVerwaltung.exceptions.BadRequestException;
import me.modul153.NotenVerwaltung.exceptions.NotFoundException;
import me.modul153.NotenVerwaltung.managers.AdressManager;
import me.modul153.NotenVerwaltung.managers.OrtManager;
import me.modul153.NotenVerwaltung.data.response.AdressResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adresse")
public class AdressController {
    @GetMapping("/getAdresse/{adressId}")
    public Adresse getAdresse(@PathVariable(value = "adressId") Integer id) {
        Adresse adresse = AdressManager.getInstance().getBuissnesObject(id);
        if (adresse == null) {
            throw new NotFoundException();
        }
        return adresse;
    }

    @PutMapping("/addAdresse/")
    public void addAdresse(@RequestBody Adresse adresse) {
        if (adresse == null) {
            throw new NotFoundException();
        }

        AdressManager.getInstance().add(adresse.getAdressId(), adresse);
    }
    @PutMapping("/addAdresseOrt/")
    public void addAdresse2(@RequestBody AdressResponse adresse) {
        if (adresse == null) {
            throw new NotFoundException();
        }

        if (adresse.getOrt() == null) {
            throw new BadRequestException();
        }

        OrtManager.getInstance().add(adresse.getOrt().getOrtId(), adresse.getOrt());
        AdressManager.getInstance().add(adresse.getAdressId(), adresse);
    }
}
