# Thomas Zilio
import random

## Fattoriale iterativo
def fattorialeI(valore):
	lista = range(1, valore+1)

	risultato = 1
	for item in lista:
		risultato *= item

	return risultato

## Controllo sulla fattibilita' di eseguire il cast a intero
def isInt(value):
	try:
		int(value)
		return True
	except:
		return False

## Elenco delle citta
citta = ['Bari', 'Cagliari', 'Firenze', 'Genova', 'Milano', 'Napoli', 'Palermo', 'Roma', 'Torino', 'Venezia', 'Ruota Nazionale']
estrazioni = {}
## Numero delle estrazioni da effettuare per citta'
numeriEstratti = 5
## Valori massimi delle vincite
vincite = [11.23, 250, 4500, 120000, 6000000]

for item in citta:
	numeriPossibili = range(1, 91)
	lista = list()

	## Estrazione
	while len(lista) < numeriEstratti:
		numero = random.choice(numeriPossibili)
		lista.append(numero)
		numeriPossibili.remove(numero)

	## Salvataggio
	estrazioni[item] = lista

## Scelta della combinazione desiderata
combinazione = ''
while not isInt(combinazione) or int(combinazione) < 1 or int(combinazione) > 5:
	combinazione = raw_input('Quale combinazione si desidera effettuare (in numeri)? ')
combinazione = int(combinazione)

print 

## Scalta del numero di puntate da effettuare (minimo il numero di combinazioni desiderato)
numeri = 0
while numeri > 10 or numeri <= combinazione:
	numeri = ''
	while not isInt(numeri):
		numeri = raw_input('Quanti numeri si desiderano puntare (maggiore di '+ str(combinazione) + ')? ')
	numeri = int(numeri)

print

## Scelta sulla ruota
ruote = list()
if raw_input('Modalita\' complessa? ').lower() == 'si':
	n = ''
	while not isInt(n):
		n = raw_input('Quante ruote si desiderano considerare? ')
	n = int(n)

	while len(ruote) < n:
		ruota = 0
		while ruota not in citta:
			ruota = raw_input('Inserire la ruota su cui si desidera puntare: ')
		ruote.append(random.choice(citta))
else:
	ruote.append(random.choice(citta))

print 

## Completamento della puntata
puntata = list()
while len(puntata) < numeri:
	valore = ''
	while not isInt(valore):
		valore = raw_input('Inserire un numero della puntata: ')
	valore = int(valore)

	if valore > 0 and valore <= 90 and valore not in puntata:
		puntata.append(valore)
		

for ruota in ruote:
	print 
	print '---------------'
	## Individuazione dei numeri corretti
	corretti = 0
	for item in puntata:
		if item in estrazioni[ruota]:
			corretti += 1

	print 
	print 'Hai indovinato ' + str(corretti) + ' numero/i in ' + ruota

	## Calcolo della vincita massima
	vincita = vincite[combinazione - 1]/(fattorialeI(numeri)/(fattorialeI(combinazione) * fattorialeI(numeri - combinazione)) * len(ruote))
	print 'In teoria, il massimo che potresti aver vinto corrisponde a circa ' + str(vincita) + ' euro!!!'


