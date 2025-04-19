import java.awt.Dimension;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author Muhammad Mujtaba Butt
 * @version 5.0
 */
public class Race extends JFrame {
    
    private int laneCount;
    private int trackLength;
    private String trackShape;
    private String weather;

    private JTextArea outputArea;
    private int raceLength = 10;

    private ArrayList<ArrayList<Tile>> track = new ArrayList<>();
    private final int TILEWIDTH = 30;
    private JPanel trackPanel;

    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;

    public Race(int laneCount, int trackLength, String trackShape, String weather) {
        // Initialise instance variables
        this.laneCount = laneCount + (laneCount - 1);
        this.trackLength = trackLength;
        this.trackShape = trackShape;
        this.weather = weather;
        initialiseHorses();

        // Create Horse Frame
        setTitle("Horse Race");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create race panel
        trackPanel = new JPanel();
        trackPanel.setLayout(null);
        trackPanel.setPreferredSize(new Dimension(trackLength * TILEWIDTH, laneCount * TILEWIDTH));

        JScrollPane scrollPane = new JScrollPane(trackPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        // Generate Track
        switch (trackShape) {
            case "Straight":
                InitialiseStraightTrack();
                break;
            default:
                throw new AssertionError();
        }
        add(scrollPane);
        setVisible(true);
        startRace();
    }

    private void InitialiseStraightTrack() {
        int x = 0;
        int startingHeight = 400 - ((TILEWIDTH * laneCount) / 2);
        
        for (int i = 0; i < laneCount; i++) {
            ArrayList<Tile> lane = new ArrayList<>();
            for (int j = 0; j < trackLength; j++) {
                Tile tile = new Tile(x + (TILEWIDTH * j), 
                                     startingHeight + (TILEWIDTH * i),
                                     TILEWIDTH,
                                     i % 2 == 0);
                lane.add(tile);
            }
            track.add(lane);
        } 
    }

    public void generateTrack() {
        trackPanel.removeAll();
        for (ArrayList<Tile> lane : track) {
            for (Tile tile : lane) {
                trackPanel.add(tile);
            }
        }
        trackPanel.repaint();
        trackPanel.revalidate();
    }

    /**
     * Initialise the horses to default horses
     */
    private void initialiseHorses() {
        lane1Horse = new Horse('A', "Alpha", 0.8);
        lane2Horse = new Horse('B', "Bravo", 0.6);
        lane3Horse = new Horse('C', "Charlie", 0.5);
    }

    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber) {
        switch (laneNumber) {
            case 1:
                lane1Horse = theHorse;
                break;
            case 2:
                lane2Horse = theHorse;
                break;
            case 3:
                lane3Horse = theHorse;
                break;
            default:
                System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
                break;
        }
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

            lane1Horse.goBackToStart();
            lane2Horse.goBackToStart();
            lane3Horse.goBackToStart();

            printRace();

            while (!finished) {
                moveHorse(lane1Horse);
                moveHorse(lane2Horse);
                moveHorse(lane3Horse);

                // Schedule GUI update on the Event Dispatch Thread
                SwingUtilities.invokeLater(this::printRace);

                if (raceWonBy(lane1Horse) || 
                    raceWonBy(lane2Horse) || 
                    raceWonBy(lane3Horse) || 
                    allHorsesFallen()) {
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
        boolean allFallen = lane1Horse.hasFallen() && lane2Horse.hasFallen() && lane3Horse.hasFallen();
        if (allFallen) {
            System.out.println("All horses have fallen. No winner!");
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
            if (Math.random() < theHorse.getConfidence()) {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence())) {
                theHorse.fall();
                theHorse.setSymbol('\u2620');
                theHorse.setConfidence(theHorse.getConfidence() - 0.1);
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     * If they have won, print a winning message to the terminal
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse) {
        if (theHorse.getDistanceTravelled() == trackLength) {
            //if the horse has won, print a message to the terminal
            theHorse.setConfidence(theHorse.getConfidence() + 0.1);
            System.out.printf("%s has won the race! (Confidence: %.1f)\n", theHorse.getName(), theHorse.getConfidence());

            return true;
        }
        else {
            return false;
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace() {
        displayHorse(lane1Horse, 0);
        displayHorse(lane2Horse, 2);
        displayHorse(lane3Horse, 4);
        generateTrack();
    }

    private void displayHorse(Horse horse, int laneIndex) {
        int distance = horse.getDistanceTravelled();
        distance = Math.min(distance, trackLength - 1);

        ArrayList<Tile> lane = track.get(laneIndex);

        if (distance != 0) {
            lane.get(distance - 1).setSymbol(' ');
        }

        lane.get(distance).setSymbol(horse.getSymbol());
    }

}
