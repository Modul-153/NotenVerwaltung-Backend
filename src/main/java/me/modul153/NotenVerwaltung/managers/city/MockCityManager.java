package me.modul153.NotenVerwaltung.managers.city;

import me.modul153.NotenVerwaltung.data.City;

import java.util.HashMap;

public class MockCityManager implements ICityManager {

    @Override
    public HashMap<Integer, City> getMultiple(String... names) {
        return null;
    }

    @Override
    public HashMap<Integer, City> getAll() {
        return null;
    }

    @Override
    public HashMap<Integer, City> getMultiple(int... keys) {
        return null;
    }

    @Override
    public void updateMultiple(City... objects) {

    }

    @Override
    public void deleteMultiple(int... keys) {

    }
}
