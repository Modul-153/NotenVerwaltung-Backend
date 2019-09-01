package me.modul153.NotenVerwaltung.api;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface IAbstract extends IDataObject {
    @JsonIgnore
    AbstractionType getType();
}
