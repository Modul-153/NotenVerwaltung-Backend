package me.modul153.NotenVerwaltung.data.response;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IBuissnesObject;
import me.modul153.NotenVerwaltung.api.IResopnseType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractAdresse;
import me.modul153.NotenVerwaltung.data.model.Adresse;

@Data
public class AdressResponse extends AbstractAdresse implements IResopnseType {
    private OrtResponse ort;

    public AdressResponse(int adressId, String strasse, int nummer, OrtResponse ort) {
        super(adressId, strasse, nummer);
        this.ort = ort;
    }

    @Override
    public IBuissnesObject toBusinessObject() {
        return new Adresse(getAdressId(), getStrasse(), getNummer(), ort.getOrtId());
    }
    @Override
    public AbstractionType getType() {
        return AbstractionType.RESPONSE_TYPE;
    }
}
