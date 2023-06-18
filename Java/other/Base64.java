package other;

import java.util.Scanner;

/**
 * Classe dedita alla codifica e alla decodifica in Base64 di stringhe e array di byte
 * <hr>
 * 27 marzo Pasqua Codificato: MjcgbWFyem8gUGFzcXVh
 * 
 * Cioccolata per tutti Codificato: Q2lvY2NvbGF0YSBwZXIgdHV0dGk=
 * 
 * NiBhcHJpbGUgdGVzdCBiYXNlNjQ= Decodificato: 6 aprile test base64
 * 
 * cHJlcGFyYXJzaSBwZXIgdGVtcG8= Decodificato: prepararsi per tempo
 * 
 * @author Thomas Zilio
 */
public class Base64 {
    private static final String SIMBOLI = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    
    /**
     * Esegue la codifica della stringa in Base64
     * 
     * @param array
     *        Array da codificare
     * @return Stringa codificata
     */
    public static String codifica(byte[] array) {
        StringBuilder builder = binary(array);
        StringBuilder result = new StringBuilder();
        byte index;
        while (builder.length() != 0) {
            index = 0;
            for (int i = 0; i < 6; i++) {
                index <<= 1;
                if (builder.length() != 0) {
                    index += Integer.parseInt(builder.substring(0, 1));
                    builder.deleteCharAt(0);
                }
            }
            result.append(SIMBOLI.charAt(index));
        }
        while (result.length() % 4 != 0) {
            result.append("=");
        }
        return result.toString();
    }
    
    /**
     * Esegue la codifica della stringa in Base64
     * 
     * @param array
     *        Stringa da codificare
     * @return Stringa codificata
     */
    public static String codifica(String stringa) {
        return codifica(stringa.getBytes());
    }
    
    /**
     * Esegue la decodifica della stringa in Base64
     * 
     * @param codificata
     *        Stranga codificata
     * @return Stringa decodificata
     */
    public static String decodifica(String codificata) {
        codificata = codificata.replaceAll("=", "");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < codificata.length(); i++) {
            byte xp = (byte) SIMBOLI.indexOf(codificata.charAt(i));
            builder.append(toBinary(xp).substring(2));
        }
        return AsciiToString(builder.toString());
    }
    
    /**
     * Converte array di byte in binario
     * 
     * @param array
     *        Array da convertire
     * @return Stringa modificabile
     */
    private static StringBuilder binary(byte[] array) {
        StringBuilder builder = new StringBuilder();
        for (byte b : array) {
            builder.append(toBinary(b));
        }
        return builder;
    }
    
    /**
     * Analizza e converte una stringa in una sequenza di numeri secondo la
     * codifica ASCII
     * 
     * @param asciiString
     *        Stringa da convertire
     * @return Sequanza di numeri rappresentante la stringa
     */
    private static String toBinary(byte number) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            builder.append((number & 128) == 0 ? 0 : 1);
            number <<= 1;
        }
        return builder.toString();
    }
    
    /**
     * Analizza e converte una sequenza si numeri in una stringa secondo la
     * codifica ASCII
     * 
     * @param intString
     *        Stringa di numeri da convertire
     * @return Stringa letta
     */
    public static String AsciiToString(String intString) {
        StringBuilder result = new StringBuilder();
        int nextChar;
        for (int i = 0; i < intString.length() / 8; i++) {
            nextChar = 0;
            for (int j = 0; j < 8; j++) {
                nextChar += ((int) intString.charAt((j + i * 8))) % 2
                        * esponente(2, 8 - (j % 8) - 1);
            }
            result.append((char) nextChar);
        }
        return result.toString();
    }
    
    /**
     * Esegue l'operazione di potenza con i parametri passati.
     * 
     * @param x
     *        Numero da elevare
     * @param y
     *        Esponente
     * @return Numero elevato alla potenza
     */
    private static int esponente(int x, int y) {
        int cont = 1;
        for (int i = 0; i < y; i++)
            cont *= x;
        return cont;
    }
    
    public static void main(String[] args) {
        Scanner tast = new Scanner(System.in);
        System.out.println("Inserire la stringa da codificare: ");
        String todo = tast.nextLine();
        
        String result = codifica(todo);
        System.out.println("Risultato codificato: " + result);
        System.out.println("Risultato decodificato: " + decodifica(result));
    }
}
