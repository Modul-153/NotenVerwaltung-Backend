package me.modul153.NotenVerwaltung.controller;

import me.modul153.NotenVerwaltung.services.Counter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/counter")
public class TestController {
    @GetMapping("/get/")
    public int getTeacher() {
        return Counter.connectionCounter;
    }
}
