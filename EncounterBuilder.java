import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.io.ObjectInputStream;
// import java.io.ObjectOutputStream;

/**
 * This class is *not* a typical -Builder class. This is called an EncounterBuilder because within the setting of D&D, an encounter creation tool is called an 'Encounter Builder.'
 */
public class EncounterBuilder {
    InitiativeTracker encounter;
    boolean inProgress = true;
    ArrayList<String> creatureFiles = new ArrayList<>();
    Hashtable<String, Creature> creatures = new Hashtable<>();
    // boolean saved = true;
    String commands = """
            ENCOUNTER BUILDER COMMANDS
            add monster
            add monster with notes
            remove monster
            add player
            remove player
            add unit
            add unit with notes
            print encounter
            list avaliable monsters
            help
            close
            """;

    public void buildEncounter(){
        System.out.println("Welcome to JEB - the Java Encounter Builder! \n If you don't know what to do, the command 'help' will bring up a list of options.");
        Scanner encounterScanner = new Scanner(System.in);
        this.encounter = new InitiativeTracker(encounterScanner);
        while (inProgress) {
            System.out.println("What do you want to do?");
            String command = (encounterScanner.nextLine().trim()).toLowerCase();
            if (command.startsWith("add monster with notes")){
                this.addMonsterWithNotes(encounterScanner);
            } else if (command.startsWith("add monster")){
                this.addMonster(encounterScanner);
            } else if (command.startsWith("remove monster")){
                this.removeMonster(encounterScanner);
            } else if (command.startsWith("add player")) {
                this.addPlayer(encounterScanner);
            } else if (command.startsWith("remove player")) {
                this.removePlayer(encounterScanner);
            } else if (command.startsWith("add unit with notes")) {
                this.addUnitWithNotes(encounterScanner);
            } else if (command.startsWith("add unit")) {
                this.addUnit(encounterScanner);
            } else if (command.startsWith("print encounter")) {
                this.printEncounter();
            } else if (command.startsWith("list avaliable monsters")) {
                this.listMonsters();
            } else if (command.startsWith("run encounter")) {
                 this.runEncounter();
            } else if (command.startsWith("help")) {
                System.out.println(this.commands);
            } else if (command.startsWith("close")){
                this.closeBuilder(encounterScanner);
            } else {
                System.out.println(command+" is not a valid command. If you are confused, try 'help'.");
            }

            // } else if (command.startsWith("save encounter")){
            //     this.saveEncounter(encounterScanner);
            // } else if (command.startsWith("list saved encounters")){
            //     this.listEncounters();
            // } else if (command.startsWith("clear encounter")){
            //     this.clearEncounter(encounterScanner);
            // } else if (command.startsWith("load encounter")) {
            //     this.loadEncounter(encounterScanner);
        }
        encounterScanner.close();
    }

    // private void clearEncounter(Scanner input){
    //     if (!this.saved){
    //         System.out.println("Are you sure? The current encounter is unsaved.");
    //         String response = input.nextLine().toLowerCase().trim();
    //         if (response=="yes" || response=="y"){
    //             this.encounter = new InitiativeTracker(input);
    //         }
    //     } else {
    //         this.encounter = new InitiativeTracker(input);
    //     }
    // }

    private void closeBuilder(Scanner input){
        // if (!this.saved){
        //     System.out.println("Are you sure? The current encounter is unsaved.");
        //     String response = input.nextLine().toLowerCase().trim();
        //     if (response=="yes" || response=="y"){
        //         this.inProgress = false;
        //     }
        // } else {
        this.inProgress = false;
        // }
    }

    // private void saveEncounter(Scanner input){
    //     this.saved = true;
    //     System.out.println("What should this encounter be called?");
    //     String encounterName = input.nextLine().trim();
    //     try {
    //         FileOutputStream encounterFile = new FileOutputStream("Encounters/"+encounterName+".ser");
    //         ObjectOutputStream encounterWriter = new ObjectOutputStream(encounterFile);
    //         encounterWriter.writeObject(this.encounter.getCreatures());
    //         encounterWriter.close();
    //         encounterFile.close();
    //         System.out.println("Your encounter "+encounterName+" has been saved.");
    //     } catch (IOException e) {
    //         System.out.println("An error occured with the files in question.");
    //     }
    // }

    // private void loadEncounter(Scanner input){
    //     boolean loading = false;
    //     if (!this.saved){
    //         System.out.println("Are you sure? The current encounter is unsaved.");
    //         String response = input.nextLine().toLowerCase().trim();
    //         if (response=="yes" || response=="y"){
    //             loading = true;
    //         }
    //     } else{
    //         loading = true;
    //     }
    //     if (loading){
    //         System.out.println("What encounter do you want to load?");
    //         String encounterName = input.nextLine().trim();
    //         try {
    //             FileInputStream encounterFile = new FileInputStream("Encounters/"+encounterName+".ser");
    //             ObjectInputStream encounterReader = new ObjectInputStream(encounterFile);
    //             ArrayList<Creature> creatures = (ArrayList<Creature>) encounterReader.readObject();
    //             this.encounter = new InitiativeTracker(creatures);
    //             encounterReader.close();
    //             encounterFile.close();
    //             System.out.println("You have loaded in the saved encounter "+encounterName);
    //         } catch (IOException e) {
    //             System.out.println("The file in question has problems and may not exist.");
    //         } catch (ClassNotFoundException e){
    //             System.out.println("The file in question does not contain appropriate contents.");
    //         }
    //     }
    // }

