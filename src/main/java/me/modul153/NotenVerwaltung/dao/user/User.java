package me.modul153.NotenVerwaltung.dao.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.modul153.NotenVerwaltung.dao.adresse.Adresse;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private int userId;
    private String name;
    private String nachname;
    private String userName;
    private Date birthday;
    private Adresse adresse;
}
