# Aufsetzten

Grundsätzlich sollte eine Lokale MySQL Datenbank und ein Lokaler Redis aufgesetzt werden:

**Redis**
 
 https://github.com/dmajkic/redis/downloads herunterladen, dateien in einen Ordner irgendwo hin entpacken, und im ordner "64bit" die Datei redis-server.exe ausführen.

**MySQL**

Xamp Installieren. https://www.apachefriends.org/de/download.html
Wenn installiert Controle Panel starten und bei Apache und MySQL auf start klicken. 


Wenn das alles an ist muss das verzeichnis "C:\temp\nv-backend" erstellt werden.
Da rein müssen 2 Dateien die einmal "mysql-settings.properties" und einmal "mysql-settings.properties" heissen.

**mysql-settings.properties**
```
port=3306
hostname=localhost
password=
database=notenverwaltung
username=root
```

**redis-settings.properties**
```
port=6379
hostname=localhost
password=
database=
username=
```

Solange bei Redis und bei MySQL udn Redis nichts eingestellt wurde, gehen diese einstellungen.

Jetzt wenn das gemacht ist muss man auf die Seite "http://localhost/phpmyadmin/" gehen und dort eine neue Datenbank namens "notenverwaltung" erstellen. 

Wenn das alles gemacht ist sollte man auf start Klicken können und es sollte Laufe.

# Neuer Controller hinzufügen


Wenn ein neuer Controller erstellt werden soll, müssen mindestens 3 Klassen implementiert werden: 
- Manager
- abstract
- Controller

Anfangs sollte überlegt werden, wie das Objekt heissen soll, wofür ein Controller erstellt werden soll.
Als Beispiel werde ich jetzt `Account` nehmen.

Es muss überlegt werden, ob man ein Objekt erstellt das unterobjekte hat die von wo anders Kontrolliert werden sollen.

Wenn nicht, muss einfach die Klasse `Account` im Package `me.modul153.NotenVerwaltung.data.abstracts`erstellt werden, und da die Interfaces `IAbstract`, `ISqlType` und `IComplexType` implementiert werden. 
Bei den Methoden `toComplexType` und `toSqlType` kann einfach `this` returned werden.

Wenn schon wird es ein bisschen Komplizierter.

Es müssen drei Klasse erstellt werden: 
1. `AbstractAccount` im Package `me.modul153.NotenVerwaltung.data.abstracts` mit dem Interface `IAbstract`.
2. `Account` im Package `me.modul153.NotenVerwaltung.data.model` das von `AbstractAccount` erbt, und `ISqlType` implementiert.
3. `AccountComplex` im Package `me.modul153.NotenVerwaltung.data.complex` das von `AbstractAccount` erbt, und `IComplexType` implementiert.

In die Klasse `AbstractAccount` werden alle Felder, die keine unteren Werte enthalten, implentiert haben, rein.
Beispielsweisse `name` oder `accountId`.

in der Klasse `Account` sollte der Primary Key des Objektes mit dem eigenen Controller rein, als Beispiel bei der Adresse wäre das die AdressId.
In der Klasse `AccountComplex` wird dann das Objekt selber als eigenschaft genommen. (Beispielsweisse Adresse etc.)

Danach sollte die Klasse `AccountManager` implementiert werden.

Die Klasse `AccountManager` erbt von der Klasse `AbstractManager` mit den Generischen argumenten `AbstractAccount`, `Account` und `AccountComplex`. 
Wenn Es ein Objekt ohne Komplexe Datentypen als eingentschaften, ist kann da einfach 3 mal die Selbe Klasse mitgegeben werden.

Das sieht beispielsweisse so aus: `public class AccountManager extends AbstractManager<AbstractAccount, Account, AccountComplex>`
oder so: `public class AdressManager extends AbstractManager<Adresse, Adresse, Adresse>`

Wenn das gemacht ist müssen die Methoden implementiert werden, die verlangt werden von dem AbstractManager implementation.

Dazu siehe JavaDoc des Klasse `AbstractManager`.

Danach muss als erstes die Klasse `AccountManager` im Package `me.modul153.NotenVerwaltung.managers` erstellt werden. 

Diese muss die 

# REST API Calls

## User

### GetUser
`GET http://localhost:8080/api/users/getUser?userId=<id>`

### GetUserComplex
`GET http://localhost:8080/api/users/getUserComplex?userId=<id>`

### AddUser
`PUT http://localhost:8080/users/addUser/`

body: 
```
{
    "id": 1,
    "name": "Neddy",
    "nachname": "Martinie",
    "userName": "nmartinie0",
    "birthday": "1999-06-28",
    "adresseId": 1
}
```
### AddUserComplex
`PUT http://localhost:8080/users/addUserComplex/`

body: 
```
{
    "userId": 2,
    "name": "Briant",
    "nachname": "Doughtery",
    "userName": "bdoughtery0",
    "adresse": {
        "adressId": 1,
        "strasse": "Mallard",
        "nummer": "981",
        "ort": {
            "ortId": 1,
            "zipCode": 3984,
            "name": "Yulin"
        }
    }
}
```

## Adresse

### getAdresse
`GET http://localhost:8080/api/adresse/getAdresse?adressId=<id>`

### getAdresseComplex
`GET http://localhost:8080/api/adresse/getAdresse?adressId=<id>`

### addAdresse
`put http://localhost:8080/api/adresse/addAdresse/`

body: 

```
{
    "adressId": 5,
    "strasse": "Alpine",
    "nummer": "9770",
    "ortId": 2
}
```

### addAdresseComplex
`PUT http://localhost:8080/api/adresse/addAdresseComplex/`

body:

```
{
    "adressId": 5,
    "strasse": "Welch",
    "nummer": "5",
    "ort": {
        "ortId": 5,
        "zipCode": 6160,
        "name": "Torre do Pinhão"
    }
}
```

