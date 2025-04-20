import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;
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

    private Map<String, Color> weatherColor = Map.of(
        "Clear", Color.decode("#7CFC00"),
        "Muddy", Color.decode("#70543E"),
        "Icy", Color.decode("#86D6D8")
    );

    private ArrayList<ArrayList<Tile>> track = new ArrayList<>();
    private JPanel trackPanel;

    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;

    private final int TILEWIDTH;
    private final int GRID_WIDTH = 800;
    private final int GRID_HEIGHT = 600;   

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
        setSize(GRID_WIDTH, GRID_HEIGHT);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

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
                TILEWIDTH = 40;
                InitialiseStraightTrack();
            }
            case "Oval" -> {
                TILEWIDTH = 20;
                InitialiseOvalTrack();
            }
            default -> throw new AssertionError();
        }

        // Create race panel
        trackPanel = new JPanel();
        trackPanel.setLayout(null);
        trackPanel.setBackground(weatherColor.get(weather));
        trackPanel.setPreferredSize(new Dimension(trackLength * TILEWIDTH, laneCount * TILEWIDTH));

        JScrollPane scrollPane = new JScrollPane(trackPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        
        add(scrollPane);
        setVisible(true);
        generateTrack();
    }

    private void InitialiseStraightTrack() {
        int x = 0;
        int startingHeight = GRID_HEIGHT / 2 - ((TILEWIDTH * laneCount) / 2);
        
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

    private void InitialiseOvalTrack() {
        int centerX = GRID_WIDTH / 2;
        int centerY = GRID_HEIGHT / 2;

        int a = trackLength * 8;
        int b = trackLength * 3;

        for (int i = 0; i < laneCount; i++) {
            track.add(new ArrayList<>());
        }

        // Step through angles from 0 to 360 degrees in small increments
        for (double angle = 0; angle < 2 * Math.PI; angle += 0.01) {
            for (int laneIndex = 0; laneIndex < laneCount; laneIndex++) {
                int innerA = a + TILEWIDTH * laneIndex + TILEWIDTH / 2;
                int innerB = b + TILEWIDTH * laneIndex + TILEWIDTH / 2;

                double xCenter = centerX + innerA * Math.cos(angle);
                double yCenter = centerY + innerB * Math.sin(angle);

                int x = (int)(xCenter / TILEWIDTH) * TILEWIDTH;
                int y = (int)(yCenter / TILEWIDTH) * TILEWIDTH;

                // Check if the tile already exists in this lane to avoid duplicates
                ArrayList<Tile> lane = track.get(laneIndex);
                boolean exists = false;
                for (Tile t : lane) {
                    if (t.getX() == x && t.getY() == y) {
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    Tile tile = new Tile(x, y, TILEWIDTH, laneIndex % 2 == 0);
                    lane.add(tile);
                }
            }
        }
    }

    private void generateTrack() {
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
        lane1Horse = new Horse('A', "Alpha", 0.5, 0);
        weatherEffect(lane1Horse);
        lane2Horse = new Horse('B', "Bravo", 0.5, 2);
        weatherEffect(lane2Horse);
        lane3Horse = new Horse('C', "Charlie", 0.5, 4);
        weatherEffect(lane3Horse);
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

        double confidenceModifier = calculateModifier(theHorse);


        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        if  (!theHorse.hasFallen()) {
            //the probability that the horse will move forward depends on the confidence;
            if (trackShape.equals("Oval")) {
                double moveChance = theHorse.getConfidence() * (1.0 + (theHorse.getLaneIndex() * 0.1));

                if (Math.random() < moveChance) {
                    theHorse.moveForward();
                    theHorse.setConfidence(theHorse.getConfidence() + confidenceModifier);
                }
            }
            else {
                if (Math.random() < theHorse.getConfidence()) {
                    theHorse.moveForward();
                    theHorse.setConfidence(theHorse.getConfidence() + confidenceModifier);
                }
            }
            
            if (weather.equals("Icy") || weather.equals("Muddy")) {
                if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence())) {
                    theHorse.fall();
                    theHorse.setSymbol('\u2620');
                    theHorse.setConfidence(theHorse.getConfidence() - 0.1);
                }
            }
            else {
                if (Math.random() < (0.05*theHorse.getConfidence()*theHorse.getConfidence())) {
                    theHorse.fall();
                    theHorse.setSymbol('\u2620');
                    theHorse.setConfidence(theHorse.getConfidence() - 0.1);
                }
            }
        }
    }
        
    private double calculateModifier(Horse theHorse) {
        double modifier = 0.0;
        ArrayList<Tile> lane = track.get(theHorse.getLaneIndex());
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
    private boolean raceWonBy(Horse theHorse) {
        if (theHorse.getDistanceTravelled() == track.get(theHorse.getLaneIndex()).size()) {
            //if the horse has won, print a message to the terminal
            theHorse.setConfidence(theHorse.getConfidence() + 0.1);
            String winningMessage = String.format("%s has won the race! (Confidence: %.2f)\n", theHorse.getName(), theHorse.getConfidence());
            finishedFrame.add(new JLabel(winningMessage));
            finishedFrame.setVisible(true);
            return true;
        }
        else {
            return false;
        }
    }
    
    /***
     * Print the race
     */
    private void printRace() {
        displayHorse(lane1Horse);
        System.out.println("Confidence at " + lane1Horse.getDistanceTravelled() + ": " + lane1Horse.getConfidence());
        displayHorse(lane2Horse);
        displayHorse(lane3Horse);
        generateTrack();
    }

    private void displayHorse(Horse horse) {
        int laneIndex = horse.getLaneIndex();
        int distance = horse.getDistanceTravelled();
        ArrayList<Tile> lane = track.get(laneIndex);

        distance = Math.min(distance, lane.size() - 1);

        if (distance != 0) {
            lane.get(distance - 1).setSymbol(' ');
        }

        lane.get(distance).setSymbol(horse.getSymbol());
    }

}
