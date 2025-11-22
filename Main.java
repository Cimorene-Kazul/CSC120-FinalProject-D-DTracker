public class Main {
    public static void main(String[] args) {
        Player moth = new Player("Moth", "Elizabeth");
        Player emia = new Player("Emia", "Celina");
        Monster dragon = new Monster("ancient_green_dragon");
        Creature[] creatures = {moth, emia, dragon};
        InitiativeTracker myEncounter = new InitiativeTracker(creatures);
        myEncounter.rollInitiative();
    }
}