    private void addMonster(Scanner input){
        // this.saved = false;
        try{
            System.out.println("What monster do you want to add?");
            String monsterFile = toFileName(input.nextLine().trim());
            Monster monsterToAdd = new Monster(monsterFile);
            this.creatures.put(monsterToAdd.getName(), monsterToAdd);
            this.encounter.addCreature(monsterToAdd);
            System.out.println(monsterToAdd.getName() + " has been added to your encounter.");
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    private void addMonsterWithNotes(Scanner input){
        // this.saved = false;
        try{
            System.out.println("What monster do you want to add?");
            String monsterFile = toFileName(input.nextLine().trim());
            System.out.println("What notes do you want to add about this monster's location?");
            String locationNotes = input.nextLine().trim();
            Monster monsterToAdd = new Monster(monsterFile, locationNotes);
            this.creatures.put(monsterToAdd.getName(), monsterToAdd);
            this.encounter.addCreature(monsterToAdd);
            System.out.println(monsterToAdd.getName() + " has been added to your encounter.");
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    private void addUnit(Scanner input){
        // this.saved = false;
        try{
            System.out.println("What monster do you want to add?");
            String monsterFile = toFileName(input.nextLine().trim());
            System.out.println("How many monsters are in the unit?");
            int size = input.nextInt();
            Monster monsterToAdd = new MonsterGroup(monsterFile, size);
            this.creatures.put(monsterToAdd.getName(), monsterToAdd);
            this.encounter.addCreature(monsterToAdd);
            System.out.println("The unit "+monsterToAdd.getName() + " has been added to your encounter.");
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    private void addUnitWithNotes(Scanner input){
        // this.saved = false;
        try{
            System.out.println("What monster do you want to add?");
            String monsterFile = toFileName(input.nextLine().trim());
            System.out.println("How many monsters are in the unit?");
            int size = input.nextInt();
            System.out.println("What notes do you want to add about this monster's location?");
            String locationNotes = input.nextLine().trim();
            Monster monsterToAdd = new MonsterGroup(monsterFile, size, locationNotes);
            this.creatures.put(monsterToAdd.getName(), monsterToAdd);
            this.encounter.addCreature(monsterToAdd);
            System.out.println("The unit "+monsterToAdd.getName() + " has been added to your encounter.");
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    private void removeMonster(Scanner input){
        // this.saved = false;
        System.out.println("What monster do you want to remove?");
        String monsterName = input.nextLine().trim();
        if (this.creatures.containsKey(monsterName)){
            try {
                this.encounter.removeCreature(this.creatures.get(monsterName));
                System.out.println(monsterName+" has been removed from your encounter.");
            } catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println(monsterName + " is not in this encounter and thus could not be removed.");
        }
    }

    private void addPlayer(Scanner input){
        // this.saved = false;
        System.out.println("What is the player's name?");
        String playerName = input.nextLine().trim();
        System.out.println("What is "+playerName+"'s player character named?");
        String PCName = input.nextLine().trim();
        Player PC = new Player(PCName, playerName);
        this.encounter.addCreature(PC);
        this.creatures.put(PCName, PC);
        this.creatures.put(playerName, PC);
        System.out.println(PCName+" has been added to your encounter.");
    }

    private void removePlayer(Scanner input){
        // this.saved = false;
        System.out.println("Which player do you want to remove?");
        String playerName = input.nextLine().trim();
        if (this.creatures.containsKey(playerName)){
            try {
                this.encounter.removeCreature(this.creatures.get(playerName));
                System.out.println(playerName+" has been removed from your encounter.");
            } catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println(playerName + " is not in this encounter and thus could not be removed.");
        }
    }

    private void listMonsters(){
        System.out.println("AVAILIABLE MONSTERS TO USE IN THIS ENCOUNTER");
        File[] listOfMonsterFiles = new File("MonsterFiles").listFiles();
        for (File monsterFile: listOfMonsterFiles){
            System.out.println(toMonsterName(monsterFile.getName()));
        }
    }

    private void runEncounter(){
        inProgress = false;
        this.encounter.rollInitiative();
    }

    // private void listEncounters(){
    //     System.out.println("AVAILIABLE ENCOUNTERS TO LOAD");
    //     File[] listOfEncounterFiles = new File("Encounters").listFiles();
    //     for (File EncounterFile: listOfEncounterFiles){
    //         System.out.println(toEncounterName(EncounterFile.getName()));
    //     }
    // }

    private void printEncounter(){
        System.out.println(this.encounter);
    }

    private static String toFileName(String monsterName){
        return (monsterName.replaceAll(" ", "_")).toLowerCase();
    }

    private static String toMonsterName(String fileName){
        return (fileName.replaceAll("_", " ").substring(0, fileName.length()-4));
    }

    // private static String toEncounterName(String fileName){
    //     return fileName.substring(0,fileName.length()-4);
    // }

    public static void main(String[] args) {
        EncounterBuilder myBuilder = new EncounterBuilder();
        myBuilder.buildEncounter();
    }
}