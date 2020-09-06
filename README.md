# OPEN WebIM

## Introduzione

La seguente applicazione consente la creazione e la gestione di un giornale dei lavori.
Permette il caricamento in server locale di immagini e misure relative a dei lavori in fase di svolgimento, inoltre la bontà di tali dati è verificata tramite hash salvati all'interno della blockchain Quorum.

## Tecnologie

1. *Docker* [link](https://github.com/docker/compose)
2. *Quorum-7-nodes* [link](https://github.com/jpmorganchase/quorum-examples/tree/master/examples/7nodes)
3. *Eclipse* [link](https://www.eclipse.org/downloads)
4. *JDK 1.8* [link](https://github.com/ojdkbuild/ojdkbuild)
5. *Xampp / MySql* [link](https://www.apachefriends.org/it/index.html)
6. *Spring Boot* [link](https://spring.io/projects/spring-boot) 
7. *Solidity* [link](https://solidity.readthedocs.io/en/v0.7.0/installing-solidity.html)
8. *Epirus* [link](https://github.com/epirus-io/epirus.github.io)
9. *web3j* [link](https://docs.web3j.io)

Per problemi con l'aggiornamento del progetto provare ad eseguirne il "clean" o il "maven update" da Eclipse

## Launch 

1. Scaricare e installare docker-compose
2. È necessaria una blockchain Quorum su cui testare il software, in questo caso è stata utilizzata Quorum-7-nodes quindi scaricarla dal link sovrastante
3. Scaricare il progetto dalla branch MASTER  con il comando **git clone https://github.com/marzdeveloper/SoftwareCybersecurity.git** ed importarlo come progetto Maven su Eclipse
4. Controllare che si scarichino le varie dependencies
5. Assicurarsi di usare Java OpenJDK 1.8
6. Installare Spring Tools 4 su Eclipse tramite il Marketplace [link](https://marketplace.eclipse.org/content/spring-tools-4-aka-spring-tool-suite-4)
7. Avviare Quorum-7-nodes all'interno di Docker tramite "sudo docker-compose up -d" dalla cartella di Quorum-7-nodes
8. Assicurarsi che tutti i nodi siano healthy, è possibile controllare lo stato dei nodi della blockchain tramite il comando **sudo docker ps**, per verificare lo stato dei blocchi e delle transazioni collegarsi all'indirizzo **http://localhost:<porta_cakeshop>** (porta default: 8999), la porta cakeshop è visualizzabile tramite il comando precedente
9. Scaricare e avviare Xampp, avviare i server MySQL Database e Apache Web Server
10. Modificare nella classe di configurazione DataServiceConfigWeb.java, la password per la connsessione a PhpMyAdmin ed eventualmente username e numero di porta (default: 3306)

## Per il primo avvio (senza avere il database):

11. Effettuare il run della classe WebimApplicationLoadData.java nel pacchetto com.sc.webim come Spring Boot App, che avvierà l'applicazione e creerà il database
12. Connettersi all'applicazione web all'indirizzo **http://localhost:8080**, eventualmente cambiando la porta se si usa una porta differente
11. Modificare nella classe di configurazione **DataServiceConfigWeb.java** la proprietà **javax.persistence.schema-generation.database.action**, commentando la riga **61** e decommentando la riga **62**

## Per gli avvii successivi (con il database già creato):

11. Modificare nella classe di configurazione **DataServiceConfigWeb.java** la proprietà **javax.persistence.schema-generation.database.action**, commentando la riga **61** e decommentando la riga **62**
12. Effettuare il run della classe WebimApplication.java del pacchetto com.sc.webim come Spring Boot App, che avvierà l'applicazione
13. Connettersi all'applicazione web all'indirizzo **http://localhost:8080**, eventualmente cambiando la porta se si usa una porta differente

## Navigare all'interno dell'applicazione
La guida all'uso dell'applicazione è nella sezione wiki di questo repository quindi si rimanda a tale guida, di seguito c'è un breve riassunto.
L'utente con username **drone**, utilizzando come credenziali : username: **drone**, password: **P.4ssw0rd** potrà caricare le immagini all'interno del server locale ed autamaticamente verranno salvate le relative informazioni nel database.
Il direttore dei lavori, utilizzando come credenziali : username: **direttore**, password: **P.4ssw0rd**, potrà richiamare il servizio di fotogrammetria per calcolare le misure  scegliendo le immagini caricate dal drone. Inoltre potrà aggiungere nuove transazioni nella blockchain Quorum, cioè il giornale dei lavori, ed è in grado di visualizzare tutti i lavori inseriti nel giornale.
Per eseguire lo shutdown della blockchain, eseguire il comando "sudo docker-compose down" dalla cartella di Quorum-7-nodes.
