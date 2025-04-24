import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * This class is responsible for displaying the statistics
 * of each horse that participated in the race.
 * 
 * @author Muhammad Mujtaba Butt
 * @version 1.0
 */

class Statistics extends JFrame {

    /**
     * Constructor for objects of class Statistics
     * @param horses the list of horses participating
     * @param laneCount the number of lanes in the track
     */
    public Statistics(ArrayList<Horse> horses, int laneCount) {
        setPreferredSize(new Dimension(500, 400));
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Statistics");

        // Set up the scroll pane which will contain the statistics text area
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(500, 400));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a JTextArea to display the statistics
        JTextArea statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setFont(new Font("Poppins", Font.PLAIN, 16));

        scrollPane.setViewportView(statsArea);
        add(scrollPane);

        // For each horse, display all its attributes and statistics within the race
        for (int i = 0; i < laneCount; i++) {
            Horse horse = horses.get(i);
            statsArea.append("Horse Name: " + horse.getName() + "\n");
            statsArea.append("Breed: " + horse.getBreed() + "\n");
            statsArea.append("Colour: " + horse.getColour() + "\n");
            statsArea.append("Accessory: " + horse.getAccessory() + "\n");
            statsArea.append("Distance Travelled: " + horse.getDistanceTravelled() + "m\n");
            statsArea.append("Has Fallen: " + (horse.hasFallen() ? "Yes" : "No") + "\n");
            statsArea.append(String.format("Confidence: %.2f \n", horse.getConfidence()));
            statsArea.append(String.format("Finish Time: %.2f s\n", horse.getFinishTime()));
            statsArea.append(String.format("Average Speed: %.2f m/s\n", horse.getAverageSpeed()));
            statsArea.append(String.format("Win Percentage: %.2f%% \n", horse.getWinRatio()));
            statsArea.append("-------------------------------------------------\n");
        }

        pack();
    }
}