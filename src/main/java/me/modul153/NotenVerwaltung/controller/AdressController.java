package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractAdresse;
import me.modul153.NotenVerwaltung.data.model.Adresse;
import me.modul153.NotenVerwaltung.exceptions.BadRequestException;
import me.modul153.NotenVerwaltung.exceptions.NotFoundException;
import me.modul153.NotenVerwaltung.managers.AdressManager;
import me.modul153.NotenVerwaltung.managers.OrtManager;
import me.modul153.NotenVerwaltung.data.response.AdresseComplex;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("(/api/adresse")
public class AdressController {
    @GetMapping("/getAdresse")
    public Adresse getAdresse(@RequestParam(value = "adressId") Integer id) {
        AbstractAdresse adresse = AdressManager.getInstance().get(id);

        if (adresse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adresse with id " + id + " not found.");
        }

        if (adresse.getType() == AbstractionType.SQL_TYPE) {
            return (Adresse) adresse;
        }else if(adresse.getType() == AbstractionType.COMPLEX_TYPE) {
            return ((AdresseComplex) adresse).toSqlType();
        }else {
            return null;
        }
    }

    @GetMapping("/getAdresseComplex")
    public AdresseComplex getAdresseComplex(@RequestParam(value = "adressId") Integer id) {
        AbstractAdresse adresse = AdressManager.getInstance().get(id);

        if (adresse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adresse with id " + id + " not found.");
        }

        if (adresse.getType() == AbstractionType.SQL_TYPE) {
            return ((Adresse) adresse).toComplexType();
        }else if(adresse.getType() == AbstractionType.COMPLEX_TYPE) {
            return (AdresseComplex) adresse;
        }else {
            return null;
        }
    }

    @PutMapping("/addAdresse/")
    public void addAdresse(@RequestBody Adresse adresse) {
        if (adresse == null) {
            throw new NotFoundException();
        }

        AdressManager.getInstance().add(adresse.getAdressId(), adresse);
    }
    @PutMapping("/addAdresseComplex/")
    public void addAdresse2(@RequestBody AdresseComplex adresse) {
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
