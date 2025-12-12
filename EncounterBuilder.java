import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.File;
/**
 * This class is *not* a typical -Builder class. This is called an EncounterBuilder because within the setting of D&D, an encounter creation tool is called an 'Encounter Builder.'
 */
public class EncounterBuilder {
    Encounter encounter;
    boolean inProgress = true;
    ArrayList<String> creatureFiles = new ArrayList<>();
    Hashtable<String, ArrayList<Creature>> creatures = new Hashtable<>();
    boolean saved = true;
    String commands = """
ENCOUNTER BUILDER COMMANDS
add player - This command adds a player-controled character to the encounter. The player character is indicated by character name and player name, and during combat will prompt for the player-rolled initiative rather than rolling automatically.
add monster - This command adds a single entity defined by a stat block to the encounter. The stat block will be in the MonsterFiles folder, and must have a particular format, which will be described later. This is not a default format typically fetched off the web, but is easiest to code and reasonably easy to turn a standard off-the-web stat block into.
add multiple monsters - This command adds a given number of monsters to the encounter, which is like repeating the add monster command a certain number of times.
add monster with note - This command does the same thing as add monster but also allows the user to add a note to make it easier to keep track of things in large encounters.
add placeholder - This command adds a placeholder at a particular initiative, for a particular event.
add unit - This command adds an entity composed of some number of entites defined by a stat block to the encounter. Units of this sort follow rules I created for my personal campaign, and are nonstandard.They have a size, indicating the number of entities composing the unit, and have HP equal to the base HP for the composing monster multiplied by the size. As they take damage, the size decreases. Unit size must be a positive integer.
add unit with note - This command does the same thing as add unit but also allows the user to add a note to make it easier to keep track of things in large encounters, the same way add monster with note does.
remove creature - This command removes an entity from the encounter. 
remove creatures - This command removes all entities with the same name from the encounter. 
run encounter - This command runs the encounter that the encounter builder is currently working on.
print encounter - This command prints a list of creatures in the encounter. This will include notes.
list avaliable monsters - This command lists the monster names for monsters with stat blocks avaliable to use. 
save encounter - This commander saves the encounter as an encounter file. Such files can be loaded later.
list saved encounters - This command lists the file names of encounters that are saved and thus can be loaded. 
load encounter - This command loads an encounter from a saved file. It will ask which encounter to load, the resulting input must be a file name that list saved encounters would print.
clear encounter - This command removes all entities from the current encounter, reseting it.
help - This command prints the list of commands that the encounter builder will accept.
close - This command closes the program """;

    /**
     * Constructor for an encounter, step by step without an existing file using user input
     */
    public void buildEncounter(){
        System.out.println("Welcome to JEB - the Java Encounter Builder! \n If you don't know what to do, the command 'help' will bring up a list of options.");
        Scanner encounterScanner = new Scanner(System.in);
        this.encounter = new Encounter(encounterScanner);
        while (inProgress) {
            System.out.println("What do you want to do?");
            String command = (encounterScanner.nextLine().trim()).toLowerCase();
            if (command.startsWith("add monster with note")) {
                this.addMonsterWithNotes(encounterScanner);
            } else if (command.startsWith("add multiple monsters")){
                this.addNMonsters(encounterScanner);
            } else if (command.startsWith("add monster")){
                this.addMonster(encounterScanner);
            } else if (command.startsWith("add placeholder")){
                this.addPlaceholder(encounterScanner);
            } else if (command.startsWith("remove creatures")){
                this.removeMonsters(encounterScanner);
            } else if (command.startsWith("remove creature")){
                this.removeMonster(encounterScanner);
            } else if (command.startsWith("add player")) {
                this.addPlayer(encounterScanner);
            } else if (command.startsWith("add unit with note")) {
                this.addUnitWithNotes(encounterScanner);
            } else if (command.startsWith("add unit")) {
                this.addUnit(encounterScanner);
            } else if (command.startsWith("list saved encounters") || command.startsWith("list encounters")){
                this.listEncounters();
            } else if (command.startsWith("print encounter")) {
                this.printEncounter();
            } else if (command.startsWith("list avaliable monsters") || command.startsWith("list monsters")) {
                this.listMonsters();
            } else if (command.startsWith("run encounter") || command.startsWith("start encounter") || command.startsWith("roll initiative")) {
                 this.runEncounter();
            } else if (command.startsWith("help")) {
                System.out.println(this.commands);
            } else if (command.startsWith("close")){
                this.closeBuilder(encounterScanner);
            } else if (command.startsWith("save encounter")){
                this.saveEncounter(encounterScanner);
            } else if (command.startsWith("clear")){
                this.clearEncounter(encounterScanner);
            } else if (command.startsWith("load encounter")) {
                this.loadEncounter(encounterScanner);
            } else{
                System.out.println(command+" is not a valid command. If you are confused, try 'help'.");
            }
        }
        encounterScanner.close();
    }

