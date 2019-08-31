package me.modul153.NotenVerwaltung.data.response;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IResopnseType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractUser;
import me.modul153.NotenVerwaltung.data.model.User;

@Data
public class UserResponse extends AbstractUser implements IResopnseType {
    private AdressResponse adresse;

    public UserResponse(int userId, String name, String nachname, String userName, AdressResponse adresse) {
        super(userId, name, nachname, userName);
        this.adresse = adresse;
    }

    @Override
    public User toBusinessObject() {
        return new User(getUserId(), getName(), getNachname(), getUserName(), adresse.getAdressId());
    }
    @Override
    public AbstractionType getType() {
        return AbstractionType.RESPONSE_TYPE;
    }
}
