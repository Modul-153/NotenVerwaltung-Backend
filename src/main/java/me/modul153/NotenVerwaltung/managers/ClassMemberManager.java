package me.modul153.NotenVerwaltung.managers;

import me.modul153.NotenVerwaltung.api.AbstractManager;
import me.modul153.NotenVerwaltung.data.abstracts.ClassMember;
import net.myplayplanet.services.cache.Cache;

import java.util.Collection;

public class ClassMemberManager extends AbstractManager<Collection<ClassMember>, Collection<ClassMember>, Collection<ClassMember>> {
    private Cache<Integer, Integer[]> userClassCache;
    private Cache<Integer, Integer[]> classUserCache;



    public ClassMemberManager() {

        userClassCache = null;
    }
}
