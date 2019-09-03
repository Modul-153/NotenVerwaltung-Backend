package me.modul153.NotenVerwaltung.data.complex;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractAdresse;
import me.modul153.NotenVerwaltung.data.abstracts.City;
import me.modul153.NotenVerwaltung.data.model.Adress;

@Data
public class AdressComplex extends AbstractAdresse implements IComplexType {
    private City city;

    public AdressComplex(int adressId, String strasse, int nummer, City city) {
        super(adressId, strasse, nummer);
        this.city = city;
    }

    @Override
    public Adress toSqlType() {
        return new Adress(getAdressId(), getStreet(), getNumber(), city.getCityId());
    }
    @Override
    public AbstractionType getType() {
        return AbstractionType.COMPLEX_TYPE;
    }
}
