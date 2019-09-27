package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractUser;
import me.modul153.NotenVerwaltung.data.complex.UserComplex;
import me.modul153.NotenVerwaltung.managers.CityManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

@Data
public class User extends AbstractUser implements ISqlType {
    public int cityId;

    public User(int userId, String firstname, String lastname, String username, String street, int number,String password, int cityId) {
        super(userId, firstname, lastname, username, street, number, password);
        this.cityId = cityId;
    }

    @Override
    public UserComplex toComplexType() {
        try {
            return new UserComplex(getUserId(), getFirstname(), getLastname(), getUsername(), getStreet(), getNumber(), getPassword(),
                    CityManager.getInstance().getComplex(getCityId()));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading city with id " + getCityId() + "\n" + e.getMessage());
        }
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }
}
