# Thomas Zilio
import socket ## Ritorna un oggetto (-> diverso da C), un'iterfaccia a basso livello con funzioni e parametri di BSD(Berkeley Software Distribution) API
import sys, os

FAMIGLIA = socket.AF_INET ## Tipo di socket (dominio, famiglia), idetifica il meccanismo di trasporto(protocollo): AF_INET (IPv4 -> socket composto da IP e porta), AF_UNIX, AF_INET6 (IPv6)
TIPO = socket.SOCK_STREAM ## Per AF_INET: SOCK_STREAM (TCP), SOCK_DGRAM (UDP), SOCK_RAW (IP, ICMP)

HOSTNAME = os.popen('ip addr show eth0').read().split("inet ")[1].split("/")[0] ## Risoluzione dell'indirizzo IP locale
PORTA = 6666 ## Porta del server
BUFFER = 1024 ## Lunghezza tipica del buffer

soc = socket.socket() ## Crea socket di tipo client

if len(sys.argv) > 1:
	messaggio = sys.argv[1] ## Primo parametro da CLI
else:
	messaggio = raw_input('Inserire il messaggio da inviare: ') ## Richiesta del messaggio
	
if len(sys.argv) > 2:
	chiave = sys.argv[2] ## Secodo parametro da CLI
else:
	chiave = raw_input('Inserire la chiave di cifratura: ') ## Richiesta della chiave

## print 'Prima della connessione'
## print os.system('netstat -nat | grep ESTA')

try:
	soc.connect((HOSTNAME, PORTA)) ## Connessione
except:
	print 'Impossibile risolvere il nome dell\'host'
else:
	print 'Connesso con il server'
	
	print
	print 'Invia:', messaggio
	soc.send(messaggio)
	print
	
	dati = soc.recv(BUFFER)
	print 'Riceve:', dati

	print 'Invia:', chiave
	soc.send(chiave)
	print
	
	dati = soc.recv(BUFFER)
	print 'Riceve:', dati
	print
	
	## print 'Prima della chiusura'
	## print os.system('netstat -nat | grep ESTA')

	soc.close() ## Chiude il socket -> fondamentale, o la porta rimane occupata!!!

	## print 'Dopo la chiusura'
	## print os.system('netstat -nat | grep ESTA')

