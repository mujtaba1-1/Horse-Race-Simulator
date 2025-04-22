import java.awt.event.ActionEvent;
import javax.swing.*;

public class HorseRacingGUI extends JFrame {

    // Track Variables
    private int laneCount;
    private int trackLength;
    private String trackShape;
    private String weather;
    private TrackCustomisation tc;

    private HorseCustomisation hc;

    // Race
    private Race race;

    HorseRacingGUI() {
        setTitle("Horse Racing Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        tc = new TrackCustomisation();
        add(tc);

        JButton applyButton = tc.getApplyButton();
        applyButton.addActionListener((ActionEvent e) -> {
            laneCount = tc.getLaneCount();
            trackLength = tc.getTrackLength();
            trackShape = tc.getSelectedShape();
            weather = tc.getSelectedWeather();

            hc = new HorseCustomisation(laneCount);  // Reinitialize the horse customisation panel

            // Update the content pane
            getContentPane().removeAll();
            getContentPane().add(hc);

            // Re-add back button listener after adding hc panel
            JButton backButton = hc.getBackButton();
            backButton.addActionListener((ActionEvent ev) -> {
                // Switch back to track customisation
                getContentPane().removeAll();
                getContentPane().add(tc);
                revalidate();
                repaint();
            });

            JButton startButton = hc.getStartButton();
            startButton.addActionListener((ActionEvent ev) -> {
                hc.setInteractable(false);
                Race race = new Race(laneCount, trackLength, trackShape, weather, hc.getHorses(), hc);
                race.startRace();
            });

            revalidate();
            repaint();
        });

        setVisible(true);
    }
}
