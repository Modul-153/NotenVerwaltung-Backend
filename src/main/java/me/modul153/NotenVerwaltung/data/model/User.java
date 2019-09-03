package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractUser;
import me.modul153.NotenVerwaltung.data.complex.UserComplex;
import me.modul153.NotenVerwaltung.managers.AdressManager;
import me.modul153.NotenVerwaltung.managers.RoleManager;

@Data
public class User extends AbstractUser implements ISqlType {
    public int adresseId;
    public int roleId;

    public User(int userId, String name, String nachname, String userName, int adresseId, int roleId) {
        super(userId, name, nachname, userName);
        this.adresseId = adresseId;
        this.roleId = roleId;
    }

    @Override
    public UserComplex toComplexType() {
        return new UserComplex(getUserId(), getFirstname(), getLastname(), getUsername(),
                AdressManager.getInstance().getComplexType(getAdresseId()),
                RoleManager.getInstance().getSqlType(getRoleId()));
    }
    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }
}
