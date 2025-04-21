import java.util.ArrayList;
import javax.swing.JPanel;

class Track extends JPanel {
    protected int trackLength;
    protected int lanes;
    protected ArrayList<Horse> horses;

    public Track(int trackLength, int lanes, ArrayList<Horse> horses) {
        this.trackLength = trackLength;
        this.lanes = lanes;
        this.horses = horses;
    }

    public void updateTrack() {
        repaint();
    }
}