    /**
     * Clears the current encounter
     * @param input The Scanner to get user input from
     */
    private void clearEncounter(Scanner input){
        if (!this.saved){
            System.out.println("Are you sure? The current encounter is unsaved.");
            String response = input.nextLine().toLowerCase().trim();
            if (response.startsWith("y")){
                this.encounter = new Encounter(input);
            }
        } else {
            this.encounter = new Encounter(input);
        }
    }

    /**
     * Closes the encounter builder
     * @param input
     */
    private void closeBuilder(Scanner input){
        if (!this.saved){
            System.out.println("Are you sure? The current encounter is unsaved.");
            String response = input.nextLine().toLowerCase().trim();
            if (response.startsWith("y")){
                this.inProgress = false;
            }
        } else {
        this.inProgress = false;
        }
    }

    /**
     * Saves the current encounter
     * @param input The Scanner to get user input from
     */
    private void saveEncounter(Scanner input){
        this.saved = true;
        System.out.println("What file should this encounter be saved in?");
        String encounterName = input.nextLine().trim();
        try {
            this.encounter.saveEncounter(encounterName);
        } catch (RuntimeException e) {
            System.out.println("An error occured with the files in question.");
        }
    }

    /**
     * Loads an encounter from a file
     * @param input The Scanner to get user input from
     */
    private void loadEncounter(Scanner input){
        boolean loading = false;
        if (!this.saved){
            System.out.println("Are you sure? The current encounter is unsaved.");
            String response = input.nextLine().toLowerCase().trim();
            if (response.startsWith("y")){
                loading = true;
            }
        } else{
            loading = true;
        }
        if (loading){
            System.out.println("What encounter do you want to load?");
            String encounterName = input.nextLine().trim();
            try {
                this.encounter = Encounter.loadEncounter(encounterName);
                if (this.encounter.inProgress()){
                    System.out.println("This encounter is active. All attempts to modify this encounter will not successfully change it.");
                }
            } catch (RuntimeException e) {
                System.out.println("The file in question has problems and may not exist.");
            }
        }
    }

