import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

class Main {
    public static void main(String[] args) {
        PrintStream printStream = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        System.setOut(printStream);

        Race race = new Race(10);

        Horse horse1 = new Horse('A', "Alpha", 0.8);
        race.addHorse(horse1, 1);
        
        Horse horse2 = new Horse('B', "Bravo", 0.6);
        race.addHorse(horse2, 2);

        Horse horse3 = new Horse('T', "Thunder", 0.5);
        race.addHorse(horse3, 3);

        race.startRace();
    }
}