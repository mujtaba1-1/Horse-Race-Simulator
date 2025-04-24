import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Track class that serves as a base class for different types of tracks
 * This class is responsible for holding the track length, number of lanes, and the horses participating
 * 
 * @author Muhammad Mujtaba Butt
 * @version 1.0
 */

class Track extends JPanel {
    protected int trackLength;
    protected int lanes;
    protected ArrayList<Horse> horses;

    /**
     * Constructor for objects of class Track
     * @param trackLength the length of the racetrack (in metres/yards...)
     * @param lanes the number of lanes in the track
     * @param horses the list of horses participating
     */
    public Track(int trackLength, int lanes, ArrayList<Horse> horses) {
        this.trackLength = trackLength;
        this.lanes = lanes;
        this.horses = horses;
    }

    /**
     * Method to update the track and repaint it
     */
    public void updateTrack() {
        repaint();
    }
}