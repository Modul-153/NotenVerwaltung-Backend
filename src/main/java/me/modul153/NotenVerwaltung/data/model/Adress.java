package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractAdress;
import me.modul153.NotenVerwaltung.managers.CityManager;
import me.modul153.NotenVerwaltung.data.complex.AdressComplex;

@Data
public class Adress extends AbstractAdress implements ISqlType {
    private int cityId;

    public Adress(int adressId, String strasse, int nummer, int cityId) {
        super(adressId, strasse, nummer);
        this.cityId = cityId;
    }

    @Override
    public AdressComplex toComplexType() {
        return new AdressComplex(getAdressId(), getStreet(), getNumber(), CityManager.getInstance().getComplexType(cityId));
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }
}
