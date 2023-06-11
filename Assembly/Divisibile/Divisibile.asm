; 25 nov 2014

; multi-segment executable file template.

data segment
    ; add your data here!
    primo db 50d
    secondo db 5d 
    ris db 0
ends

stack segment
    dw   128  dup(0)
ends

code segment
start:
; set segment registers:
    mov ax, data
    mov ds, ax
    mov es, ax

    ; add your code here
    mov al,primo ;salva il valore di primo in al
    sub ah,ah ;azzera ah tramite sottrazione
    div secondo ;divide ax per secondo e salva in ah il reso e in al il quoto
    cmp ah,0
    je fine ;se ah uguale a zero il risultato rimane zero (primo divisibile per secondo), altrimenti assegna al risultato il numero 255 (11111111) 
    mov ris, 0ffh
     
    fine:mov ax, 4c00h ; exit to operating system.
    int 21h    
ends

end start ; set entry point and stop the assembler.
