import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Player moth = new Player("Moth", "Elizabeth");
        Player emia = new Player("Emia", "Celina");
        Monster luna = new Monster("Ilumetrix Luna", 300, 21, +5, "<substitute for Luna's stats>", true);
        Creature[] creatures = {moth, emia, luna};
        InitativeTracker myEncounter = new InitativeTracker(creatures);
        myEncounter.rollInitative();
    }
}
