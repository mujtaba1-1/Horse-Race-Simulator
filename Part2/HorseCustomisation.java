import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

class HorseCustomisation extends JPanel {

    private String[] horseNames = {"Alpha", "Bravo", "Charlie", "Delta", "Echo"};
    private ArrayList<Horse> horses = new ArrayList<>();
    
    private JButton backButton;
    private JButton startButton;

    private boolean isInteractable = true;

    public HorseCustomisation(int laneCount) {
        setPreferredSize(new Dimension(1000, 800));
        setLayout(new BorderLayout());

        // Initalise Horses
        initialiseHorses(laneCount);

        // Heading
        JPanel headingPanel = new JPanel();
        headingPanel.setPreferredSize(new Dimension(1000, 80));
        headingPanel.setLayout(new BorderLayout());
        headingPanel.setBackground(new Color(100, 150, 255));

        JLabel headingText = new JLabel("Horse Customisation");
        headingText.setHorizontalAlignment(JLabel.CENTER);
        headingText.setFont(new Font("Poppins", Font.BOLD, 35));
        headingPanel.add(headingText, BorderLayout.CENTER);

        add(headingPanel, BorderLayout.NORTH);

        // Horse Customisation Panel
        JPanel customisationPanel = new JPanel();
        customisationPanel.setLayout(new GridLayout(10, 2, 20, 20));
        customisationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(customisationPanel, BorderLayout.CENTER);

        // Horse Selection Panel
        JPanel horseSelectionPanel = new JPanel();
        horseSelectionPanel.setBackground(Color.LIGHT_GRAY);
        horseSelectionPanel.setLayout(new GridLayout(laneCount, 1));


        for (int i = 0; i < laneCount; i++) {
            final int index = i;
            JLabel label = new JLabel(horseNames[i]);
            label.setFont(new Font("Poppins", Font.PLAIN, 16));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));

            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (!isInteractable) return;

                    customisationPanel.removeAll();

                    Horse horse = horses.get(index);

                    // Display Horse Name
                    JLabel nameLabel = new JLabel("Horse Name: " + horse.getName());
                    nameLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
                    nameLabel.setHorizontalAlignment(JLabel.CENTER);
                    customisationPanel.add(nameLabel);

                    // Display Horse Confidence
                    JLabel confidenceLabel = new JLabel(String.format("Confidence: %.2f", horse.getConfidence()));
                    confidenceLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
                    confidenceLabel.setHorizontalAlignment(JLabel.CENTER);
                    customisationPanel.add(confidenceLabel);

                    // Display Horse Image
                    JLabel imageLabel = new JLabel(new ImageIcon(horse.getHorseImage()));
                    customisationPanel.add(imageLabel);

                    // Breed Selector
                    JLabel breedLabel = new JLabel("Select Breed:");
                    breedLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
                    customisationPanel.add(breedLabel);
                    
                    JComboBox<String> breedComboBox = new JComboBox<>(new String[]{"Plain", "Skeleton", "Bold"}); // Replace with actual breeds
                    customisationPanel.add(breedComboBox);

                    // Color Selector
                    JLabel colorLabel = new JLabel("Select Color:");
                    colorLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
                    customisationPanel.add(colorLabel);
                    
                    JComboBox<String> colorComboBox = new JComboBox<>(new String[]{"Original", "Red", "Green"}); // Replace with actual colors
                    customisationPanel.add(colorComboBox);

                    // Accessory Selector
                    JLabel accessoryLabel = new JLabel("Select Accessory:");
                    accessoryLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
                    customisationPanel.add(accessoryLabel);
                    
                    JComboBox<String> accessoryComboBox = new JComboBox<>(new String[]{"Saddle", "Bridle", "Shoe"}); // Replace with actual accessories
                    customisationPanel.add(accessoryComboBox);

                    // Apply Button
                    JButton applyButton = new JButton("Apply Changes");
                    applyButton.setFont(new Font("Poppins", Font.PLAIN, 16));
                    customisationPanel.add(applyButton);

                    applyButton.addActionListener(e1 -> {
                        String selectedBreed = (String) breedComboBox.getSelectedItem();
                        String selectedColor = (String) colorComboBox.getSelectedItem();
                        String selectedAccessory = (String) accessoryComboBox.getSelectedItem();

                        horse.setBreed(selectedBreed);
                        horse.setColour(selectedColor);
                        horse.setAccessory(selectedAccessory);

                        ImageIcon newHorseImage = new ImageIcon(horse.getHorseImage());
                        imageLabel.setIcon(newHorseImage);

                        double newConfidence = horse.getConfidence();
                        confidenceLabel.setText(String.format("Confidence: %.2f", newConfidence));

                        System.out.println(horse.getName());
                        System.out.println("Breed: " + horse.getBreed());
                        System.out.println("Color: " + horse.getColour());
                        System.out.println("Accessory: " + horse.getAccessory());
                        
                        JOptionPane.showMessageDialog(customisationPanel, "Changes applied to " + horse.getName() + "!");

                        customisationPanel.revalidate();
                        customisationPanel.repaint();
                    });

                    customisationPanel.revalidate();
                    customisationPanel.repaint();
                }
            });

            horseSelectionPanel.add(label);
        }

        add(horseSelectionPanel, BorderLayout.WEST);

        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(null);
        footerPanel.setPreferredSize(new Dimension(1000, 100));

        backButton = new JButton("Back");
        backButton.setBounds(50, 30, 200, 30);
        backButton.setFont(new Font("Poppins", Font.PLAIN, 20));
        backButton.setFocusable(false);
        footerPanel.add(backButton);

        startButton = new JButton("Start Race");
        startButton.setBounds(750, 30, 200, 30);
        startButton.setFont(new Font("Poppins", Font.PLAIN, 20));
        startButton.setFocusable(false);
        footerPanel.add(startButton);

        add(footerPanel, BorderLayout.SOUTH);
    }

    private void initialiseHorses(int laneCount) {
        for (int i = 0; i < laneCount; i++) {
            horses.add(new Horse(horseNames[i], 0.5));
        }
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public ArrayList<Horse> getHorses() {
        return horses;
    }

    public void setInteractable(boolean interactable) {
        isInteractable = interactable;
        for (Component component : this.getComponents()) {
            setComponentEnabled(component, interactable);
        }
    }

    private void setComponentEnabled(Component component, boolean enabled) {
        component.setEnabled(enabled);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setComponentEnabled(child, enabled);
            }
        }
    }
}