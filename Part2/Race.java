import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author Muhammad Mujtaba Butt
 * @version 7.0
 */
public class Race extends JFrame {
    
    private int trackLength;
    private String weather;
    private int laneCount;

    private ArrayList<Horse> horses;

    private Track track;

    private JFrame finishedFrame;

    public Race(int laneCount, int trackLength, String trackShape, String weather, ArrayList<Horse> horses, HorseCustomisation hc) {
        // Initialise instance variables
        this.trackLength = trackLength;
        this.weather = weather;
        this.horses = horses;
        this.laneCount = laneCount;
        
        // Create Race Frame
        setTitle("Horse Race");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Create Winning Frame
        finishedFrame = new JFrame("Winner");
        finishedFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        finishedFrame.setSize(300, 100);
        finishedFrame.setLocationRelativeTo(null);
        finishedFrame.setResizable(false);

        JButton viewStatsButton = new JButton("View Stats");
        viewStatsButton.setFocusable(false);
        finishedFrame.add(viewStatsButton);

        viewStatsButton.addActionListener((ActionEvent e) -> {
            Statistics stats = new Statistics(horses, laneCount);
            finishedFrame.dispose();
            stats.setVisible(true);

            // Window listener to close both the race frame and finished frame
            stats.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    dispose();
                    for (int i = 0; i < laneCount; i++) {
                        horses.get(i).goBackToStart();
                    }
                    hc.setInteractable(true);
                }
            });
        });

        // Window listener to close both the race frame and finished frame
        finishedFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                for (int i = 0; i < laneCount; i++) {
                    horses.get(i).goBackToStart();
                }
                hc.setInteractable(true);
            }
        });

        // Generate Track
        switch (trackShape) {
            case "Straight" -> {
                track = new StraightTrack(trackLength, laneCount, horses);
            }
            case "Oval" -> {
                track = new OvalTrack(trackLength, laneCount, horses);
            }
            case "Star" -> {
                track = new StarTrack(trackLength, laneCount, horses);
            }
            default -> throw new AssertionError();
        }
        add(track);
        setVisible(true);
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace() {
        new Thread(() -> {
            boolean finished = false;

            for (int i = 0; i < laneCount; i++) {
                horses.get(i).goBackToStart();
                horses.get(i).incrementTotalRaces();
            }

            while (!finished) {
                for (int i = 0; i < laneCount; i++) {
                    moveHorse(horses.get(i));

                }

                // Schedule GUI update on the Event Dispatch Thread
                SwingUtilities.invokeLater(() -> track.updateTrack());

                if (raceWonBy() || allHorsesFallen()) { 
                    finished = true;
                }

                try {
                    Thread.sleep(100); // Sleep in the background thread
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    /**
     * Check if all horses have fallen.
     * If they have, print a message to the terminal
     * and return true.
     * 
     * @return true if all horses have fallen, false otherwise
     */
    private boolean allHorsesFallen() {
        boolean allFallen = false;

        for (int i = 0; i < laneCount; i++) {
            if (!horses.get(i).hasFallen()) {
                allFallen = false;
                break;
            } else {
                allFallen = true;
            }
        }

        if (allFallen) {
            finishedFrame.add(new JLabel("All horses have fallen. No winner!"));
            finishedFrame.setVisible(true);
        }
        return allFallen;
    }

    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse) {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        if  (!theHorse.hasFallen()) {
            //the probability that the horse will move forward depends on the confidence;
            theHorse.addTime();
            double moveChance = ("Bridle".equals(theHorse.getAccessory()) ? 0.08 : 0) + theHorse.getConfidence();
            if (moveChance > 1) {
                moveChance = 1.0;
            }

            if (Math.random() < moveChance) {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2

            double weatherEffect = weatherEffect();
            double fallChance = (("Shoe".equals(theHorse.getAccessory()) ? 0.2 : 0.1) * (theHorse.getConfidence() + weatherEffect) * (theHorse.getConfidence() + weatherEffect));

            if (Math.random() < fallChance) {
                theHorse.fall();
                theHorse.setConfidence(theHorse.getConfidence() - 0.1);
            }
        }
    }

    private double weatherEffect() {
            return switch (weather) {
                case "Clear" -> -0.05;
                case "Muddy" -> +0.05;
                case "Icy" -> +0.1;
                default -> 0.0;
            };
    }

    /** 
     * Determines if a horse has won the race
     * If they have won, print a winning message to the terminal
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy() {
        for (int i = 0; i < laneCount; i++) {
            Horse horse = horses.get(i);
            if (horse.getDistanceTravelled() == trackLength) {
                horse.addWin();
                horse.setConfidence(horse.getConfidence() + 0.15);
                String winningMessage = String.format("%s has won the race! (Confidence: %.2f)\n", horse.getName(), horse.getConfidence());
                finishedFrame.add(new JLabel(winningMessage));
                finishedFrame.setVisible(true);
                return true;
            }
        }
        return false;
    }

}
