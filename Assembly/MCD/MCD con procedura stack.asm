; 10 dic 2015

; multi-segment executable file template.

data segment
    ; add your data here!
    op1 dw 9999
    op2 dw 9998
    mcd dw ?
ends

stack segment
    db   6  dup(0)
ends

code segment
start:
; set segment registers:
    mov ax, data
    mov ds, ax
    mov es, ax

    ; add your code here
    push op1
    push op2    
    call gcd
    pop mcd          
    
    mov ax, 4c00h ; exit to operating system.
    int 21h
    gcd proc near
        mov bp,sp
        mov ax,[bp+2]
        mov bx,[bp+4]
    ciclo:cmp ax,bx
        je fine
        ja grande
        sub bx,ax
        jmp ciclo
    grande:sub ax,bx
        jmp ciclo
    fine:mov [bp+2],ax
        ret
    gcd endp    
ends

end start ; set entry point and stop the assembler.
