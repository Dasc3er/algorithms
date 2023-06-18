matrice = [
	[0, 2, 0, 8, 0, 0, 0,],
	[2, 0, 6, 2, 0, 0, 0,],
	[0, 6, 0, 0, 0, 0, 5,],
	[0, 2, 0, 0, 2, 9, 0,],
	[8, 0, 0, 2, 0, 3, 0,],
	[0, 0, 0, 9, 3, 0, 1,],
	[0, 0, 5, 0, 0, 1, 0,],
]

partenza = 0
fine = len(matrice) - 1

fare = range(len(matrice))

costi = []
precedenti = []
for i in fare:
	costi.append(999999999)
	precedenti.append(-1)

costi[partenza] = 0

while fare:
	minimo = costi[fare[0]]
	for nodo in fare:
		if costi[nodo] <= minimo:
			minimo = costi[nodo]
			attuale = nodo
			
	for nodo, costo in enumerate(matrice[attuale]):
		if costo > 0:
			if costi[nodo] > costi[attuale] + costo:
				costi[nodo] = costi[attuale] + costo
				precedenti[nodo] = attuale
				if not fare.count(nodo):
					fare.append(nodo)
				
	fare.remove(attuale)

print 'Percorso:', fine,

while precedenti[fine] >= 0:
	print precedenti[fine],
	fine = precedenti[fine]
