package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractAdress;
import me.modul153.NotenVerwaltung.data.model.Adress;
import me.modul153.NotenVerwaltung.managers.AdressManager;
import me.modul153.NotenVerwaltung.data.complex.AdressComplex;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/adress")
public class AdressController {
    @GetMapping("/get/{id}")
    public Adress get(@PathVariable Integer id) {
        AbstractAdress adress = AdressManager.getInstance().get(id);

        if (adress == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adress with id '" + id + "' not found.");
        }

        if (adress.getType() == AbstractionType.SQL_TYPE) {
            return (Adress) adress;
        }else if(adress.getType() == AbstractionType.COMPLEX_TYPE) {
            return ((AdressComplex) adress).toSqlType();
        }else {
            return null;
        }
    }

    @GetMapping("/getComplex/{id}")
    public AdressComplex getComplex(@PathVariable Integer id) {
        AbstractAdress adress = AdressManager.getInstance().get(id);

        if (adress == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adress with id " + id + " not found.");
        }

        if (adress.getType() == AbstractionType.SQL_TYPE) {
            return ((Adress) adress).toComplexType();
        }else if(adress.getType() == AbstractionType.COMPLEX_TYPE) {
            return (AdressComplex) adress;
        }else {
            return null;
        }
    }

    @PutMapping("/add/")
    public void add(@RequestBody Adress adress) {
        addAbstractAdress(adress);
    }

    @PutMapping("/addMultiple/")
    public void addMultiple(@RequestBody Adress[] adresses) {
        for (Adress adress : adresses) {
            addAbstractAdress(adress);
        }
    }

    @PutMapping("/addComplex/")
    public void addComplex(@RequestBody AdressComplex adress) {
        addAbstractAdress(adress);
    }

    @PutMapping("/addMultipleComplex/")
    public void addMultipleComplex(@RequestBody AdressComplex[] adresses) {
        for (AdressComplex adress : adresses) {
            addAbstractAdress(adress);
        }
    }

    private void addAbstractAdress(AbstractAdress adress) {
        if (adress == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "adress can not be null.");
        }

        if (adress instanceof IComplexType) {
            if (!AdressManager.getInstance().validate(adress)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid adress.");
            }
        }

        AdressManager.getInstance().add(adress.getAdressId(), adress);
    }
}
