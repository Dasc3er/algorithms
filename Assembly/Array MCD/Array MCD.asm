; 13 apr 2015

; multi-segment executable file template.

data segment
    ; add your data here!
    a dw 1,232,79,400,5,645
    lun equ ($-a)/2
    b dw 10,2132,1719,329,75,32
    c dw 0,0,0,0,0,0
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
    mov cx,lun ; mette lun in cx come contatore del loop
ciclo:push a[si]
    push b[si]
    call mcd
    pop c[si]
    inc si ; incrementa l'indice dell'array
	inc si ; incrementa l'indice dell'array
    loop ciclo ; decrementa cx, se cx != 0 ripete (salta a ciclo)
    
    mov ax, 4c00h ; exit to operating system.
    int 21h
    
    mcd proc near
        mov bp,sp
        mov ax,[bp+2]
        mov bx,[bp+4]
 ciclop:cmp ax,bx
        je fine
        ja grande
        sub bx,ax
        jmp ciclop
 grande:sub ax,bx
        jmp ciclop
   fine:mov [bp+4],ax
        ret 2
    mcd endp  
    
ends

end start ; set entry point and stop the assembler.