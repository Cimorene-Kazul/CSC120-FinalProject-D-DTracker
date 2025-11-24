import java.util.Hashtable;
import java.util.Scanner;

public class EncounterBuilder {
    InitiativeTracker encounter;
    boolean inProgress = true;
    Hashtable<String, Creature> entities;
    String commands = """
            ENCOUNTER BUILDER COMMANDS
            add monster
            remove monster
            add player
            remove player
            print encounter
            list avaliable
            run encounter
            save encounter
            help
            """;

    public void buildEncounter(){
        System.out.println("Welcome to JEB - the Java Encounter Builder! \n If you don't know what to do, the command 'help' will bring up a list of options.");
        Scanner encounterScanner = new Scanner(System.in);
        encounter = new InitiativeTracker(encounterScanner);
        while (inProgress) {
            System.out.println("What do you want to do?");
            String command = encounterScanner.nextLine();
            if (command == "run encounter"){
                inProgress = false;
                encounter.rollInitiative();
            } if (command == "save encounter") {
                
            }else {
                System.out.println("This is not a valid command.");
            }
        }
    }

    private static String toFileName(String monsterName){
        return (monsterName.replace(" ", "_")).toLowerCase();
    }

}
