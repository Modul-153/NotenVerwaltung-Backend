package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractAdresse;
import me.modul153.NotenVerwaltung.managers.OrtManager;
import me.modul153.NotenVerwaltung.data.response.AdresseComplex;

@Data
public class Adresse extends AbstractAdresse implements ISqlType {
    private int ortId;

    public Adresse(int adressId, String strasse, int nummer, int ortId) {
        super(adressId, strasse, nummer);
        this.ortId = ortId;
    }

    @Override
    public AdresseComplex toComplexType() {
        return new AdresseComplex(getAdressId(), getStrasse(), getNummer(), OrtManager.getInstance().getComplexType(ortId));
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }
}
