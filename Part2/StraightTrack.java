import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 * StraightTrack class that extends the Track class
 * This class is responsible for painting the straight track and the horses on it
 * It uses simple linear equations to calculate the positions of the horses on the track
 * 
 * @author Muhammad Mujtaba Butt
 * @version 1.0
 */

class StraightTrack extends Track {

    private final int verticalOffset = 15;
    private final int horizontalOffset = 15;
    private final int thickness = 3;

    /**
     * Constructor for objects of class StraightTrack
     * @param trackLength the length of the racetrack (in metres/yards...)
     * @param lanes the number of lanes in the track
     * @param horses the list of horses participating
     */
    public StraightTrack(int trackLength, int lanes, ArrayList<Horse> horses) {
        super(trackLength, lanes, horses);
    }

    /**
     * Paints the straight track and the horses on it
     * using simple linear equations
     * @param g the Graphics object to paint on
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        for (int i = 0; i < lanes; i++) {
            Horse horse = horses.get(i);

            Image horseImage = horse.getHorseImage();
            int horseWidth = horse.getWidth();
            int horseHeight = horse.getHeight();
            
            int lineHeight = horseHeight + 2 * verticalOffset;

            g.fillRect(horseWidth + horizontalOffset, i * lineHeight, thickness, lineHeight);
            g.fillRect(getWidth() - horseWidth - horizontalOffset, i * lineHeight, thickness, lineHeight);
            g.drawString("Lane " + (i + 1), getWidth() / 2, i * lineHeight + verticalOffset + horseHeight / 2);

            int horseX = horizontalOffset + (int) (((double) horse.getDistanceTravelled() / (double) trackLength) * (getWidth() - 2*(horseWidth + horizontalOffset) - thickness));
            int horseY = i * lineHeight + verticalOffset;

            g.drawImage(horseImage, horseX, horseY, this);
        }
    }
}