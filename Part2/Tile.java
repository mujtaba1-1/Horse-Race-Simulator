import java.awt.Color;
import java.util.Random;
import javax.swing.JLabel;

public class Tile extends JLabel {

    public int x_pos;
    public int y_pos;
    public boolean horseLane;

    public Tile(int x, int y, int size, boolean horseLane) {
        x_pos = x;
        y_pos = y;
        this.horseLane = horseLane;

        setBounds(x_pos, y_pos, size, size);
        setOpaque(true);
        setVerticalAlignment(CENTER);
        setHorizontalAlignment(CENTER);
        
        Random rand = new Random();
        setBackground(horseLane ? Color.RED : Color.BLACK);
    }

    public void setSymbol(char symbol) {
        setText(symbol + "");
    }

    public void clearSymbol() {
        setText("");
    }

    public int getX() {
        return x_pos;
    }

    public int getY() {
        return y_pos;
    }

    public boolean isHorseLane() {
        return horseLane;
    }
}