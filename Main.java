public class Main {
    public static void main(String[] args) {
        Player moth = new Player("Moth", "Elizabeth");
        Player emia = new Player("Emia", "Celina");
        Monster dragon1 = new Monster("ancient_green_dragon");
        Monster dragon2 = new Monster("ancient_green_dragon");
        Creature[] creatures = {moth, emia, dragon1, dragon2};
        InitiativeTracker myEncounter = new InitiativeTracker(creatures);
        myEncounter.rollInitiative();
    }
}
