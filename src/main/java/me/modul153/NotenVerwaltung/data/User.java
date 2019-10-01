package me.modul153.NotenVerwaltung.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class User implements Serializable {
    private int userId;
    private String firstName;
    private String lastName;
    private String nickName;
    private String street;
    private int number;
    private String password;
    private City city;
}
