# Allgemeine Infos zum Projekt (unabhängig von den Einzelprojekten)

Genaueres zu Datenverarbeitung siehe unten

## Server und Zugänge

Wir nutzen zwei Server, die die HTW bereitstellt:

* Programmserver, Adresse: `ecco.f4.htw-berlin.de`, User: `local`
* Datenbankserver. Hier haben wir keinen SSH-Zugriff, sondern können nur unsere Datenbank nutzen. 
Sie ist nur aus dem HTW-Netz ansprechbar. Die Zugriffsdaten sind in Environment-Variablen gespeichert,
die auf dem Programmserver unter `home/local/.profile` einzusehen sind.

Befehl zum Einloggen auf dem Programmserver:

`ssh local@ecco.f4.htw-berlin.de`

Auf dem Server zur Datenbank connecten:

`mongo hadoop05.f4.htw-berlin.de:27020`

Befehl zum Einloggen auf dem Programmserver mit SSH-Tunnel zur Datenbank, sodass die Datenbank lokal über localhost:27020 angesprochen werden kann: 

`ssh -L 127.0.0.1:27020:hadoop05.f4.htw-berlin.de:27020 local@ecco.f4.htw-berlin.de`

## Screen

Wir nutzen Screen als Terminal-Session-Manager, damit die Prozesse auch ohne SSH-Verbindung weiterlaufen. 

Die wichtigsten Screen-Befehle:

Screen starten: `screen -DR`

Zum nächsten Screen-Fenster wechseln: `Ctrl-a, n`

Zum vorherigen Screen-Fenster wechseln: `Ctrl-a, p`

Screen-Fenster-Übersicht: `Ctrl-a, "`

Aus Screen rausgehen: `Ctrl-a, d`

[Weitere Infos hier](http://aperiodic.net/screen/quick_reference)

# ECCO-Project: Datenverarbeitung

## Verwendete Frameworks

- Scalatra
- Apache Spark
- MongoDB
  - MongoSpark Connector


## Deployment 

### auf dem Server
```
$ git clone https://github.com/htw-wise-2018/Datenverarbeitung.git
$ cd Datenverarbeitung/floatbackendapp
$ screen -S Backend
$ sbt clean
$ sbt package
$ sbt
$ jetty:start
```
Nach dem Start sollte keine Tastatureingabe erfolgen, sonst schließt sich das Programm.  
Konsole schließen empfohlen.

### lokal
```
$ ssh -L 127.0.0.1:27020:hadoop05.f4.htw-berlin.de:27020 local@ecco.f4.htw-berlin.de
```
Lokal Environment-Variablen setzen wie in `/home/local/.profile` auf dem Server, nur mit `MONGO_HOST=localhost`

```
$ sbt clean
$ sbt package
$ sbt
> jetty:start
```

## Neuladen der Anwendung
```
$ sbt
> jetty:stop;jetty:start
```

## Endpunkte
Wenn das Programm läuft, kann über `http://localhost:8080/` eine Übersicht über die Endpunkte abgerufen werden.
