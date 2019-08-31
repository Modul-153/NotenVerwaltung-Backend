package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IBuissnesObject;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractAdresse;
import me.modul153.NotenVerwaltung.managers.OrtManager;
import me.modul153.NotenVerwaltung.data.response.AdressResponse;

@Data
public class Adresse extends AbstractAdresse implements IBuissnesObject {
    private int ortId;

    public Adresse(int adressId, String strasse, int nummer, int ortId) {
        super(adressId, strasse, nummer);
        this.ortId = ortId;
    }

    @Override
    public AdressResponse toResponse() {
        return new AdressResponse(getAdressId(), getStrasse(), getNummer(), OrtManager.getInstance().getResponseType(ortId));
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.BUISSNES_OBJECT;
    }
}
