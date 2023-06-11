; 3 feb 2015

; multi-segment executable file template.

data segment
    ; add your data here!
    v dw 1,232,79,400,198,645,7,5,33,869
    lun equ ($-v)/2
    media dw ?
ends
stack segment
    dw   128  dup(0)
ends

code segment
start:
; set segment registers:
    mov ax,data
    mov ds,ax
    mov es,ax
    
    ; add your code here
    sub ax,ax ; azzera il registro del risultato
	sub dx,dx ; azzera il registro del risultato
	xor si,si ; azzera il registro indice
	xor bx,bx ; azzera il registro contatore dei numeri
    mov cx,lun ; mette lun in cx come contatore del loop
ciclo: test v[si], 0001h ; controllo sul bit meno significativo del numero (pari o dispari)
	jz avanti ; se pari salta all'etichetta avanti
	add ax,v[si] ; aggiunge ad al il valore contenuto nel byte di indirizzo effettivo di v + si
    adc dx,0 ; aggiunge l'eventuale riporto dell'addizione a dx
	inc bx ; incrementa il contatore dei numeri
avanti: inc si ; incrementa l'indice dell'array
	inc si ; incrementa l'indice dell'array
    loop ciclo ; decrementa cx, se cx != 0 ripete (salta all'etichetta ciclo)
    div bx ; divide dx:ax (la somma di tutti i valori dell'array) per bx, salvando su ax il quoto e su dx il resto
    mov media,ax ; mette il valore contenuto in ax nella variabile media
    
    mov ax, 4c00h ; exit to operating system.
    int 21h    
ends

end start ; set entry point and stop the assembler.