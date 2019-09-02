package me.modul153.NotenVerwaltung.api;

import net.myplayplanet.services.cache.AbstractSaveProvider;
import net.myplayplanet.services.cache.Cache;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractManager<M extends IAbstract, B extends ISqlType & IAbstract, R extends IComplexType & IAbstract> {

    private Cache<Integer, M> iDataObjectCache;
    private List<String> notSave;

    public AbstractManager() {
        notSave = new ArrayList<>();
        iDataObjectCache = new Cache<>(getManagerName() + "-cache", this::loadIDataObjectComplex, new AbstractSaveProvider<Integer, M>() {
            @Override
            public boolean save(Integer integer, M user) {
                String o = user.toJson();
                if (!notSave.contains(o)) {
                    return saveIDataObjectComplex(integer, user);
                }else {
                    notSave.remove(o);
                    return true;
                }
            }
        });
    }

    public abstract M loadIDataObjectComplex(Integer key);

    public abstract boolean saveIDataObjectComplex(Integer key, M value);

    public boolean save(Integer key, M value) {
        if (saveIDataObjectComplex(key, value)) {
            setSave(value, true);
            this.add(key, value);
            return true;
        }else {
            return false;
        }
    }

    public boolean contains(Integer key) {
        return iDataObjectCache.get(key) != null;
    }

    public B getSqlType(Integer key) {
        M abstr = iDataObjectCache.get(key);

        if (abstr instanceof ISqlType) {
            return (B) abstr;
        }else if (abstr instanceof IComplexType) {
            IComplexType rt = (IComplexType) abstr;
            return (B) rt.toSqlType();
        }else {
            return null;
        }
    }

    public void setSave(IDataObject object, boolean dontSave) {
        String e = object.toJson();
        if (dontSave) {
            notSave.add(e);
        }else {
            notSave.remove(e);
        }
    }

    public R getComplexType(Integer key) {
        M abstr = iDataObjectCache.get(key);

        if (abstr instanceof IComplexType) {
            return (R) abstr;
        }else if (abstr instanceof ISqlType) {
            ISqlType rt = (ISqlType) abstr;
            return (R) rt.toComplexType();
        }else {
            return null;
        }
    }

    public M get(Integer key) {
        return iDataObjectCache.get(key);
    }

    public void add(Integer key, M value) {
        if (validate(value)) {
            iDataObjectCache.update(key, value);
        }else {
            System.out.println("invalid object with id " + key);
        }
    }

    public void clearCache() {
        iDataObjectCache.clearCache();
    }

    /**
     * this method will be called before adding and if it returns false it will not add the item.
     * @return
     */
    public boolean validate(M value) {
        return true;
    }

    public abstract String getManagerName();
}
