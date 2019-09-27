package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.data.abstracts.City;
import me.modul153.NotenVerwaltung.managers.CityManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/city")
public class CityController {
    @GetMapping("/get/{id}")
    public City get(@PathVariable Integer id) {
        City city = null;
        try {
            city = CityManager.getInstance().getComplex(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error getting City with id " + id + "\n" + e.getMessage());
        }
        if (city == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "City with id '" + id + "' not found.");
        }
        return city;
    }

    @PutMapping("/add/")
    public void add(@RequestBody City city) {
        try {
            CityManager.getInstance().updateComplex(city);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving City with id " + city.getCityId() + "\n" + e.getMessage());
        }
    }

    @PutMapping("/addMultiple/")
    public void addMultiple(@RequestBody City[] cities) {
        for (City city : cities) {
            try {
                CityManager.getInstance().updateComplex(city);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving City with id " + city.getCityId() + "\n" + e.getMessage());
            }
        }
    }
}
