package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractUser;
import me.modul153.NotenVerwaltung.data.complex.UserComplex;
import me.modul153.NotenVerwaltung.managers.CityManager;

@Data
public class User extends AbstractUser implements ISqlType {
    public int cityId;

    public User(int userId, String firstname, String lastname, String username, String street, int number, int cityId) {
        super(userId, firstname, lastname, username, street, number);
        this.cityId = cityId;
    }

    @Override
    public UserComplex toComplexType() {
        return new UserComplex(getUserId(), getFirstname(), getLastname(), getUsername(), getStreet(), getNumber(),
                CityManager.getInstance().getComplex(getCityId()));
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }
}
