package me.modul153.NotenVerwaltung.data.complex;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractUser;
import me.modul153.NotenVerwaltung.data.abstracts.City;
import me.modul153.NotenVerwaltung.data.model.User;

@Data
public class UserComplex extends AbstractUser implements IComplexType {
    private City city;

    public UserComplex(int userId, String firstname, String lastname, String username, String street, int number, City city) {
        super(userId, firstname, lastname, username, street, number);
        this.city = city;
    }

    @Override
    public User toSqlType() {
        return new User(getUserId(), getFirstname(), getLastname(), getUsername(), getStreet(), getNumber(), city.getCityId());
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.COMPLEX_TYPE;
    }
}
