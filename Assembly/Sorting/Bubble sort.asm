; Bressanin Damiano, Zilio Thomas - 3ai
; 3 mar 2015

; multi-segment executable file template.

data segment
    ; add your data here!
    array dw 162, 3, 142, 181, 111, 345, 159, 264, 151, 158, 207, 257, 361, 264, 398, 219, 290, 393, 227, 236, 168, 217, 96, 400, 281, 146, 182, 114, 336, 293, 311, 330, 197, 325, 50, 320, 284, 394, 134, 138, 179, 362, 334, 12, 294, 97, 113, 126, 351, 262, 163, 6, 104, 350, 371, 118, 86, 108, 190, 77, 228, 153, 182, 221, 300, 274, 165, 299, 73, 345, 219, 89, 350, 187, 129, 154, 45, 127, 197, 101, 170, 62, 281, 162, 149, 43, 258, 99, 326, 53
    lun equ ($-array)/2
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
    lea dx,array  ; salva in dx la posizione in memoria dell'array (load effective address)
    push dx ; copia nello stack il contenuto di dx
    mov ax,lun ; salva in ax la lunghezza
    push ax ; copia nello stack il contenuto di ax
    call bubble ; richiama la procedura bubble
    mov ax, 4c00h ; exit to operating system.
    int 21h
    bubble proc near
        mov bp,sp ; copia in bp (base pointer) il valore di sp (posizione ultimo dato occupato dello stack)
 inizio:mov si,[bp+4] ; salva in si il contenuto della memoria in posizione bp+4 (posizione array)
        mov cx,[bp+2] ; salva in si il contenuto della memoria in posizione bp+2 (lunghezza array)
        dec cx ; decrementa cx: i confronti da fare sono lunghezza-1
        sub bl,bl ; azzera bl, utilizzato come segnalatore di ordine dell'array (se 0 array ordinato)
  ciclo:mov ax,[si] ; copia in ax il contenuto della posizione di memoria si (elemento n array)
        cmp ax,[si+2] ; confronta ax con l'elemento successivo dell'array (contenuto della posizione di memoria si+2)
        jbe salta ; se i due elementi sono minori o ugiuali tra loro salta al'etichetta "salta"
        push ax
        push word ptr[si+2] ; word ptr[] indica che la memoria da considerare � di dimensione word (16 bit)
        pop word ptr[si]
        pop word ptr[si+2]
        inc bl ; inverte l'ordine degli elementi tramite stack e aumenta il contatore bl
  salta:add si,2 ; incrementa si di 2 per considerare aol giro successivo l'elemento successivo
        loop ciclo ; esegue il loop per cx volte tornando all'etichetta "ciclo"
        cmp bl,0
        jne inizio  ; se ebl diveso da zero esegue nuovamente il confronto tra tutti gli elemenyi dell'array
        ret 4 ; ritorna all'istruzione con offset corrispondente al contenuto della parte pi� "alta" dello stack, diminuendo poi sp di 4
    bubble endp
ends

end start ; set entry point and stop the assembler.