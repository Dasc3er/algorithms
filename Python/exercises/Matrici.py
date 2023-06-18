import random

width = -1
while width < 2:
	width = int(raw_input("Inserire la larghezza della matrice: "))

height = -1
while height < 2:
	height = int(raw_input("Inserire l'altezza della matrice: "))

matrice = []
for i in range(width):
	elenco = []
	for j in range(height):
		elenco.append(random.randint(0, 10))
	matrice.append(elenco)

"""
matrice = [
	[1, 0, 0, 0, 0, 0, 0,],
	[0, 2, 0, 0, 0, 0, 0,],
	[0, 0, 3, 0, 0, 0, 0,],
	[0, 0, 0, 4, 0, 0, 0,],
	[0, 0, 0, 0, 5, 0, 0,],
	[0, 0, 0, 0, 0, 6, 0,],
	[0, 0, 0, 0, 0, 0, 7,],
]
"""

print 'Matrice creata:'
for i in range(width):
	print matrice[i]

print

if height == width:
	superiore = True
	inferiore = True
	for i in range(width):
		for j in range(height):
			if matrice[i][j] != 0:
				if j > i:
					superiore = False
				elif j < i:
					inferiore = False
					
	simmetrica = True
	for i in range(width):
		for j in range(height):
			if matrice[i][j] != matrice[j][i]:
				simmetrica = False

	if superiore:
		print 'Matrice triangolare superiore'
	elif inferiore:
		print 'Matrice triangolare inferiore'
	else:
		print 'Matrice quadrata'
	
	if simmetrica:
		print 'Matrice simmetrica!!!'
	
	print
	
	determinante = 1
	if superiore or inferiore:
		for i in range(width):
			determinante *= matrice[i][i]
			
	else:
		determinante = 10
		
	print 'Determinante:', determinante
else:
	print 'Matrice rettangolare'

