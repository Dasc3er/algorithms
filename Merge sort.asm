; multi-segment executable file template.

data segment
    ; add your data here!
    a dw 689,506,711,62,500,700,698,516,701,689,506,711,62,500,700,698,516,701,689,506,711,62,500,700,698,516,701,689,506,711,689,506,711,62,500,700,698,516,701,689,506,711,62,500,700,698,516,701,689,506,711,62,500,700,698,516,701,689,506,711,689,506,711,62,500,700,698,516,701,689,506,711,62,500,700,698,516,701,689,506,711,62,500,700,698,516,701,689,506,711
    lun equ ($-a)/2
	b dw 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
ends

stack segment
    dw   256  dup(0)
ends

code segment
start:
; set segment registers:
    mov ax,data
    mov ds,ax
    mov es,ax
    
    ; add your code here
    lea dx,a  ; salva in dx la posizione in memoria dell'array a (load effective address)
    push dx ; copia nello stack il contenuto di dx
	lea dx,b  ; salva in dx la posizione in memoria dell'array b (load effective address)
    push dx ; copia nello stack il contenuto di dx
	mov ax,0 ; salva in ax l'indice iniziale
    push ax ; copia nello stack il contenuto di ax (l'indice iniziale -  first)
	mov ax,lun ; salva in ax la lunghezza
    dec ax ; trova l'indice finale
    push ax ; copia nello stack il contenuto di ax (l'indice finale -  last)
    call mergesort ; richiama la procedura mergesort
    
    mov ax, 4c00h ; exit to operating system.
    int 21h
    
    mergesort proc near
        mov bp,sp ; copia in bp (base pointer) il valore di sp (posizione ultimo dato occupato dello stack)
        mov ax,[bp+4] ; ax diventa l'indice iniziale 
        cmp ax,[bp+2] ; confronta l'indice iniziale con l'indice finale
        jae finem ; salta all'etichetta finem se l'indice iniziale >= indice finale
		add ax,[bp+2]
		shr ax,1 ; individua l'indice medio (mid)
		push word ptr[bp+8] ; copia nello stack l'indirizzo dell'array da ordinare
		push word ptr[bp+6] ; copia nello stack l'indirizzo dell'array temporaneo
		push word ptr[bp+4] ; copia nello stack l'indice iniziale (first)
		push ax  ; copia nello stack l'indice finale (mid)
		call mergesort ; richiama la procedura mergesort
		mov bp,sp ; copia in bp (base pointer) il valore di sp (posizione ultimo dato occupato dello stack)
		mov ax,[bp+4]
		add ax,[bp+2]
		shr ax,1
		inc ax ; individua l'indice medio1 (mid+1 - mid1)
		push word ptr[bp+8] ; copia nello stack l'indirizzo dell'array da ordinare
        push word ptr[bp+6] ; copia nello stack l'indirizzo dell'array temporaneo
		push ax ; copia nello stack l'indice iniziale (mid+1)
		push word ptr[bp+2] ; copia nello stack l'indice finale (last)
		call mergesort ; richiama la procedura mergesort
		mov bp,sp ; copia in bp (base pointer) il valore di sp (posizione ultimo dato occupato dello stack)
		push word ptr[bp+8] ; copia nello stack l'indirizzo dell'array da ordinare
		push word ptr[bp+6] ; copia nello stack l'indirizzo dell'array temporaneo
		push word ptr[bp+4] ; copia nello stack l'indice iniziale (first)
		push word ptr[bp+2] ; copia nello stack l'indice finale (last)
		call merge ; richiama la procedura merge
 finem: ret 8 ; ritorna all'istruzione con offset corrispondente al contenuto della parte più "alta" dello stack, diminuendo poi sp di 8
    mergesort endp  
    
	merge proc near
		mov bp,sp ; copia in bp (base pointer) il valore di sp (posizione ultimo dato occupato dello stack)
		mov si,word ptr[bp+8] ; salva in si il contenuto della memoria in posizione bp+8 (posizione array da ordinare)
		mov di,word ptr[bp+6] ; salva in di il contenuto della memoria in posizione bp+6 (posizione array temporaneo)
		mov cx,word ptr[bp+2]
		add cx,word ptr[bp+4] 
		shr cx,1 ; salva in cx l'indice medio
		shl cx,1 ; trasforma il contenuto di cx da contatore di word a contatore di byte
		mov dx,cx ; dx diventa l'indice medio (mid) in byte
		inc cx
		inc cx ; cx diventa l'indice medio1 (mid+1 - mid1) in byte
		shl word ptr[bp+2],1 ; trasforma il contenuto dell'area di memoria bp+2 da contatore di word a contatore di byte
		shl word ptr[bp+4],1 ; trasforma il contenuto dell'area di memoria bp+4 da contatore di word a contatore di byte
		mov bx,word ptr[bp+4] ; bx diventa l'indice inizialie (first) in byte
		sub ax,ax ; azzera ax
ciclo1: cmp bx,dx ; inizio primo while
		ja ciclo2 ; prima condizione -> salta all'etichetta ciclo2 se first > mid
		cmp cx,word ptr[bp+2]
		ja ciclo2 ; seconda condizione -> salta all'etichetta ciclo2 se mid1 > last
		add si,bx
		mov ax,word ptr[si] ; copia in ax il contenuto del primo elemento da considerare dell'array da ordinare
		sub si,bx
		add si,cx
		cmp ax,word ptr[si]
		jae altro ; salta all'etichetta altro se ax >= contenuto
		mov word ptr[di],ax
		sub si,cx
		add bx,2
		jmp fine1
 altro: mov ax,word ptr[si] ; se ax >= contenuto in mid1
        mov word ptr[di],ax
        sub si,cx
        add cx,2
 fine1: add di,2
		jmp ciclo1
ciclo2: cmp bx,dx  ; inizio secondo while
		ja ciclo3 ; condizione (first<=mid)
		add si,bx
		mov ax,word ptr[si] ; copia in ax il contenuto dell'elemento da considerare dell'array da ordinare
		sub si,bx
		mov word ptr[di],ax
		add di,2
		add bx,2
		jmp ciclo2
ciclo3: cmp cx,word ptr[bp+2] ; inizio terzo while
		ja fine ; condizione (mid1<=last)
		add si,cx
		mov ax,word ptr[si] ; copia in ax il contenuto dell'elemento da considerare dell'array da ordinare
		sub si,cx
		mov word ptr[di],ax
		add di,2
		add cx,2
		jmp ciclo3
  fine: mov si,word ptr[bp+8]
        mov di,word ptr[bp+6]
		mov bx,word ptr[bp+4]
ciclo4: cmp bx,word ptr[bp+2] ; for finale di ordinamento
		ja ff
		mov ax,word ptr[di] ; copia in ax il valore dell'array ordinato
		add si,bx
		mov word ptr[si],ax ; copia nell'array da ordinare il contenuto di ax
		sub si,bx
		add bx,2
		add di,2
		jmp ciclo4
	ff: ret 8
	merge endp 
	
ends

end start ; set entry point and stop the assembler.
