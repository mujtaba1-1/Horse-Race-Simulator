import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

class StarTrack extends Track {

    private final int lanePadding = 60;

    public StarTrack(int trackLength, int lanes, ArrayList<Horse> horses) {
        super(trackLength, lanes, horses);
    }

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