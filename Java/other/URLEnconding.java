package other;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.Vector;

public class URLEnconding {
    /**
     * Esegue la codifica della stringa in URLEncode
     * 
     * @param stringa
     *        Stringa fa codificare
     * @return Stringa codificata
     */
    public static String codifica(String stringa) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < stringa.length(); i++) {
            char carattere = stringa.charAt(i);
            if ((carattere >= 'A' && carattere <= 'Z')
                    || (carattere >= 'a' && carattere <= 'z')
                    || (carattere >= '0' && carattere <= '9')
                    || carattere == '.' || carattere == '-' || carattere == ','
                    || carattere == '*' || carattere == '_') {
                result.append(carattere);
            }
            else {
                try {
                    byte[] bytes = new String("" + carattere).getBytes("UTF-8");
                    for (byte value : bytes) {
                        result.append("%"
                                + Integer.toHexString(value).replaceAll(
                                        "ffffff", ""));
                    }
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }
    
    /**
     * Esegue la decodifica della stringa in Base64
     * 
     * @param codificata
     *        Stranga codificata
     * @return Stringa decodificata
     * @throws UnsupportedEncodingException
     */
    public static String decodifica(String codificata) {
        codificata = codificata.replaceAll("=", "");
        StringBuilder result = new StringBuilder();
        int x = 0;
        for (int i = 0; i < codificata.length(); i++) {
            if (codificata.charAt(i) == '%') x++;
        }
        Vector<Byte> bytes = new Vector<Byte>(10, 10);
        for (int i = 0; i < codificata.length(); i++) {
            char carattere = codificata.charAt(i);
            if ((carattere >= 'A' && carattere <= 'Z')
                    || (carattere >= 'a' && carattere <= 'z')
                    || (carattere >= '0' && carattere <= '9')
                    || carattere == '.' || carattere == '-' || carattere == ','
                    || carattere == '*' || carattere == '_') {
                
                try {
                    byte[] n = new byte[bytes.size()];
                    for (int j = 0; j < bytes.size(); j++)
                        n[j] = bytes.elementAt(j);
                    result.append(new String(n, "UTF-8"));
                    bytes.clear();
                }
                catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                result.append(carattere);
            }
            else {
                bytes.add((byte) Integer.parseInt(
                        codificata.substring(i + 1, i + 3), 16));
                i += 2;
                
            }
        }
        return result.toString();
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
