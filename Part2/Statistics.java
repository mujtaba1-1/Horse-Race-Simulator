import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

class Statistics extends JFrame {

    public Statistics(ArrayList<Horse> horses, int laneCount) {
        setPreferredSize(new Dimension(500, 400));
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("Statistics");

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(500, 400));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setFont(new Font("Poppins", Font.PLAIN, 16));

        scrollPane.setViewportView(statsArea);
        add(scrollPane);

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