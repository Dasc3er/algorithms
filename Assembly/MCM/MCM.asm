; 25 nov 2014

; multi-segment executable file template.

data segment
    ; add your data here!
    mcm dw 0
    a db 5
    b db 7
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
    mov al,a
    mov bl,b
    sub ah,ah
    sub bh,bh
     
    mov cl,al ;salva il valore originale di a
    mov ch,bl ;salva b
ciclo:cmp ax,bx
    je fine
    ja maggiore
    add al,cl ;somma il valore originale di a
    adc ah,0
    jmp ciclo
maggiore:add bl,ch
    adc bh,0
    jmp ciclo
fine: mov mcm,bx          
    
    mov ax, 4c00h ; exit to operating system.
    int 21h    
ends

end start ; set entry point and stop the assembler.
