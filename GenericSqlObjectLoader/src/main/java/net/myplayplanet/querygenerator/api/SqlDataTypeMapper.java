package net.myplayplanet.querygenerator.api;

import net.myplayplanet.querygenerator.api.table.SqlDataType;
import net.myplayplanet.querygenerator.api.table.SqlDataTypes;

import java.util.HashMap;
import java.util.UUID;


public class SqlDataTypeMapper {

    private static SqlDataTypeMapper instance;

    public static  SqlDataTypeMapper getInstance() {
        if (instance == null) {
            instance = new SqlDataTypeMapper();
        }
        return instance;
    }

    private HashMap<Class, SqlDataType> typeMap;

    private SqlDataTypeMapper() {
        typeMap = new HashMap<>();

        register(String.class, SqlDataType.create(SqlDataTypes.VARCHAR));
        register(UUID.class, SqlDataType.create(SqlDataTypes.CHAR, 36));

        register(int.class, SqlDataType.create(SqlDataTypes.INT));
        register(Integer.class, SqlDataType.create(SqlDataTypes.INT));

        register(double.class, SqlDataType.create(SqlDataTypes.DOUBLE));
        register(Double.class, SqlDataType.create(SqlDataTypes.DOUBLE));

        register(float.class, SqlDataType.create(SqlDataTypes.FLOAT));
        register(Float.class, SqlDataType.create(SqlDataTypes.FLOAT));

        register(boolean.class, SqlDataType.create(SqlDataTypes.BOOLEAN));
        register(Boolean.class, SqlDataType.create(SqlDataTypes.BOOLEAN));
    }

    public SqlDataTypeMapper register(Class clazz, SqlDataType type) {
        typeMap.put(clazz, type);
        return this;
    }

    public SqlDataType map(Class clazz) {
        return typeMap.getOrDefault(clazz, null);
    }
}
