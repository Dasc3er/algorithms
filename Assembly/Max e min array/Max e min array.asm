; 13 gen 2015

; multi-segment executable file template.

data segment
    ; add your data here!
    v db 5,7,-2,43,-56,
    lun equ $-v
    max db ?
	min db ?
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
	mov al,v ; massimo (max)
	mov ah,v ; minimo (min)
	mov cx,lun ; mette lun in cx come contatore del loop
	dec cx ; decrementa il contatore (la prima posizione è già stata considerata dall'algoritmo)
    mov si,1 ; azzera il registro indice
ciclo:cmp al,v[si]
	jge minimo
	mov al,v[si] ; salva il valore dell'array come massimo
	jmp fine
minimo: cmp ah,v[si]
	jle fine 
	mov ah,v[si] ; salva il valore dell'array come minimo
fine: inc si
	loop ciclo
    mov max,al ; salva  il valore massimo in max
    mov min,ah ; salva  il valore minimo in min
    
    mov ax, 4c00h ; exit to operating system.
    int 21h    
ends

end start ; set entry point and stop the assembler.