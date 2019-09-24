package me.modul153.NotenVerwaltung.data.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.modul153.NotenVerwaltung.api.IAbstract;

@AllArgsConstructor
@Data
public abstract class AbstractUser implements IAbstract {
    private int userId;
    private String firstname;
    private String lastname;
    private String username;
    private String street;
    private int number;
}
