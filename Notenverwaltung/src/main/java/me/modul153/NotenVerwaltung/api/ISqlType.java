package me.modul153.NotenVerwaltung.api;

public interface ISqlType extends IDataObject, IAbstract{
    IComplexType toComplexType();
}
