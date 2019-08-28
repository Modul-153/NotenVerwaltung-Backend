package me.modul153.NotenVerwaltung.services;

import net.myplayplanet.services.connection.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlSetup {
    public void setup() {
        try {
            createUser();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void createUser() throws SQLException {
        createOrtTable();
        createRole();

        createAdresseTable();
        createUserTable();
        createRoleUser();
        createUserAccount();
        createUserAuth();
    }


    private void createOrtTable() throws SQLException {
        PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                "CREATE TABLE IF NOT EXISTS `notenverwaltung`.`ort` " +
                        "(" +
                        " `ort_id` INT NOT NULL AUTO_INCREMENT , " +
                        "`zipcode` INT NOT NULL , " +
                        "`name` VARCHAR(100) NOT NULL , " +
                        "PRIMARY KEY (`ort_id`)" +
                        ") " +
                        "ENGINE = InnoDB;");
        statement.executeUpdate();
    }

    private void createAdresseTable() throws SQLException {
        PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                "CREATE TABLE IF NOT EXISTS `notenverwaltung`.`adresse` " +
                        "(" +
                        " `adress_id` INT NOT NULL AUTO_INCREMENT ," +
                        " `strasse` VARCHAR(100) NOT NULL ," +
                        " `nummer` INT NOT NULL ," +
                        " `ort_id` INT NOT NULL ," +
                        " PRIMARY KEY (`adress_id`)," +
                        " CONSTRAINT `fk_ort` FOREIGN KEY (`ort_id`) REFERENCES `ort`(`ort_id`) ON DELETE RESTRICT ON UPDATE RESTRICT" +
                        ")" +
                        " ENGINE = InnoDB;");
        statement.executeUpdate();
    }
    private void createUserTable() throws SQLException {
        PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                "CREATE TABLE iF NOT EXISTS `notenverwaltung`.`user` " +
                        "( " +
                        "`user_id` INT NOT NULL AUTO_INCREMENT , " +
                        "`vorname` VARCHAR(100) NOT NULL , " +
                        "`nachname` VARCHAR(100) NOT NULL , " +
                        "`username` VARCHAR(100) NOT NULL , " +
                        "`adress_id` INT NOT NULL , " +
                        "PRIMARY KEY (`user_id`), " +
                        "CONSTRAINT `fk_adresse` FOREIGN KEY (`adress_id`) REFERENCES `adresse`(`adress_id`) ON DELETE RESTRICT ON UPDATE RESTRICT" +
                        ") " +
                        "ENGINE = InnoDB;");
        statement.executeUpdate();
    }

    private void createRole() throws SQLException {
        PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                "CREATE TABLE IF NOT EXISTS `notenverwaltung`.`role` " +
                        "( " +
                        "`role_id` INT NOT NULL AUTO_INCREMENT , " +
                        "`name` VARCHAR(100) NOT NULL , " +
                        "PRIMARY KEY (`role_id`)" +
                        ")" +
                        " ENGINE = InnoDB;");
        statement.executeUpdate();
    }

    private void createRoleUser() throws SQLException {
        PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                "CREATE TABLE IF NOT EXISTS `notenverwaltung`.`user_roles` " +
                        "( " +
                        "`user_id` INT NOT NULL AUTO_INCREMENT , " +
                        "`role_id` INT NOT NULL , " +
                        "PRIMARY KEY (`user_id`), " +
                        "CONSTRAINT `role_id_user_role` FOREIGN KEY (`role_id`) REFERENCES `role`(`role_id`) ON DELETE RESTRICT ON UPDATE RESTRICT, " +
                        "CONSTRAINT `user_id_user_role` FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT" +
                        ")" +
                        " ENGINE = InnoDB;");
        statement.executeUpdate();
    }

    private void createUserAuth() throws SQLException {
        PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                "CREATE TABLE IF NOT EXISTS `notenverwaltung`.`user_authentication` " +
                        "( " +
                        "`user_id` INT NOT NULL , " +
                        "`token` VARCHAR(255) NOT NULL , " +
                        "PRIMARY KEY (`user_id`), " +
                        "CONSTRAINT `user_id_auth` FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT" +
                        ") " +
                        "ENGINE = InnoDB;");
        statement.executeUpdate();
    }

    private void createUserAccount() throws SQLException {
        PreparedStatement statement = ConnectionManager.getInstance().getMySQLConnection().prepareStatement(
                "CREATE TABLE IF NOT EXISTS `notenverwaltung`.`user_accounts` " +
                        "( " +
                        "`user_id` INT NOT NULL , " +
                        "`password` VARCHAR(255) NOT NULL , " +
                        "PRIMARY KEY (`user_id`), " +
                        "CONSTRAINT `user_id_account` FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT" +
                        ") " +
                        "ENGINE = InnoDB;");
        statement.executeUpdate();
    }
}
