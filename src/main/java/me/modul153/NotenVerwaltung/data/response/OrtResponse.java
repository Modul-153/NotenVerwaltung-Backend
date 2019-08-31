package me.modul153.NotenVerwaltung.data.response;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IResopnseType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractOrt;
import me.modul153.NotenVerwaltung.data.model.Ort;

@Data
public class OrtResponse extends AbstractOrt implements IResopnseType {
    public OrtResponse(int ortId, int zipCode, String name) {
        super(ortId, zipCode, name);
    }

    @Override
    public Ort toBusinessObject() {
        return new Ort(getOrtId(), getZipCode(), getName());
    }
    @Override
    public AbstractionType getType() {
        return AbstractionType.RESPONSE_TYPE;
    }
}
