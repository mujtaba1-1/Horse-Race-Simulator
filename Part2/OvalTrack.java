import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

class OvalTrack extends Track {

    private final int lanePadding = 30;

    public OvalTrack(int trackLength, int lanes, ArrayList<Horse> horses) {
        super(trackLength, lanes, horses);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        int radiusX = (getWidth() - lanePadding * 2) / 2;
        int radiusY = (getHeight() - lanePadding * 2) / 2;

        for (int i = 0; i < lanes; i++) {
            Horse horse = horses.get(i);

            Image horseImage = horse.getHorseImage();
            int horseWidth = horse.getWidth();
            int horseHeight = horse.getHeight();

            int laneRadiusX = radiusX - i * lanePadding;
            int laneRadiusY = radiusY - i * lanePadding;

            g.drawOval(centerX - laneRadiusX, centerY - laneRadiusY, laneRadiusX * 2, laneRadiusY * 2);
            g.drawString("Lane " + (i + 1), centerX, centerY - laneRadiusY - lanePadding / 2);

            double angle = ((double) horse.getDistanceTravelled() / trackLength) * 2 * Math.PI;

            int horseX = centerX + (int) (laneRadiusX * Math.cos(angle)) - horseWidth / 2;
            int horseY = centerY + (int) (laneRadiusY * Math.sin(angle)) - horseHeight / 2;

            g.drawImage(horseImage, horseX, horseY, this);
        }
    }
}