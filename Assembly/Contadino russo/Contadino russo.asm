; 9 dic 2014

; multi-segment executable file template.

data segment
    ; add your data here!
    a db 10d
    b db 9d
    ris db 0
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
    mov dl,a ; salva a in dl 
    mov dh,b ; salva b in dh
    mov bl,0 ; azzera bl
inizio: cmp dl,0 ; confronta dl con 0
    je fine ; se dl = 0 allora salta all'etichetta fine
    test dl,1 ; esegue un'operazione di "AND" tra dl e 00000001
    je salta ; se il primo bit di dl corrisponde a 0 (numero pari) allora salta all'etichetta salta
    add bl,dh ; somma bl e dh, salvando in dh
salta: shr dl,1 ; divide dl per due
    shl dh,1 ; moltiplica dh per 2
    jmp inizio ; salta all'etichetta inizio
fine: mov ris, bl ; copia il risultato in ris
    
    mov ax, 4c00h ; exit to operating system.
    int 21h    
ends

end start ; set entry point and stop the assembler.