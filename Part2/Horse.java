
/**
 * This class represents a horse within a race simulation
 * Each horse has a name, symbol, confidence level and a tracker
 * for the distance travelled. This also tracks whether the horse
 * has fallen during the race. It implements all the methods needed
 * to get and set the horse's attributes.
 * 
 * @author Muhammad Mujtaba Butt
 * @version 3.0
 */

import java.awt.Image;
import javax.swing.ImageIcon;

public class Horse {
    //Fields of class Horse
    private final String name;
    private char symbol;
    private int distanceTravelled;
    private boolean hasFallen;
    private double confidence;

    private final Image horseImage = new ImageIcon("images/horse.png").getImage();

    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence) {
       name = horseName;
       symbol = horseSymbol;
       confidence = validateConfidence(horseConfidence);
       distanceTravelled = 0;
       hasFallen = false;
    }
    
    //Private methods
    /**
     * Validates the confidence level of the horse
     * to ensure it does not exceed 1.0 or fall below 0.0.
     * If it does, it sets the confidence to 0.99 or 0.01 respectively.
     */
    private double validateConfidence(double confidence) {
        if (confidence >= 1.0) {
            return 0.9;
        }
        else if (confidence <= 0.0) {
            return 0.1;
        }
        return confidence;
    }
    
    //Other methods of class Horse
    public void fall() {
        hasFallen = true;
    }
    
    public Image getHorseImage() {
        return horseImage;
    }

    public double getConfidence() {
        return confidence;
    }

    public int getDistanceTravelled() {
        return distanceTravelled;
    }
    
    public String getName() {
        return name;
    }
    
    public char getSymbol() {
        return symbol;
    }
    
    public void goBackToStart() {
        distanceTravelled = 0;
    }
    
    public boolean hasFallen() {
        return hasFallen;
    }

    public void moveForward() {
        distanceTravelled++;
    }

    public void setConfidence(double newConfidence) {
        confidence = validateConfidence(newConfidence);
    }
    
    public void setSymbol(char newSymbol) {
        symbol = newSymbol;
    }
    
}
