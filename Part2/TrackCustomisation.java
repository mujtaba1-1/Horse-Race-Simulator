import java.awt.*;
import javax.swing.*;

class TrackCustomisation extends JPanel {
    private JSpinner laneSpinner;
    private JSpinner lengthSpinner;
    private JComboBox<String> shapeSelector;
    private JComboBox<String> weatherSelector;
    private JButton applyButton;

    public TrackCustomisation() {
        setPreferredSize(new Dimension(1000, 800));
        setLayout(new BorderLayout());

        // Heading
        JPanel headingPanel = new JPanel();
        headingPanel.setPreferredSize(new Dimension(1000, 80));
        headingPanel.setLayout(new BorderLayout());
        headingPanel.setBackground(new Color(100, 150, 255));

        JLabel headingText = new JLabel("Track Customisation");
        headingText.setHorizontalAlignment(JLabel.CENTER);
        headingText.setFont(new Font("Poppins", Font.BOLD, 35));
        headingPanel.add(headingText, BorderLayout.CENTER);

        add(headingPanel, BorderLayout.NORTH);

        // Placeholder panels for the sides
        JPanel eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(200, 720));
        eastPanel.setBackground(Color.LIGHT_GRAY);

        JPanel westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(200, 720));
        westPanel.setBackground(Color.LIGHT_GRAY);

        add(eastPanel, BorderLayout.EAST);
        add (westPanel, BorderLayout.WEST);

        // Main Content

        JPanel customisationPanel = new JPanel();
        customisationPanel.setLayout(null);
        customisationPanel.setPreferredSize(new Dimension(600, 720));

        add(customisationPanel, BorderLayout.CENTER);

        // Track Customisation
        customisationPanel.add(createLabel("Number of Lanes:", 75, 50));

        laneSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 5, 1));
        laneSpinner.setBounds(375, 50, 100, 25);
        customisationPanel.add(laneSpinner);

        customisationPanel.add(createLabel("Track Length:", 75, 125));

        lengthSpinner = new JSpinner(new SpinnerNumberModel(10, 10, 30, 5));
        lengthSpinner.setBounds(375, 125, 100, 25);
        customisationPanel.add(lengthSpinner);

        // Track Shape
        customisationPanel.add(createLabel("Track Shape:", 75, 200));

        String[] shapes = {"Straight", "Oval", "Star"};
        shapeSelector = new JComboBox<>(shapes);
        shapeSelector.setBounds(350, 200, 150, 25);
        customisationPanel.add(shapeSelector);

        // Weather Conditions
        customisationPanel.add(createLabel("Weather:", 75, 275));

        String[] weatherOptions = {"Clear", "Muddy", "Icy"};
        weatherSelector = new JComboBox<>(weatherOptions);
        weatherSelector.setBounds(350, 275, 150, 25);
        customisationPanel.add(weatherSelector);

        // Apply Button
        applyButton = new JButton("Apply Track Settings");
        applyButton.setBounds(200, 375, 200, 30);
        applyButton.setFont(new Font("Poppins", Font.BOLD, 13));
        applyButton.setFocusable(false);
        customisationPanel.add(applyButton);
    }

    // Create JLabels
    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Poppins", Font.PLAIN, 20));
        Dimension size = label.getPreferredSize();
        label.setBounds(x, y, size.width, size.height);

        return label;
    }

    // Getters
    public int getLaneCount() {
        return (int) laneSpinner.getValue();
    }

    public int getTrackLength() {
        return (int) lengthSpinner.getValue();
    }

    public String getSelectedShape() {
        return (String) shapeSelector.getSelectedItem();
    }

    public String getSelectedWeather() {
        return (String) weatherSelector.getSelectedItem();
    }

    public JButton getApplyButton() {
        return applyButton;
    }
}
