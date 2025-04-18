import javax.swing.*;

public class HorseRacingGUI extends JFrame {

    JButton button;
    JTextArea raceOutput;
    Race race;

    HorseRacingGUI() {
        setTitle("Horse Racing Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        TrackCustomisation tc = new TrackCustomisation();
        add(tc);

        setVisible(true);
    }

}
