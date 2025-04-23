
/**
 * This class represents a horse within a race simulation
 * Each horse has a name, symbol, confidence level and a tracker
 * for the distance travelled. This also tracks whether the horse
 * has fallen during the race. It implements all the methods needed
 * to get and set the horse's attributes.
 * 
 * @author Muhammad Mujtaba Butt
 * @version 4.0
 */

import java.awt.Image;
import javax.swing.ImageIcon;

public class Horse {
    //Fields of class Horse
    private final String name;
    private int distanceTravelled;
    private boolean hasFallen;
    private double confidence;

    private String breed;
    private String colour;
    private String accessory;

    private double finishTime;
    private int totalRaces;
    private int totalWins;

    private Image horseImage;
    private final Image fallenImage = new ImageIcon("images/skull.png").getImage();

    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(String horseName, double horseConfidence) {
       name = horseName;
       confidence = validateConfidence(horseConfidence);
       distanceTravelled = 0;
       hasFallen = false;

       breed = "PLAIN";
       colour = "ORIGINAL";
       accessory = "NONE";

       finishTime = 0.0;
       totalRaces = 0;
       totalWins = 0;

       horseImage = new ImageIcon("images/" + breed + "-" + colour + ".png").getImage();
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
    
    public void setBreed(String newBreed) {
        breed = newBreed.toUpperCase();
        setHorseImage();
    }

    public void setColour(String newColour) {
        colour = newColour.toUpperCase();
        setHorseImage();
    }

    public void setAccessory(String newAccessory) {
        accessory = newAccessory;
    }

    public String getBreed() {
        return breed;
    }

    public String getColour() {
        return colour;
    }

    public String getAccessory() {
        return accessory;
    }

    private void setHorseImage() {
        horseImage = new ImageIcon("images/" + breed + "-" + colour + ".png").getImage();
    }

    public Image getHorseImage() {
        return hasFallen() ? fallenImage : horseImage;
    }

    public int getHeight() {
        return horseImage.getHeight(null);
    }

    public int getWidth() {
        return horseImage.getWidth(null);
    }

    public double getConfidence() {
        return accessory.equals("Saddle") ? validateConfidence(confidence + 0.08) : confidence;
    }

    public int getDistanceTravelled() {
        return distanceTravelled;
    }
    
    public String getName() {
        return name;
    }
    
    public void goBackToStart() {
        distanceTravelled = 0;
        hasFallen = false;
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
    
    public void incrementTotalRaces() {
        totalRaces++;
    }

    public void addTime() {
        finishTime += 0.2;
    }

    public void addWin() {
        totalWins++;
    }

    public double getAverageSpeed() {
        return (double) (distanceTravelled * 10) / finishTime;
    }
    
    public double getFinishTime() {
        return finishTime;
    }

    public double getWinRatio() {
        return ((double) totalWins / totalRaces * 100);
    }
}
