package me.modul153.NotenVerwaltung.data.model;

import lombok.Data;
import me.modul153.NotenVerwaltung.api.AbstractionType;
import me.modul153.NotenVerwaltung.api.ISqlType;
import me.modul153.NotenVerwaltung.data.abstracts.AbstractSchool;
import me.modul153.NotenVerwaltung.data.complex.SchoolComplex;
import me.modul153.NotenVerwaltung.managers.CityManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

@Data
public class School extends AbstractSchool implements ISqlType {
    int cityId;

    public School(int schoolId, String schoolName, String street, int number, int cityId) {
        super(schoolId, schoolName, street, number);
        this.cityId = cityId;
    }

    @Override
    public AbstractionType getType() {
        return AbstractionType.SQL_TYPE;
    }

    @Override
    public SchoolComplex toComplexType() {
        try {
            return new SchoolComplex(getSchoolId(), getSchoolName(), getStreet(),getNumber(),CityManager.getInstance().getComplex(getCityId()));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading city with id " + getCityId() + "\n" + e.getMessage());
        }
    }
}
