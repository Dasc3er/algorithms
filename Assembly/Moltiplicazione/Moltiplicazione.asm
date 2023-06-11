; 25 nov 2014

; multi-segment executable file template.

data segment
    ; add your data here!
    a db 5d
    b db 8d
    p dw 0
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
    sub ax,ax ; azzera ax per evitare problemi 
    mov bl,b ; salva valore di b in bl
    sub ch,ch ; azzera ch
    mov cl,a ; salva in cl a (contatore)
    ciclo:add al,bl ; somma al e bl
    adc ah,0 ; aggiunge l'eventuale riporto dell'addizione ad ah
    loop ciclo ; decrementa cx, se cx != 0 ripete (salta a ciclo)
    mov p,ax ;sposta il risultato (ax) in p
    
    mov ax, 4c00h ; exit to operating system.
    int 21h    
ends

end start ; set entry point and stop the assembler.