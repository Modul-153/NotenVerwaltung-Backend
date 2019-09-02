package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractUser;
import me.modul153.NotenVerwaltung.data.response.UserComplex;
import me.modul153.NotenVerwaltung.managers.AdressManager;

@Data
public class User extends AbstractUser implements ISqlType {
    public int adresseId;

    public User(int userId, String name, String nachname, String userName, int adresseId) {
        super(userId, name, nachname, userName);
        this.adresseId = adresseId;
    }

    @Override
    public UserComplex toComplexType() {
        return new UserComplex(getUserId(), getName(), getNachname(), getUserName(), AdressManager.getInstance().getComplexType(getAdresseId()));
    }
    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }
}
