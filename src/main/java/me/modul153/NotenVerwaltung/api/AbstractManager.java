package me.modul153.NotenVerwaltung.api;

import net.myplayplanet.services.cache.AbstractSaveProvider;
import net.myplayplanet.services.cache.Cache;
import net.myplayplanet.services.cache.advanced.ListCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractManager<M extends IAbstract, B extends ISqlType & IAbstract, R extends IComplexType & IAbstract> {

    private ListCache<Integer, M> iDataObjectCache;
    private List<String> notSave;
    private HashMap<Integer, String> dontSave;
    private List<Integer> oneTimeNoSave;

    public AbstractManager() {
        notSave = new ArrayList<>();
        dontSave = new HashMap<>();
        oneTimeNoSave = new ArrayList<>();
        iDataObjectCache = new ListCache<>(getManagerName() + "-cache", key -> {

            M m = loadIDataObjectComplex(key);

            if (m != null) {
                dontSave.put(key, m.toJson());
            }

            return m;
        }, new AbstractSaveProvider<Integer, M>() {
            @Override
            public boolean save(Integer key, M value) {
                String o = value.toJson();
                if (!notSave.contains(o)) {
                    if (oneTimeNoSave.contains(key)) {
                        oneTimeNoSave.remove(key);
                        return true;
                    }
                    if (!dontSave.containsKey(key) || !dontSave.get(key).equals(o)) {
                        dontSave.remove(key);
                        return saveIDataObjectComplex(key, value);
                    }else {
                        System.out.println(value.getClass().getSimpleName() + " Object with id " + key + " does not need to be saved.");
                        return true;
                    }
                } else {
                    notSave.remove(o);
                    return true;
                }
            }

            @Override
            public HashMap<Integer, M> load() {
                HashMap<Integer, M> integerMHashMap = loadAllObjects();
                oneTimeNoSave.addAll(integerMHashMap.keySet());
                return integerMHashMap;
            }
        }, m -> getKeyFromValue());
    }

    public abstract int getKeyFromValue();

    /***
     * SQL implemention des ladens alles Objekte, wird ein mal am start gemacht.
     */
    public abstract HashMap<Integer, M> loadAllObjects();

    /***
     * SQL implemention des ladens eines Objektes
     */
    public abstract M loadIDataObjectComplex(Integer key);

    /**
     * SQL implementions des speicherns eines Objektes.
     */
    public abstract boolean saveIDataObjectComplex(Integer key, M value);

    public boolean save(Integer key, M value) {
        if (saveIDataObjectComplex(key, value)) {
            setSave(value, true);
            this.add(key, value);
            return true;
        } else {
            return false;
        }
    }

    public boolean contains(Integer key) {
        return get(key) != null;
    }

    public B getSqlType(Integer key) {
        M abstr = iDataObjectCache.get(key);

        if (abstr instanceof ISqlType) {
            return (B) abstr;
        } else if (abstr instanceof IComplexType) {
            IComplexType rt = (IComplexType) abstr;
            return (B) rt.toSqlType();
        } else {
            return null;
        }
    }

    public void setSave(IDataObject object, boolean dontSave) {
        String e = object.toJson();
        if (dontSave) {
            notSave.add(e);
        } else {
            notSave.remove(e);
        }
    }

    public R getComplexType(Integer key) {
        M abstr = iDataObjectCache.get(key);

        if (abstr instanceof IComplexType) {
            return (R) abstr;
        } else if (abstr instanceof ISqlType) {
            ISqlType rt = (ISqlType) abstr;
            return (R) rt.toComplexType();
        } else {
            return null;
        }
    }

    public M get(Integer key) {
        return iDataObjectCache.get(key);
    }

    public void add(Integer key, M value) {
        if (validate(value)) {
            iDataObjectCache.addItem(key, value);
        } else {
            System.out.println("invalid object with id " + key);
        }
    }

    public void clearCache() {
        iDataObjectCache.clear();
    }

    /**
     * this method will be called before adding and if it returns false it will not add the item.
     *
     * @return
     */
    public boolean validate(M value) {
        return true;
    }

    public abstract String getManagerName();
}
