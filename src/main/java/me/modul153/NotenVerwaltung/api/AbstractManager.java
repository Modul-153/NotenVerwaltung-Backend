package me.modul153.NotenVerwaltung.api;

import java.sql.SQLException;
import java.util.HashMap;

public abstract class AbstractManager<M extends IAbstract, B extends ISqlType, R extends IComplexType> {
    public abstract HashMap<Integer, R> getAllComplex() throws SQLException;
    public abstract HashMap<Integer, B> getAllSimple() throws SQLException;

    public abstract R getComplex(int key) throws SQLException;
    public abstract B getSimple(int key) throws SQLException;

    public abstract boolean updateComplex(R complex) throws SQLException;
    public abstract boolean updateSimple(B simple) throws SQLException;

    /**
     * this method will be called before adding and if it returns false it will not add the item.
     *
     * @return
     */
    public boolean validate(M value) {
        return true;
    }
}