    /**
     * Adds a monster to the encounter
     * @param input The Scanner to get user input from
     */
    private void addMonster(Scanner input){
        this.saved = false;
        try{
            System.out.println("What monster do you want to add?");
            String monsterFile = toFileName(input.nextLine().trim());
            Monster monsterToAdd = new Monster(monsterFile);
            this.addToList(monsterToAdd);
            this.encounter.addCreature(monsterToAdd);
            System.out.println(monsterToAdd.getName() + " has been added to your encounter.");
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a placeholder to the encounter
     * @param input The Scanner to get user input from
     */
    private void addPlaceholder(Scanner input){
        this.saved = false;
        try {
            System.out.println("What initiative do you want the placeholder for?");
            int initiative = input.nextInt();
            input.nextLine();
            System.out.println("What event happens at initiative count "+initiative+"?");
            String event = input.nextLine();
            encounter.addCreature(new Placeholder(initiative, event));
            System.out.println("A placeholder at "+initiative+" for "+event+" has been added to your encounter.");
        } catch (RuntimeException e) {
            System.out.println("Initiatives must be integers.");
            input.nextLine();
        }
    }

    /**
     * Adds multiple monsters to the encounter
     * @param input The Scanner to get user input from
     */
    private void addNMonsters(Scanner input){
        this.saved = false;
        try{
            System.out.println("What monster do you want to add?");
            String monsterFile = toFileName(input.nextLine().trim());
            System.out.println("How many copies of this monster do you want to add?");
            Integer n = 0;
            try {
                n = Integer.parseInt(input.nextLine());
            } catch (RuntimeException e) {
                throw new RuntimeException("You can only add a nonnegative integer number of monsters.");
            }
            for (int i=0; i<n; i++){
                Monster monsterToAdd = new Monster(monsterFile);
                this.addToList(monsterToAdd);
                this.encounter.addCreature(monsterToAdd);
            }
            System.out.println(n+" copies of the specified monster have been added to your encounter.");
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a monster with notes to the encounter
     * @param input The Scanner to get user input from
     */
    private void addMonsterWithNotes(Scanner input){
        this.saved = false;
        try{
            System.out.println("What monster do you want to add?");
            String monsterFile = toFileName(input.nextLine().trim());
            System.out.println("What note do you want to add to this monster?");
            String monsterNote = input.nextLine().trim();
            Monster monsterToAdd = new Monster(monsterFile, monsterNote);
            this.addToList(monsterToAdd);
            this.encounter.addCreature(monsterToAdd);
            System.out.println(monsterToAdd.getName() + " has been added to your encounter.");
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a unit (group of monsters of the same type) with notes to the encounter
     * @param input The Scanner to get user input from
     */
    private void addUnitWithNotes(Scanner input){
        this.saved = false;
        try{
            System.out.println("What monster makes up the unit you want to add?");
            String monsterFile = toFileName(input.nextLine().trim());
            System.out.println("How many monsters are in the unit?");
            int size = input.nextInt();
            input.nextLine();
            System.out.println("What note do you want to add to this unit?");
            String monsterNote = input.nextLine().trim();
            Monster monsterToAdd =  new MonsterGroup(monsterFile, size, monsterNote);
            this.addToList(monsterToAdd);
            this.encounter.addCreature(monsterToAdd);
            System.out.println(monsterToAdd.getName() + " has been added to your encounter.");
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a unit (group of monsters of the same type) to the encounter
     * @param input The Scanner to get user input from
     */
    private void addUnit(Scanner input){
        this.saved = false;
        try{
            System.out.println("What monster makes up the unit you want to add?");
            String monsterFile = toFileName(input.nextLine().trim());
            System.out.println("How many monsters are in the unit?");
            int size = input.nextInt();
            input.nextLine();
            Monster monsterToAdd = new MonsterGroup(monsterFile, size);
            this.addToList(monsterToAdd);
            this.encounter.addCreature(monsterToAdd);
            System.out.println("The unit "+monsterToAdd.getName() + " has been added to your encounter.");
        } catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes the first instance of a monster from the encounter list of monsters
     * @param input The Scanner to get user input from
     */
    private void removeMonster(Scanner input){
        this.saved = false;
        System.out.println("What creature do you want to remove?");
        String monsterName = input.nextLine().trim();
        if (this.creatures.containsKey(monsterName)){
            try {
                this.removeCreature(monsterName);
                System.out.println(monsterName+" has been removed from your encounter.");
            } catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println(monsterName + " is not in this encounter and thus could not be removed.");
        }
    }

    /**
     * Removes all monsters of the same name in the encounter <== NOT SURE WHAT THE DIFFERENCE IS BETWEEN THIS AND PREVIOUS
     * @param input
     */
    private void removeMonsters(Scanner input){
        this.saved = false;
        System.out.println("What entity do you want to remove all copies of?");
        String monsterName = input.nextLine().trim();
        if (this.creatures.containsKey(monsterName)){
            try {
                for (int i=0; i<this.creatures.get(monsterName).size(); i++){
                    this.removeCreature(monsterName);
                }
            } finally {
                System.out.println("All copies of the entity"+monsterName+"have been removed from your encounter.");
            }
        } else {
            System.out.println(monsterName + " is not in this encounter and thus could not be removed.");
        }
    }

    /**
     * Adds a new creature to the encounter
     * @param c The creature
     */
    private void addToList(Creature c){
        if (!this.creatures.containsKey(c.getName())){
            this.creatures.put(c.getName(), new ArrayList<Creature>());
        } 
        this.creatures.get(c.getName()).add(c);
    }

    /**
     * Adds a new creature to the encounter
     * @param c The creature
     * @param name
     */
    private void addToList(Creature c, String name){
        if (!this.creatures.containsKey(name)){
            this.creatures.put(name, new ArrayList<Creature>());
        } 
        this.creatures.get(name).add(c);
    }

    /**
     * Removes a creature from the encounter
     * @param name
     */
    private void removeCreature(String name){
        String failureMessage = "";
        for (Creature c: this.creatures.get(name)){
            try {
                this.encounter.removeCreature(c);
                return;  
            } catch (RuntimeException e) {
                failureMessage = e.getMessage();
            }
        }
        throw new RuntimeException(failureMessage);
    }

    /** 
     * Adds a player to the encounter
     * @input Scanner to get user input from
     */
    private void addPlayer(Scanner input){
        this.saved = false;
        System.out.println("What is the player's name?");
        String playerName = input.nextLine().trim();
        System.out.println("What is "+playerName+"'s player character named?");
        String PCName = input.nextLine().trim();
        Player PC = new Player(PCName, playerName);
        this.encounter.addCreature(PC);
        this.addToList(PC, playerName);
        this.addToList(PC, PCName);
        System.out.println(PCName+" has been added to your encounter.");
    }

    /**
     * Prints a list of monsters in the encounter
     */
    private void listMonsters(){
        System.out.println("AVAILIABLE MONSTERS TO USE IN THIS ENCOUNTER");
        File[] listOfMonsterFiles = new File("MonsterFiles").listFiles();
        for (File monsterFile: listOfMonsterFiles){
            System.out.println(toMonsterName(monsterFile.getName()));
        }
    }

    /**
     * Begins an encounter by rolling initiative
     */
    private void runEncounter(){
        inProgress = false;
        this.encounter.rollInitiative();
    }

    /**
     * Prints available encounter files
     */
    private void listEncounters(){
        System.out.println("AVAILIABLE ENCOUNTERS TO LOAD");
        File[] listOfEncounterFiles = new File("Encounters").listFiles();
        for (File EncounterFile: listOfEncounterFiles){
            System.out.println(EncounterFile.getName().substring(0, EncounterFile.getName().length()-4));
        }
    }

    /**
     * Prints the current encounter
     */
    private void printEncounter(){
        System.out.println(this.encounter);
    }

    /**
     * Replaces a String's spaces with underscores
     * @param monsterName
     * @return monsterName with underscores instead of spaces
     */
    private static String toFileName(String monsterName){
        return (monsterName.replaceAll(" ", "_")).toLowerCase();
    }

    /**
     * Replaces a String's underscores with spaces
     * @param fileName
     * @return fileName with spaces instead of underscores
     */
    private static String toMonsterName(String fileName){
        return (fileName.replaceAll("_", " ").substring(0, fileName.length()-4));
    }

    public static void main(String[] args) {
        EncounterBuilder myBuilder = new EncounterBuilder();
        myBuilder.buildEncounter();
    }
}