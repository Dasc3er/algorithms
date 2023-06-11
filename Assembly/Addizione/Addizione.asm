; 25 nov 2014

; multi-segment executable file template.

data segment
    ; add your data here!
    a db 255d
    b db 254d
    c db 0
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
    add al,b
    mov c, al 
    
    mov ax, 4c00h ; exit to operating system.
    int 21h    
ends

end start ; set entry point and stop the assembler.
