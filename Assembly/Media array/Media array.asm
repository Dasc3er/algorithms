; 16 dic 2014

; multi-segment executable file template.

data segment
    ; add your data here!
    v db 1,2,3,4,5,6,7,8,9,10
    lun equ $-v
    media db ?
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
    sub si,si ; azzera il registro indice
    sub ax,ax ; azzera il registro del risultato
    mov cx,lun ; mette lun in cx come contatore del loop
ciclo:add al,v[si] ; aggiunge ad al il valore contenuto nel byte di indirizzo effettivo di v + si
    adc ah,0 ; aggiunge l'eventuale riporto dell'addizione ad ah
    inc si ; incrementa l'indice dell'array
    loop ciclo ; decrementa cx, se cx != 0 ripete (salta a ciclo)
    mov bl, lun ; mette lun in bl per utilizzarlo nella media
    div bl ; divide ax (la somma di tutti i valori dell'array) per bl, salvando su al il quoto e su ah il resto
    mov media,al ; mette il valore contenuto in al nella variabile media
    
    mov ax, 4c00h ; exit to operating system.
    int 21h    
ends

end start ; set entry point and stop the assembler.