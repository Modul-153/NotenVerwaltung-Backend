package me.modul153.NotenVerwaltung.api;

import com.google.gson.Gson;

import java.io.Serializable;

public interface IDataObject extends Serializable {
    default String toJson() {
        return new Gson().toJson(this);
    }
}
