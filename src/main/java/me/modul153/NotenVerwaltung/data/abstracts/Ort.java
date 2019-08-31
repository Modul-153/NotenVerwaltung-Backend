package me.modul153.NotenVerwaltung.data.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IAbstract;
import me.modul153.NotenVerwaltung.api.IBuissnesObject;
import me.modul153.NotenVerwaltung.api.IResopnseType;

@AllArgsConstructor
@Data
public class Ort implements IAbstract, IBuissnesObject, IResopnseType {
    private int ortId;
    private int zipCode;
    private String name;

    @Override
    public AbstractionType getType() {
        return AbstractionType.BUISSNES_OBJECT;
    }

    @Override
    public IResopnseType toResponse() {
        return this;
    }

    @Override
    public IBuissnesObject toBusinessObject() {
        return this;
    }
}
