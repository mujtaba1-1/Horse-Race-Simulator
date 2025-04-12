import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

class Main {
    public static void main(String[] args) {
        PrintStream printStream = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        System.setOut(printStream);

        Race race = new Race(10);

        race.startRace();
    }
}