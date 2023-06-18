# Thomas Zilio
## Sono state cerati due diversi tipi di funzioni che gestiscono l'algoritmo di Vigenere, in particolare per la decifrazione, in modo differente:
## il primo tipo (funzioni "cifra" e "decifra") ottimizza il codice e risulta leggermente piu' efficace a livello di elaborazione;
## il secondo tipo (funzioni "cifraConChiave" e "decifraConChiave") genera una chiave apposita per la decifrazione del messaggio.

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


## Messaggio da cifrare
messaggio = raw_input('Inserire la parola da cifrare: ')
chiave = raw_input('Inserire la chiave di cifratura: ')

## Codifica
risultato = CifrarioVigenere.cifra(messaggio, chiave)

## Stampa
print ('Messaggio cifrato: ' + risultato)

## Decodifica
risultato = CifrarioVigenere.decifra(risultato, chiave)

## Stampa
print ('Messaggio decifrato: ' + risultato)
