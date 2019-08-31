package me.modul153.NotenVerwaltung.api;

import net.myplayplanet.services.cache.AbstractSaveProvider;
import net.myplayplanet.services.cache.Cache;

public abstract class AbstractManager<M extends IAbstract, B extends IBuissnesObject & IAbstract, R extends IResopnseType & IAbstract> {

    private Cache<Integer, M> iDataObjectCache;

    public AbstractManager() {
        iDataObjectCache = new Cache<>(getManagerName() + "-cache", this::loadIDataObjectComplex, new AbstractSaveProvider<Integer, M>() {
            @Override
            public boolean save(Integer integer, M user) {
                return saveIDataObjectComplex(integer, user);
            }
        });
    }

    public abstract M loadIDataObjectComplex(Integer key);

    public abstract boolean saveIDataObjectComplex(Integer key, M value);

    public boolean contains(Integer key) {
        return iDataObjectCache.get(key) != null;
    }

    public B getBuissnesObject(Integer key) {
        M abstr = iDataObjectCache.get(key);

        if (abstr instanceof IBuissnesObject ) {
            return (B) abstr;
        }else if (abstr instanceof IResopnseType) {
            IResopnseType rt = (IResopnseType) abstr;
            return (B) rt.toBusinessObject();
        }else {
            return null;
        }
    }

    public R getResponseType(Integer key) {
        M abstr = iDataObjectCache.get(key);

        if (abstr instanceof IResopnseType ) {
            return (R) abstr;
        }else if (abstr instanceof IBuissnesObject) {
            IBuissnesObject rt = (IBuissnesObject) abstr;
            return (R) rt.toResponse();
        }else {
            return null;
        }
    }

    public void add(Integer key, M value) {
        if (validate(value)) {
            iDataObjectCache.update(key, value);
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
