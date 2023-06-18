# Thomas Zilio
import random

## Merge sort
class MergeSort:
	def sort(lista):
		MergeSort.divide(lista, 0, len(lista)-1)

	def divide(lista, inizio, fine):
		if inizio >= fine: return
		
		centro = (inizio+fine)/2
		
		MergeSort.divide(lista, inizio, centro)
		MergeSort.divide(lista, centro+1, fine)
		MergeSort.impera(lista, inizio, centro, fine)

	def impera(lista, inizio, centro, fine):
		dim = fine-inizio+1
		temp = []
		
		i = inizio
		j = centro+1
		k = 0
		
		while inizio <= centro and j <= fine:
			if lista[inizio] < lista[j]:
				temp.append(lista[inizio])
				inizio += 1
			else:
				temp.append(lista[j])
				j += 1
				
			k += 1
				
		if inizio <= centro:
			for item in xrange(k, dim):
				temp.append(lista[inizio])
				inizio += 1
		else:
			for item in xrange(k, dim):
				temp.append(lista[j])
				j += 1
				
				
		for item in xrange(dim):
			lista[i] = temp[item]
			i += 1

	sort = staticmethod(sort)
	divide = staticmethod(divide)
	impera = staticmethod(impera)


lista = list()
for item in range (100):
	lista.append(random.randint(1, 100))

print 'Lista originale: ', lista
print
print

MergeSort.sort(lista)

print 'Lista ordinata: ', lista
