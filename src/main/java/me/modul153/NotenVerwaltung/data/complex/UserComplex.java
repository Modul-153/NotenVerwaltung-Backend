package me.modul153.NotenVerwaltung.data.complex;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IComplexType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractUser;
import me.modul153.NotenVerwaltung.data.model.User;

@Data
public class UserComplex extends AbstractUser implements IComplexType {
    private AdressComplex adresse;

    public UserComplex(int userId, String name, String nachname, String userName, AdressComplex adresse) {
        super(userId, name, nachname, userName);
        this.adresse = adresse;
    }

    @Override
    public User toSqlType() {
        return new User(getUserId(), getFirstname(), getLastname(), getUsername(), adresse.getAdressId());
    }
    @Override
    public AbstractionType getType() {
        return AbstractionType.COMPLEX_TYPE;
    }
}
