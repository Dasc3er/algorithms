## Classe dedicata al cifrario di Vigenere
class CifrarioVigenere:
	## Insiemi da considerare
	insiemi = (['a', 'z'], ['A', 'Z'], ['0', '9'])

	## Cifratura semplice
	def cifra(messaggio, chiave):
		## Calcola la diffrerenza tra chiave e il primo elemento dell'insieme di appartenenza
		difference = list()
		for carattere in chiave:
			char = ord(carattere)
			for insieme in CifrarioVigenere.insiemi:
				if(carattere >= insieme[0] and carattere <= insieme[1]):
					difference.append(char - ord(insieme[0]))
		
		return CifrarioVigenere.algoritmo(messaggio, difference)

	## Decifrazione semplice
	def decifra(messaggio, chiave):
		## Calcola la diffrerenza tra chiave e l'ultimo elemento dell'insieme di appartenenza
		difference = list()
		for carattere in chiave:
			char = ord(carattere)
			for insieme in CifrarioVigenere.insiemi:
				if(carattere >= insieme[0] and carattere <= insieme[1]):
					difference.append(ord(insieme[1]) + 1 - char)
		
		return CifrarioVigenere.algoritmo(messaggio, difference)

	## Esecuzione di Vigenere
	def algoritmo(messaggio, difference):
		result = list()
		index = 0
		
		for carattere in messaggio:
			used = False
			char = ord(carattere)

			for insieme in CifrarioVigenere.insiemi:
				if(carattere >= insieme[0] and carattere <= insieme[1]):
					used = True
					char += difference[index]

					start = ord(insieme[0])
					end = ord(insieme[1])
					gap = (end - start) + 1
					while(char < start):
						char += gap
					while(char > end):
						 char -= gap
	
			result.append(chr(char))

			## Controllo sul fatto che la chiave sia stata utilizzata
			if used:
				index = (index + 1) % len(chiave)

		return ''.join(result)

	algoritmo = staticmethod(algoritmo)
	cifra = staticmethod(cifra)
	decifra = staticmethod(decifra)

## Messaggio da cifrare
messaggio = raw_input('Inserire la parola da cifrare: ')
chiave = raw_input('Inserire la chiave di cifratura: ')

## Codifica
result = CifrarioVigenere.cifra(messaggio, chiave)

## Stampa
print ('Messaggio cifrato: ' + result)

## Decodifica
result = CifrarioVigenere.decifra(result, chiave)

## Stampa
print ('Messaggio decifrato: ' + result)
