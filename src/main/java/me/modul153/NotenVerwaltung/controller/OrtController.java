package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.data.abstracts.City;
import me.modul153.NotenVerwaltung.managers.CityManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/ort")
public class OrtController {
    @GetMapping("/getOrt")
    public City getOrt(@RequestParam(value = "ortId") Integer id) {
        City city = CityManager.getInstance().getSqlType(id);
        if (city == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ort with id not found " + id + ".");
        }
        return city;
    }

    @PutMapping("/addOrt/")
    public void addOrt(@RequestBody City city) {
        CityManager.getInstance().add(city.getCityId(), city);
    }
}
