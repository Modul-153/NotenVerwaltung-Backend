package me.modul153.NotenVerwaltung.api;

import java.util.HashMap;

public abstract class AbstractManager<M extends IAbstract, B extends ISqlType, R extends IComplexType> {
    public abstract HashMap<Integer, R> getAllComplex();
    public abstract HashMap<Integer, B> getAllSimple();

    public abstract R getComplex(int key);
    public abstract B getSimple(int key);

    public abstract boolean updateComplex(R complex);
    public abstract boolean updateSimple(B simple);

    /**
     * this method will be called before adding and if it returns false it will not add the item.
     *
     * @return
     */
    public boolean validate(M value) {
        return true;
    }
}
