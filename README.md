# Journal

## Introduzione

La seguente applicazione consente la creazione e la gestione di un giornale dei lavori.
Permette il caricamento in server locale di immagini e misure relative a dei lavori in fase di svolgimento, inoltre la bontà di tali dati è verificata tramite hash salvati all'interno della blockchain Quorum.

## Tecnologie

1. *Docker* [link](https://github.com/docker/compose)
2. *Quorum-7-nodes* [link](https://github.com/jpmorganchase/quorum-examples/tree/master/examples/7nodes)
3. *JDK 1.8* [link](https://github.com/ojdkbuild/ojdkbuild)
4. *Eclipse*
5. *Xampp / MySql* 
6. *web3j* [link](https://docs.web3j.io)
7. *epirus* [link](https://github.com/epirus-io/epirus.github.io)
8. *solidity* [link] (https://solidity.readthedocs.io/en/v0.7.0/installing-solidity.html)



## Launch 

1. Scaricare e installare docker-compose
2. é necessaria una blockchain quorum su cui testare il software, in questo caso è stata utilizzata Quorum 7-nodes
3. Scaricare il progetto dalla branch MASTER ed importarlo su Eclipse
4. Controllare che si scarichino le varie dependency
5. Avviare Quorum-7-nodes all'interno di Docker tramite "sudo docker-compose up -d" dalla cartella di download di Quorum-7-nodes, assicurarsi che tutti i nodi siano healthy.
6. Scaricare e avviare xampp
3. Modificare nelle classi di configurazione (DataServiceConfigWeb, DataServiceConfig, DataServiceConfigTest) la password per l'accesso ed eventualmente username e numero di porta(default: 8080)
4. Effettuare il run della classe WebimApplication del pacchetto com.sc.webim come springboot app
5. Per il login usare le credenziali: username: "drone", password:"password" per l'utente con ruolo drone o username: "direttore", password: "password" per il direttore
6. é possibile controllare lo stato dei nodi della blockchain tramite comando "sudo docker ps -a", per verificare lo stato dei blocchi e delle transazioni collegarsi all'indirizzo "http://localhost:<porta_cakeshop>/", la porta cakeshop è visualizzabile tramite il comando precedente.

## Navigare all'interno dell'applicazione

L'utente ha la possibilità di accedere come 'drone', in tal caso potrà caricare le immagini all'interno del server locale. 
Accedendo come 'direttore' l'utente può aggiungere nuovi dati scegliendo le immagini calcolate dal drone su cui calcolare le misure, e successivamente salvare gli hash delle immagini e delle misure all' interno di Quorum. Il 'direttore' inoltre è in grado di visualizzare tutti i lavori inseriti nel proprio giornale.
