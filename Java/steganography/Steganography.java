package steganography;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.lang.Math;

import javax.imageio.ImageIO;

/**
 * Classe dedita alla scrittura e alla lettura di testo in codifica ASCII all'interno di un'immagine.
 * 
 */
public class Steganography {
    private String imagePath, destinationPath;
    
    /**
     * Costruttore
     * 
     * @param imagePath
     * @throws FileNotFoundException
     */
    public Steganography(String imagePath) throws FileNotFoundException {
        setImagePath(imagePath);
    }
    
    /**
     * 
     * @param imagePath
     * @param destinationPath
     * @throws FileNotFoundException
     */
    public Steganography(String imagePath, String destinationPath)
            throws FileNotFoundException {
        this(imagePath);
        setDestination(destinationPath);
    }
    
    /**
     * Operazione di lettura dall'immagine identificata dall'immagine salvata
     */
    public String readImage() {
        return read(getImagePath());
    }
    
    /**
     * Operazione di lettura dall'immagine
     * 
     * @param filename
     */
    public static String read(String filename) throws FileNotFoundException, IOException{
        StringBuilder x = new StringBuilder();

        BufferedImage mod = ImageIO.read(new File(filename));
        for (int i = 0; i < mod.getWidth(); i++) {
            for (int j = 0; j < mod.getHeight(); j++) {
                x.append(Math.abs(mod.getRGB(i, j) % 2));
            }
        }

        return AsciiToString(x.toString());
    }
    
    /**
     * Operazione di scrittura sull'immagine e salvataggio
     * 
     * @param message
     */
    public void writeImage(String message) throws IOException,IllegalArgumentException{
        writeImage(message, null);
    }
    
    /**
     * Operazione di scrittura sull'immagine e salvataggio
     * 
     * @param message
     * @param destinationPath
     */
    public void writeImage(String message, String destinationPath) throws IOException,IllegalArgumentException {
        BufferedImage img = ImageIO.read(new File(getImagePath()));

        String stega = AsciiToBinary(message);

        int index = 0, value;
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                value = 0;
                index = i * img.getHeight() + j;
                if (index < stega.length()
                        && stega.charAt(index) == '1') value = 1;
                img.setRGB(i, j, img.getRGB(i, j) - (img.getRGB(i, j) % 2)
                        - value);
            }
        }

        if (index < stega.length()){
            throw new IllegalArgumentException("Message too long!");
        }

        if(this.getDestination() == null){
            int start = getImagePath().lastIndexOf('/');
            start = (start == -1) ? 0 : start + 1;

            setDestination(getImagePath().substring(0, start)
                    + "mod"
                    + getImagePath().substring(start, getImagePath().indexOf('.'))
                    + ".png");
        }

        ImageIO.write(img, "png", new File(this.getDestination()));
    }
    
    /**
     * 
     * @return string
     */
    public String getImagePath() {
        return this.imagePath;
    }
    
    /**
     * Assegna il valore indicato come origine dell'immagine su cui effettuare la codificare.
     * 
     * @param path
     * @throws FileNotFoundException
     */
    public void setImagePath(String path) throws FileNotFoundException {
        File f = new File(path);
        if (!f.exists() || f.isDirectory()) {
            throw new FileNotFoundException();
        };
        
        this.imagePath = path;
    }
    
    /**
     * @return string
     */
    public String getDestination() {
        return this.destinationPath;
    }
    
    /**
     * 
     * @param path
     */
    public void setDestination(String path) {
        File f = new File(path);
        if (f.exists() || f.isDirectory()) {
            throw new FileNotFoundException();
        };

        this.destinationPath = path;
    }
    
    /**
     * Analizza e converte una stringa in una sequenza di numeri secondo la
     * codifica ASCII
     * 
     * @param asciiString
     * @return string
     */
    public static String AsciiToBinary(String asciiString) {
        byte[] bytes = asciiString.getBytes();
        
        StringBuilder binary = new StringBuilder();

        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }

        return binary.toString();
    }
    
    /**
     * Analizza e converte una sequenza si numeri in una stringa secondo la
     * codifica ASCII
     * 
     * @param intString
     * @return string
     */
    public static String AsciiToString(String intString) {
        StringBuilder result = new StringBuilder();

        int nextChar = 0;

        for (int i = 0; i < intString.length() / 8; i++) {
            nextChar = 0;
            for (int j = 0; j < 8; j++) {
                nextChar += ((int) intString.charAt((j + i * 8))) % 2
                        * Math.pow(2, 8 - (j % 8) - 1);
            }
            result.append((char) nextChar);
        }

        return result.toString();
    }
}