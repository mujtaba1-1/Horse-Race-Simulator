import java.awt.event.ActionEvent;
import javax.swing.*;

public class HorseRacingGUI extends JFrame {

    // Track Variables
    private int laneCount;
    private int trackLength;
    private String trackShape;
    private String weather;
    private TrackCustomisation tc;

    // Race
    private Race race;

    HorseRacingGUI() {
        setTitle("Horse Racing Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        displayTrackCustomisation();

        setVisible(true);
    }

    private void displayTrackCustomisation() {
        tc = new TrackCustomisation();
        add(tc);

        JButton applyButton = tc.getApplyButton();
        applyButton.addActionListener((ActionEvent e) -> {
            applyButton.setEnabled(false);
            laneCount = tc.getLaneCount();
            trackLength = tc.getTrackLength();
            trackShape = tc.getSelectedShape();
            weather = tc.getSelectedWeather();
            
            System.out.println("Lane Count: " + laneCount);
            System.out.println("Track Length: " + trackLength);
            System.out.println("Track Shape: " + trackShape);
            System.out.println("Weather: " + weather);
            
            race = new Race(laneCount, trackLength, trackShape, weather, applyButton);
            race.startRace();
        });
    }

}
