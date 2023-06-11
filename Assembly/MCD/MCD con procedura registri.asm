; 10 feb 2015

; multi-segment executable file template.

data segment
    ; add your data here!
    a db 5
    b db 25
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
	mov al,a
    mov ah,b
    call gcd
	mov mcd,al	
    
    mov ax, 4c00h ; exit to operating system.
    int 21h
    gcd proc near
    ciclo:cmp al,ah
        je fine
        ja grande
        sub ah,al
        jmp ciclo
    grande:sub al,ah
        jmp ciclo
    fine:ret
    gcd endp    
ends

end start ; set entry point and stop the assembler.
