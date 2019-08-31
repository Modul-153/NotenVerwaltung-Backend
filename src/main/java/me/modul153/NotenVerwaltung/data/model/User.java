package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.IBuissnesObject;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractUser;
import me.modul153.NotenVerwaltung.data.response.UserResponse;
import me.modul153.NotenVerwaltung.managers.AdressManager;

@Data
public class User extends AbstractUser implements IBuissnesObject {
    public int adresseId;

    public User(int userId, String name, String nachname, String userName, int adresseId) {
        super(userId, name, nachname, userName);
        this.adresseId = adresseId;
    }

    @Override
    public UserResponse toResponse() {
        return new UserResponse(getUserId(), getName(), getNachname(), getUserName(), AdressManager.getInstance().getResponseType(getAdresseId()));
    }
    @Override
    public AbstractionType getType() {
        return AbstractionType.BUISSNES_OBJECT;
    }
}
