package me.modul153.NotenVerwaltung.managers.school;

import me.modul153.NotenVerwaltung.data.School;

import java.util.HashMap;

public class MockSchoolManager implements ISchoolManager {

    @Override
    public HashMap<Integer, School> getMultiple(String... names) {
        return null;
    }

    @Override
    public HashMap<Integer, School> getAll() {
        return null;
    }

    @Override
    public HashMap<Integer, School> getMultiple(int... keys) {
        return null;
    }

    @Override
    public void updateMultiple(School... objects) {

    }

    @Override
    public void deleteMultiple(int... keys) {

    }
}
