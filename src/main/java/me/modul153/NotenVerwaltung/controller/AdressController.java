package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractAdresse;
import me.modul153.NotenVerwaltung.data.model.Adress;
import me.modul153.NotenVerwaltung.exceptions.BadRequestException;
import me.modul153.NotenVerwaltung.exceptions.NotFoundException;
import me.modul153.NotenVerwaltung.managers.AdressManager;
import me.modul153.NotenVerwaltung.managers.CityManager;
import me.modul153.NotenVerwaltung.data.complex.AdressComplex;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/adresse")
public class AdressController {
    @GetMapping("/getAdresse")
    public Adress getAdresse(@RequestParam(value = "adressId") Integer id) {
        AbstractAdresse adresse = AdressManager.getInstance().get(id);

        if (adresse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adresse with id " + id + " not found.");
        }

        if (adresse.getType() == AbstractionType.SQL_TYPE) {
            return (Adress) adresse;
        }else if(adresse.getType() == AbstractionType.COMPLEX_TYPE) {
            return ((AdressComplex) adresse).toSqlType();
        }else {
            return null;
        }
    }

    @GetMapping("/getAdresseComplex")
    public AdressComplex getAdresseComplex(@RequestParam(value = "adressId") Integer id) {
        AbstractAdresse adresse = AdressManager.getInstance().get(id);

        if (adresse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adresse with id " + id + " not found.");
        }

        if (adresse.getType() == AbstractionType.SQL_TYPE) {
            return ((Adress) adresse).toComplexType();
        }else if(adresse.getType() == AbstractionType.COMPLEX_TYPE) {
            return (AdressComplex) adresse;
        }else {
            return null;
        }
    }

    @PutMapping("/addAdresse/")
    public void addAdresse(@RequestBody Adress adress) {
        if (adress == null) {
            throw new NotFoundException();
        }

        AdressManager.getInstance().add(adress.getAdressId(), adress);
    }
    @PutMapping("/addAdresseComplex/")
    public void addAdresse2(@RequestBody AdressComplex adresse) {
        if (adresse == null) {
            throw new NotFoundException();
        }

        if (adresse.getCity() == null) {
            throw new BadRequestException();
        }

        CityManager.getInstance().add(adresse.getCity().getCityId(), adresse.getCity());
        AdressManager.getInstance().add(adresse.getAdressId(), adresse);
    }
}
