package me.modul153.NotenVerwaltung.data.complex;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractAdresse;
import me.modul153.NotenVerwaltung.data.abstracts.Ort;
import me.modul153.NotenVerwaltung.data.model.Adresse;

@Data
public class AdresseComplex extends AbstractAdresse implements IComplexType {
    private Ort ort;

    public AdresseComplex(int adressId, String strasse, int nummer, Ort ort) {
        super(adressId, strasse, nummer);
        this.ort = ort;
    }

    @Override
    public Adresse toSqlType() {
        return new Adresse(getAdressId(), getStrasse(), getNummer(), ort.getOrtId());
    }
    @Override
    public AbstractionType getType() {
        return AbstractionType.COMPLEX_TYPE;
    }
}
