# REST API Calls

## GetUser
`GET http://localhost:8080/users/getUser/{userId}`

## InsertUser (1)
`PUT http://localhost:8080/users/addUser/`

body: 
```
{
    "id": 1,
    "name": "Neddy",
    "nachname": "Martinie",
    "userName": "nmartinie0",
    "birthday": "1999-06-28",
    "adresse": {
        "adressId": 1,
        "strasse": "Starling",
        "nummer": "80299",
        "ort": {
            "ortId": 1,
            "zipCode": 4482,
            "name": "Kihniö"
        }
    }
}
```

`PUT http://localhost:8080/users/addUsers/`

body: 
```
[{
    "id": 1,
    "name": "Neddy",
    "nachname": "Martinie",
    "userName": "nmartinie0",
    "birthday": "1999-06-28",
    "adresse": {
        "adressId": 1,
        "strasse": "Starling",
        "nummer": "80299",
        "ort": {
            "ortId": 1,
            "zipCode": 4482,
            "name": "Kihniö"
        }
    }
},
....
]
```


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