; 10 feb 2015

; multi-segment executable file template.

data segment
    ; add your data here!
    a db 99
    b db 252
    mcd db ?
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
    call gcd      
    
    mov ax, 4c00h ; exit to operating system.
    int 21h
    gcd proc near
        mov al,a
        mov ah,b
    ciclo:cmp al,ah
        je fine
        ja grande
        sub ah,al
        jmp ciclo
    grande:sub al,ah
        jmp ciclo
    fine:mov mcd,al
        ret
    gcd endp    
ends

end start ; set entry point and stop the assembler.
