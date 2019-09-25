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