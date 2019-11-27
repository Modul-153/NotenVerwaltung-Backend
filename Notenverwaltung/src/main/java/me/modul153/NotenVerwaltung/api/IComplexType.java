package me.modul153.NotenVerwaltung.api;

public interface IComplexType extends IDataObject,IAbstract {
    ISqlType toSqlType();
}
