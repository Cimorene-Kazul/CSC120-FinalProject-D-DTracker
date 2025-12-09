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
            add monster - adds a single entity defined by a stat block to the encounter.
            add multiple monsters - adds a given number of monsters to the encounter. Good for a fight with 5 goblins or 10 guards, to avoid repeats.
            add monster with note - does the same thing as add monster but gives the option to add a note to make it easier to keep track of things in large encounters.
            remove monster - removes a monster or unit from the encounter
            remove monsters - removes all monsters or units with the same name from the encounter.
            add player - adds a player-controled character to the encounter
            remove player - removes a player-controled character from the encounter
            add unit - adds an entity composed of some number of entites defined by a stat block to the encounter. These entities might have something special added because of their nature as a composite or might just take damage together to ease running large encounters.
            add unit with note - does the same thing as add unit but gives the option to add a note to make it easier to keep track of things in large encounters.
            print encounter - prints a list of creatures in the encounter, with basic information.
            list avaliable monsters - lists the monsters by name with stat blocks avaliable to use.
            save encounter - saves the encounter as an encounter file.
            list saved encounters - lists encounter file names that are saved.
            load encounter - loads an encounter from a saved file.
            clear encounter - removes all entities from the current encounter.
            help - prints this list of commands.
            close - quits this builder.
            """;

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
            } else if (command.startsWith("remove monsters")){
                this.removeMonsters(encounterScanner);
            } else if (command.startsWith("remove monster")){
                this.removeMonster(encounterScanner);
            } else if (command.startsWith("add player")) {
                this.addPlayer(encounterScanner);
            } else if (command.startsWith("remove player")) {
                this.removePlayer(encounterScanner);
            } else if (command.startsWith("add unit with note")) {
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
            } else if (command.startsWith("save encounter")){
                this.saveEncounter(encounterScanner);
            } else if (command.startsWith("list saved encounters")){
                this.listEncounters();
            } else if (command.startsWith("clear encounter")){
                this.clearEncounter(encounterScanner);
            } else if (command.startsWith("load encounter")) {
                this.loadEncounter(encounterScanner);
            } else{
                System.out.println(command+" is not a valid command. If you are confused, try 'help'.");
            }
        }
        encounterScanner.close();
    }

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

    private void removeMonster(Scanner input){
        this.saved = false;
        System.out.println("What monster do you want to remove?");
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

    private void removeMonsters(Scanner input){
        this.saved = false;
        System.out.println("What monster do you want to remove all copies of?");
        String monsterName = input.nextLine().trim();
        if (this.creatures.containsKey(monsterName)){
            try {
                for (int i=0; i<this.creatures.get(monsterName).size(); i++){
                    this.removeCreature(monsterName);
                }
            } finally {
                System.out.println("All copies of this monster have been removed from your encounter.");
            }
        } else {
            System.out.println(monsterName + " is not in this encounter and thus could not be removed.");
        }
    }

    private void addToList(Creature c){
        if (!this.creatures.containsKey(c.getName())){
            this.creatures.put(c.getName(), new ArrayList<Creature>());
        } 
        this.creatures.get(c.getName()).add(c);
    }

    private void addToList(Creature c, String name){
        if (!this.creatures.containsKey(name)){
            this.creatures.put(name, new ArrayList<Creature>());
        } 
        this.creatures.get(name).add(c);
    }

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

    private void addPlayer(Scanner input){
        this.saved = false;
        System.out.println("What is the player's name?");
        String playerName = input.nextLine().trim();
        System.out.println("What is "+playerName+"'s player character named?");
        String PCName = input.nextLine().trim();
        Player PC = new Player(PCName, playerName);
        this.encounter.addCreature(PC);
        this.addToList(PC, playerName);
        this.addToList(PC, playerName);
        System.out.println(PCName+" has been added to your encounter.");
    }

    private void removePlayer(Scanner input){
        this.saved = false;
        System.out.println("Which player do you want to remove?");
        String playerName = input.nextLine().trim();
        if (this.creatures.containsKey(playerName)){
            try {
                this.removeCreature(playerName);
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

    private void listEncounters(){
        System.out.println("AVAILIABLE ENCOUNTERS TO LOAD");
        File[] listOfEncounterFiles = new File("Encounters").listFiles();
        for (File EncounterFile: listOfEncounterFiles){
            System.out.println(EncounterFile.getName().substring(0, EncounterFile.getName().length()-4));
        }
    }

    private void printEncounter(){
        System.out.println(this.encounter);
    }

    private static String toFileName(String monsterName){
        return (monsterName.replaceAll(" ", "_")).toLowerCase();
    }

    private static String toMonsterName(String fileName){
        return (fileName.replaceAll("_", " ").substring(0, fileName.length()-4));
    }

    public static void main(String[] args) {
        EncounterBuilder myBuilder = new EncounterBuilder();
        myBuilder.buildEncounter();
    }
}