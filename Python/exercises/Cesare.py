# Thomas Zilio

## Classe dedicata al cifrario di Cesare
class CifrarioCesare:
	## Insiemi da considerare
	insiemi = (['a', 'z'], ['A', 'Z'], ['0', '9'])

	## Cifratura
	def cifra(messaggio, passo=3):
		risultato = list()
		for carattere in messaggio:
			lettera = ord(carattere)

			for insieme in CifrarioCesare.insiemi:
				if(carattere >= insieme[0] and carattere <= insieme[1]):
					inizio = ord(insieme[0])
					fine = ord(insieme[1])
					differenza = (fine - inizio) + 1
					lettera += (passo % differenza)

					while(lettera < inizio):
						lettera += differenza
					while(lettera > fine):
						 lettera -= differenza
	
			risultato.append(chr(lettera))

		return ''.join(risultato)

	## Decifrazione
	def decifra(messaggio, passo=3):
		passoNuovo = -passo
		return CifrarioCesare.cifra(messaggio, passo = passoNuovo)

	cifra = staticmethod(cifra)
	decifra = staticmethod(decifra)

## Messaggio da cifrare
messaggio = raw_input('Inserire la parola da cifrare: ')
passo = raw_input('Inserire il passo: ')

## Codifica
risultato = CifrarioCesare.cifra(messaggio, passo = int(passo))

## Stampa
print ('Messaggio cifrato: ' + risultato)

## Decodifica
risultato = CifrarioCesare.decifra(risultato, passo = int(passo))

## Stampa
print ('Messaggio decifrato: ' + risultato)
