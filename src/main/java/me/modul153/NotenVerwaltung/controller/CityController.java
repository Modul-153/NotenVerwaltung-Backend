package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.data.abstracts.City;
import me.modul153.NotenVerwaltung.managers.CityManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/city")
public class CityController {
    @GetMapping("/get/{id}")
    public City get(@PathVariable Integer id) {
        City city = CityManager.getInstance().getSqlType(id);
        if (city == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "City with id '" + id +"' not found.");
        }
        return city;
    }

    @PutMapping("/add/")
    public void add(@RequestBody City city) {
        CityManager.getInstance().add(city.getCityId(), city);
    }

    @PutMapping("/addMultiple/")
    public void addMultiple(@RequestBody City[] cities) {
        for (City city : cities) {
            CityManager.getInstance().add(city.getCityId(), city);
        }
    }
}
