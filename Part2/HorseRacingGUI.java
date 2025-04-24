import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 * This class creates the GUI for the horse racing simulator
 * It also controls the flow of the program
 * by switching between the track customisation and horse customisation panels
 * 
 * @author Muhammad Mujtaba Butt
 * @version 1.0
 */

public class HorseRacingGUI extends JFrame {

    // Track Variables
    private int laneCount;
    private int trackLength;
    private String trackShape;
    private String weather;
    private TrackCustomisation tc;

    private final int MAX_LANES = 5;

    private HorseCustomisation hc;
    private ArrayList<Horse> horses = new ArrayList<>();
    private String[] horseNames = {"Alpha", "Bravo", "Charlie", "Delta", "Echo"};

    // Race
    private Race race;

    public HorseRacingGUI() {
        // Set up the main frame
        setTitle("Horse Racing Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        initialiseHorses(MAX_LANES);

        tc = new TrackCustomisation();
        add(tc);

        JButton applyButton = tc.getApplyButton();

        // Add action listener to the apply button
        applyButton.addActionListener((ActionEvent e) -> {
            // Gets and sets the track customisation values
            laneCount = tc.getLaneCount();
            trackLength = tc.getTrackLength();
            trackShape = tc.getSelectedShape();
            weather = tc.getSelectedWeather();

            hc = new HorseCustomisation(laneCount, horses);  // Reinitialize the horse customisation panel

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
                hc.setInteractable(false); // Disable horse customisation panel

                // Initialise a new race with the selected horses and track customisation
                Race race = new Race(laneCount, trackLength, trackShape, weather, horses, hc);
                race.startRace(); // Start the race
            });

            revalidate();
            repaint();
        });

        setVisible(true);
    }

    /**
     * This method is used to initialise the horses with default names and speeds
     * @param laneCount The number of lanes in the
     */
    private void initialiseHorses(int laneCount) {
        for (int i = 0; i < laneCount; i++) {
            horses.add(new Horse(horseNames[i], 0.5));
        }
    }
}
