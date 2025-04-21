import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author Muhammad Mujtaba Butt
 * @version 7.0
 */
public class Race extends JFrame {
    
    private int laneCount;
    private int trackLength;
    private String trackShape;
    private String weather;

    private ArrayList<Horse> horses = new ArrayList<>();

    private Track track;

    private JFrame finishedFrame;

    public Race(int laneCount, int trackLength, String trackShape, String weather, JButton applyButton) {
        // Initialise instance variables
        this.laneCount = laneCount + (laneCount - 1);
        this.trackLength = trackLength;
        this.weather = weather;
        this.trackShape = trackShape;
        initialiseHorses();

        // Create Race Frame
        setTitle("Horse Race");
        setSize(800, 600);
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


        // Window listener to close both the race frame and finished frame
        finishedFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                applyButton.setEnabled(true);
            }
        });

        // Generate Track
        switch (trackShape) {
            case "Straight" -> {
                track = new StraightTrack(trackLength, laneCount, horses);
            }
            case "Oval" -> {
            }
            default -> throw new AssertionError();
        }

        add(track);
        setVisible(true);
    }

    /**
     * Initialise the horses to default horses
     */
    private void initialiseHorses() {
        horses.add(new Horse('A', "Alpha", 0.5));
        horses.add(new Horse('B', "Beta", 0.5));
        horses.add(new Horse('C', "Charlie", 0.5));
    }

    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse) {
        horses.add(theHorse);
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

            for (Horse horse : horses) {
                horse.goBackToStart();
            }

            while (!finished) {
                for (Horse horse : horses) {
                    moveHorse(horse);
                }

                // Schedule GUI update on the Event Dispatch Thread
                SwingUtilities.invokeLater(() -> track.updateTrack());

                if (raceWonBy() || allHorsesFallen()) { 
                    finished = true;
                }

                try {
                    TimeUnit.MILLISECONDS.sleep(100); // Sleep in the background thread
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

        for (Horse horse : horses) {
            if (!horse.hasFallen()) {
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
        System.out.println(theHorse.getDistanceTravelled());
        if  (!theHorse.hasFallen()) {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence()) {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence())) {
                theHorse.fall();
                theHorse.setConfidence(theHorse.getConfidence() - 0.1);
            }
        }
    }

    /*   
    private double calculateModifier(Horse theHorse) {
        double modifier = 0.0;
        int distance = theHorse.getDistanceTravelled();
        
        Tile currentTile = lane.get(distance);
        Tile nextTile = distance + 1 < lane.size() ? lane.get(distance + 1) : null;

        if (nextTile != null && !theHorse.hasFallen()) {
            if (nextTile.getY() == currentTile.getY()) {
                modifier += theHorse.getConfidence() * 0.02;
            }
            else {
                modifier -= 0.01;
            }
        }

        return modifier;
    }
    */

    private void weatherEffect(Horse theHorse) {
        theHorse.setConfidence(theHorse.getConfidence() + switch (weather) {
            case "Clear" -> 0.1;
            case "Muddy" -> -0.1;
            case "Icy" -> -0.2;
            default -> 0.0;
        });
    }

    /** 
     * Determines if a horse has won the race
     * If they have won, print a winning message to the terminal
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy() {
        for (Horse horse : horses) {
            if (horse.getDistanceTravelled() == trackLength) {
                horse.setConfidence(horse.getConfidence() + 0.1);
                String winningMessage = String.format("%s has won the race! (Confidence: %.2f)\n", horse.getName(), horse.getConfidence());
                finishedFrame.add(new JLabel(winningMessage));
                finishedFrame.setVisible(true);
                return true;
            }
        }
        return false;
    }

}
