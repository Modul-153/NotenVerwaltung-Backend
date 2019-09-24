package me.modul153.NotenVerwaltung.helper;

import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.Connection;

public class SqlHelper {
    public static Connection getConnection() {
        Counter.connectionCounter++;
        return ConnectionManager.getInstance().getMySQLConnection();
    }
}
