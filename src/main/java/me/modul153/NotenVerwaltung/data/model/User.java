package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractUser;
import me.modul153.NotenVerwaltung.data.complex.UserComplex;
import me.modul153.NotenVerwaltung.managers.AdressManager;

@Data
public class User extends AbstractUser implements ISqlType {
    public int adressId;

    public User(int userId, String name, String nachname, String userName, int adressId) {
        super(userId, name, nachname, userName);
        this.adressId = adressId;
    }

    @Override
    public UserComplex toComplexType() {
        return new UserComplex(getUserId(), getFirstname(), getLastname(), getUsername(),
                AdressManager.getInstance().getComplexType(getAdressId()));
    }
    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }
}
