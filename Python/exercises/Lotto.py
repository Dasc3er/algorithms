# Thomas Zilio
import random

## Elenco delle citta
citta = ['Roma', 'Venezia', 'Torino']
## Numero delle estrazioni da effettuare per citta
numeriEstratti = 6

for item in citta:
	numeriPossibili = range(1, 91)
	lista = list()

	## Estrazione
	while len(lista) < numeriEstratti:
		numero = random.choice(numeriPossibili)
		lista.append(numero)
		numeriPossibili.remove(numero)

	## Stampa
	print (item + ': ' + str(lista))
