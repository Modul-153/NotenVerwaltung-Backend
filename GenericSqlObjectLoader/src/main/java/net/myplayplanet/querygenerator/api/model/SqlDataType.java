package net.myplayplanet.querygenerator.api.model;

import lombok.Getter;

/**
 * Complex type for SQLDataTypes to allow the length of for example a char to be variable.
 */
@Getter
public class SqlDataType {
    SqlDataTypes type;
    int amount;

    public SqlDataType(SqlDataTypes type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public SqlDataType(SqlDataTypes type) {
        this(type, -1);
    }

    public static SqlDataType create(SqlDataTypes type) {
        return new SqlDataType(type);
    }
    public static SqlDataType create(SqlDataTypes type, int amount) {
        return new SqlDataType(type, amount);
    }
}
