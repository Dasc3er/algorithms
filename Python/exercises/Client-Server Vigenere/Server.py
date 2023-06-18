# Thomas Zilio
## Protocollo a gettone, con socket bloccanti

import socket ## Ritorna un oggetto (-> diverso da C), un'iterfaccia a basso livello con funzioni e parametri di BSD(Berkeley Software Distribution) API
import os

## Classe dedicata al cifrario di Vigenere
class CifrarioVigenere:
	## Insiemi da considerare
	insiemi = (['a', 'z'], ['A', 'Z'], ['0', '9'])

	## Cifratura semplice
	def cifra(messaggio, chiave):
		## Calcola la diffrerenza tra chiave e il primo elemento dell'insieme di appartenenza
		scarto = list()
		for carattere in chiave:
			lettera = ord(carattere)
			for insieme in CifrarioVigenere.insiemi:
				if(carattere >= insieme[0] and carattere <= insieme[1]):
					scarto.append(lettera - ord(insieme[0]))
		
		return CifrarioVigenere.algoritmo(messaggio, scarto)

	## Decifrazione semplice
	def decifra(messaggio, chiave):
		## Calcola la diffrerenza tra chiave e l'ultimo elemento dell'insieme di appartenenza
		scarto = list()
		for carattere in chiave:
			lettera = ord(carattere)
			for insieme in CifrarioVigenere.insiemi:
				if(carattere >= insieme[0] and carattere <= insieme[1]):
					scarto.append(ord(insieme[1]) + 1 - lettera)
		
		return CifrarioVigenere.algoritmo(messaggio, scarto)

	## Esecuzione di Vigenere
	def algoritmo(messaggio, scarto):
		risultato = list()
		index = 0
		
		for carattere in messaggio:
			chiaveUsata = False
			lettera = ord(carattere)

			for insieme in CifrarioVigenere.insiemi:
				if(carattere >= insieme[0] and carattere <= insieme[1]):
					chiaveUsata = True
					lettera += scarto[index]

					inizio = ord(insieme[0])
					fine = ord(insieme[1])
					differenza = (fine - inizio) + 1
					while(lettera < inizio):
						lettera += differenza
					while(lettera > fine):
						 lettera -= differenza
	
			risultato.append(chr(lettera))

			## Controllo sul fatto che la chiave sia stata utilizzata
			if chiaveUsata:
				index = (index + 1) % len(chiave)

		return ''.join(risultato)

	## Cifratura del messaggio tramite interpretazione diretta della chiave
	def cifraConChiave(messaggio, chiave):
		## Calcola la diffrerenza tra chiave e il primo elemento dell'insieme di appartenenza
		scarto = list()
		for carattere in chiave:
			lettera = ord(carattere)
			for insieme in CifrarioVigenere.insiemi:
				if(carattere >= insieme[0] and carattere <= insieme[1]):
					scarto.append(lettera - ord(insieme[0]))

		## Esecuzione di Vigenere
		risultato = list()
		index = 0
		
		for carattere in messaggio:
			chiaveUsata = False
			lettera = ord(carattere)

			for insieme in CifrarioVigenere.insiemi:
				if(carattere >= insieme[0] and carattere <= insieme[1]):
					chiaveUsata = True
					lettera += scarto[index]

					inizio = ord(insieme[0])
					fine = ord(insieme[1])
					differenza = (fine - inizio) + 1
					while(lettera < inizio):
						lettera += differenza
					while(lettera > fine):
						 lettera -= differenza
	
			risultato.append(chr(lettera))

			## Controllo sul fatto che la chiave sia stata utilizzata
			if chiaveUsata:
				index = (index + 1) % len(chiave)

		return ''.join(risultato)

	## Decifrazione tramite creazione di una chiave
	def decifraConChiave(messaggio, chiave):
		nuovaChiave = list()

		## Individuazione della reale chiave per la decifratura del messaggio
		for carattere in chiave:
			lettera = ord(carattere)

			for insieme in CifrarioVigenere.insiemi:
				if(carattere >= insieme[0] and carattere <= insieme[1]):
					inizio = ord(insieme[0])
					fine = ord(insieme[1])

					lettera = fine + inizio + 1 - lettera

					differenza = (fine - inizio) + 1
					while(lettera < inizio):
						lettera += differenza
					while(lettera > fine):
						 lettera -= differenza

			nuovaChiave.append(chr(lettera))
		return CifrarioVigenere.cifra(messaggio, chiave = ''.join(nuovaChiave))

	algoritmo = staticmethod(algoritmo)
	cifra = staticmethod(cifra)
	decifra = staticmethod(decifra)
	cifraConChiave = staticmethod(cifraConChiave)
	decifraConChiave = staticmethod(decifraConChiave)
	
FAMIGLIA = socket.AF_INET ## Tipo di socket (dominio, famiglia), idetifica il meccanismo di trasporto(protocollo): AF_INET (IPv4 -> socket composto da IP e porta), AF_UNIX, AF_INET6 (IPv6)
TIPO = socket.SOCK_STREAM ## Per AF_INET: SOCK_STREAM (TCP), SOCK_DGRAM (UDP), SOCK_RAW (IP, ICMP)
PORTA = 6666 ## Porta del server
CONNESSIONI_MASSIME = 5 ## Connessioni massime messe in coda nel caso in cui il server sia occupato
HOSTNAME = os.popen('ip addr show eth0').read().split("inet ")[1].split("/")[0] ## Risoluzione dell'indirizzo IP locale
BUFFER = 1024 ## Lunghezza tipica del buffer

# os.system('clear') ## Esegue comando in CMD

try:
	soc = socket.socket(FAMIGLIA, TIPO) ## Crea socket
	soc.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
	soc.bind((HOSTNAME, PORTA)) ## Vincola IP e porta al socket
	soc.listen(CONNESSIONI_MASSIME) ## Limita le connessioni e imposta in stato di attesa
	print 'Socket in ascolto sulla porta', PORTA

	while True:
		print
	
		client_socket, indirizzo = soc.accept() ## Accetta le connessioni dei client
		print 'Connesso con', indirizzo
	
		dati = client_socket.recv(BUFFER)
		print 'Riceve:', dati
	
		client_socket.send('Inserire la chiave di cifratura')
		print 'Invia l\'invio ad inserire la chiave'
	
		chiave = client_socket.recv(BUFFER)
		print 'Riceve:', chiave
	
		cifrato = CifrarioVigenere.cifra(dati, chiave)
		client_socket.send(cifrato)
		print 'Invia:', cifrato
	
		client_socket.close() ## Chiude il socket del client
		print 'Socket client chiuso'
except KeyboardInterrupt:
	print 'Chiusura da tastiera...'
finally:
	soc.close()
	print 'Socket server chiuso'

