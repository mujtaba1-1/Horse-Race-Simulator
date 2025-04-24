import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 * StarTrack class that extends the Track class
 * This class is responsible for painting the star track and the horses on it
 * It uses parametric equations to calculate the positions of the horses on the track
 * 
 * @author Muhammad Mujtaba Butt
 * @version 1.0
 */

class StarTrack extends Track {

    private final int lanePadding = 60;

    /**
     * Constructor for objects of class StarTrack
     * @param trackLength the length of the racetrack (in metres/yards...)
     * @param lanes the number of lanes in the track
     * @param horses the list of horses participating
     */
    public StarTrack(int trackLength, int lanes, ArrayList<Horse> horses) {
        super(trackLength, lanes, horses);
    }

    /**
     * Paints the star track and the horses on it
     * making use of parametric equations
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

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            int scale = 100 + (i * lanePadding);

            for (double angle = 0; angle <= 2 * Math.PI; angle += 0.01) {
                int x = (int) (scale * Math.pow(Math.cos(angle), 3) + centerX);
                int y = (int) (scale * Math.pow(Math.sin(angle), 3) + centerY);

                g.fillRect(x, y, 2, 2);
            }

            double angle = ((double) horse.getDistanceTravelled() / trackLength) * 2 * Math.PI;

            int horseX = (int) (scale * Math.pow(Math.cos(angle), 3)) + centerX - horseWidth / 2;
            int horseY = (int) (scale * Math.pow(Math.sin(angle), 3)) + centerY - horseHeight / 2;

            g.drawImage(horseImage, horseX, horseY, this);
            g.drawString("" + (i + 1), centerX - 2, centerY - scale - lanePadding / 4);
        }
    }
}