# Thomas Zilio
import time
import random

## Colori
class Colorizer:
	prefix = '\033['
	suffix = 'm'
	colors = {
		'rosso': '91',
		'giallo': '93',
		'blu': '94',
		'verde': '92',
		'ciano': '96',
		'viola': '95',
		'arancione': '33',
		'white': '0',
	}

	def color(color):
		return Colorizer.prefix + Colorizer.colors[color] + Colorizer.suffix

	def colorize(str, color):
		if color in Colorizer.colors:
			str = Colorizer.color(color) + str + Colorizer.color('white');
		return str

	colorize = staticmethod(colorize)
	color = staticmethod(color)

## Dati iniziali
elencoColori = ['rosso', 'giallo', 'blu', 'verde', 'arancione', 'viola']
messages = {'corretto': 'nero', 'sbagliato': 'bianco', 'errore': '	'}
numeroMosseMassime = 9
numeroColori = 4

## Selezione dei colori da parte del computer
colori = []
for i in range (numeroColori):
	colori.append(elencoColori[random.randint(0, len(elencoColori)-1)])

print colori

contatoreMosse = 0
start_time = time.time()
selezioneUtente = []
continua = True

## Richiesta dei dati al giocatore
while contatoreMosse < numeroMosseMassime and continua:
	continua = False
	selezioneUtente = []

	## Input del giocatore
	while len(selezioneUtente) < numeroColori:
		colore = raw_input('Colore nella posizione %d: ' % (len(selezioneUtente)+1)).strip()

		if colore in elencoColori:
			selezioneUtente.append(colore)
	
	## Controllo sulla correttezza dell'input
	risultato = ''
	for index, item in enumerate(selezioneUtente):
		if colori[index] == item:
			risultato += messages['corretto']
		else:
			continua = True
			if item in colori: 
				risultato += messages['sbagliato']
			else:
				risultato += messages['errore']

		risultato += ' '

	contatoreMosse += 1

	selezione = ''
	## Stampa del risultato
	for item in selezioneUtente:
		selezione += Colorizer.colorize(item, item) + ' '
		
	print 
	print 'Selezione di questo turno: ' + selezione
	print 'Risultato: ' + risultato

	print 
	print 

## Risultato finale
if continua:
	print 'Hai perso!!!'
	print 'La sequenza era: ' + ' '.join(colori)
else:
	print 'Hai vinto!!!'

print '--- %s secondi totali ---' % (time.time() - start_time)

