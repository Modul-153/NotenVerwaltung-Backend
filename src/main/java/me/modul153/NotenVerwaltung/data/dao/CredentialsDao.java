package me.modul153.NotenVerwaltung.data.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CredentialsDao {
    String username;
    String password;
}
