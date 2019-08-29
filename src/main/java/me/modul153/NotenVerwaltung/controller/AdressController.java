package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.dao.AdresseOrt;
import me.modul153.NotenVerwaltung.dao.adresse.Adresse;
import me.modul153.NotenVerwaltung.dao.adresse.Ort;
import me.modul153.NotenVerwaltung.exceptions.BadRequestException;
import me.modul153.NotenVerwaltung.exceptions.ForeignKeyNotFoundException;
import me.modul153.NotenVerwaltung.exceptions.NotFoundException;
import me.modul153.NotenVerwaltung.managers.AdressManager;
import me.modul153.NotenVerwaltung.managers.OrtManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adresse")
public class AdressController {
    @GetMapping("/getAdresse/{adressId}")
    public Adresse getAdresse(@PathVariable(value = "adressId") Integer id) {
        Adresse adresse = AdressManager.getInstance().getAdresse(id);
        if (adresse == null) {
            throw new NotFoundException();
        }
        return adresse;
    }

    @GetMapping("/addAdresse/")
    public void addAdresse(@RequestBody Adresse adresse) {
        if (adresse == null) {
            throw new NotFoundException();
        }

        if (adresse.getOrt() == null) {
            throw new ForeignKeyNotFoundException();
        }

        AdressManager.getInstance().addAdresse(adresse);
    }
    @GetMapping("/addAdresseOrt/")
    public void addAdresse2(@RequestBody AdresseOrt adresseOrt) {
        if (adresseOrt == null) {
            throw new NotFoundException();
        }

        if (!adresseOrt.valid()) {
            throw new BadRequestException();
        }

        OrtManager.getInstance().addOrt(adresseOrt.getOrt());
        AdressManager.getInstance().addAdresse(adresseOrt.getAdresse());
    }
}
