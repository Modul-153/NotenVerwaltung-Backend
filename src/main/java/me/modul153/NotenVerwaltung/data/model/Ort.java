package me.modul153.NotenVerwaltung.data.model;

import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IBuissnesObject;
import me.modul153.NotenVerwaltung.api.IResopnseType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractOrt;
import me.modul153.NotenVerwaltung.data.response.OrtResponse;

public class Ort extends AbstractOrt implements IBuissnesObject {
    public Ort(int ortId, int zipCode, String name) {
        super(ortId, zipCode, name);
    }

    @Override
    public IResopnseType toResponse() {
        return new OrtResponse(getOrtId(), getZipCode(),getName());
    }
    @Override
    public AbstractionType getType() {
        return AbstractionType.BUISSNES_OBJECT;
    }
}
