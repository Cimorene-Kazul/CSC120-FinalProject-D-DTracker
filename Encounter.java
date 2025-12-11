import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.Integer;

public class Encounter implements Serializable {
    private ArrayList<Creature> creatures;
    private ArrayList<Creature> initiativeOrder = new ArrayList<>();
    private boolean inCombat = false;
    private Integer currentInitiative = 0;
    private Scanner encounterScanner = null;
    private String commandOptions = """
summary - This command prints a concise summary of characters in combat, with their indicies, in initiative order. 
end turn - This command makes the encounter go on to the next turn.
close - This command quits out of the whole program. 
roll <dice formula> - This command rolls and prints the result of <dice formula>, which is formatted in the standard format for writing dice rolls.
damage <index> <amt> - This command makes the creature at index <index> take <amount> damage. 
heal <index> <amt> - This command heals the creature at index <index> by <amount>. 
take note <index> - This command prompts the user for a note and then adds that note to the notes for the creature at index <index>.  If there is no index specified, the creature that gains the note is the creature whose turn it currently is.
stats <index> - This command prints the stat block of the creature <index>. 
save - This command saves the encounter to a file, after prompting for a name for that file. """;

    /** 
     * Constructor for InitiativeTracker given an array of Creatures
     * @param creatureList an array of Creatures to be added to the InitiativeTracker
     */
    public Encounter(Creature[] creatureList){
        ArrayList<Creature> creatures = new ArrayList<>();
        for (Creature c: creatureList){
            creatures.add(c);
        }
        this.creatures = creatures;
    }

    /** 
     * Default constructor for InitiativeTracker
     */
    public Encounter(){
        this.creatures=new ArrayList<Creature>();
    }

    /** 
     * Constructor for InitiativeTracker given an ArrayList of Creatures
     * @param creatures an ArrayList of Creatures to be added to the InitiativeTracker
     */
    public Encounter(ArrayList<Creature> creatures){
        this.creatures = creatures;
    }

    /** 
     * Constructor for InitiativeTracker given a Scanner
     * @param encounterScanner a Scanner to read user input during the encounter
     */
    public Encounter(Scanner encounterScanner){
        this.encounterScanner = encounterScanner;
        this.creatures=new ArrayList<Creature>();
    }

    /** 
     * toString method for InitiativeTracker
     * @return A string representation of the InitiativeTracker
     */
    public String toString(){
        if (this.inCombat){
            String creatures = "";
            for (Creature c:this.initiativeOrder){
                creatures += " \n " + c ;
            }
            return "Initative Tracker for an Active Encounter \n Initiative Order:" + creatures;
        } else {
            String creatures = "";
            for (Creature c:this.creatures){
                creatures += " \n " + c.getName();
            }
            return "Initative Tracker containing: "+creatures;
        }
    }

    /** 
     * Method to check if the encounter is in progress
     * @return true if the encounter is in progress, false otherwise
     */
    public boolean inProgress(){
        return this.inCombat;
    }

    /** 
     * Method to parse and execute a command input by the user
     * @param input the command input by the user
     */
    public void doAction(String input){
        try{
            if (input.startsWith("heal")){
                String[] commandPieces = input.trim().split(" ");
                Integer index = Integer.parseInt(commandPieces[1]);
                Integer amt = Integer.parseInt(commandPieces[2]);
                System.out.println(this.initiativeOrder.get(index).heal(amt));
            } else if (input.startsWith("damage")){
                String[] commandPieces = input.trim().split(" ");
                Integer index = Integer.parseInt(commandPieces[1]);
                Integer amt = Integer.parseInt(commandPieces[2]);
                System.out.println(this.initiativeOrder.get(index).damage(amt));
            } else if (input.startsWith("take note")) {
                System.out.println("What do you want to note?");
                Integer index = this.currentInitiative;
                if (input.substring(9).trim() != ""){
                    index = Integer.parseInt(input.substring(10).trim());
                }
                String note = this.encounterScanner.nextLine();
                initiativeOrder.get(index).takeNote(note);
            } else if (input.startsWith("roll")){
                String die = input.substring(5);
                System.out.println(DiceFormula.parseFormula(die.trim()));
            } else if (input.startsWith("options")){
                System.out.println(commandOptions);
            } else if (input.startsWith("end turn")){
                this.currentInitiative += 1;
                if (this.currentInitiative >= this.initiativeOrder.size()){
                    this.currentInitiative = 0;
                }
                System.out.println(this.initiativeOrder.get(this.currentInitiative).turnPrompt());
            } else if (input.startsWith("stats")) {
                Integer index = Integer.parseInt(input.substring(5));
                System.out.println(((Monster) this.initiativeOrder.get(index)).getStats());
            }else if (input.startsWith("summary")){
                this.printSummary();
            } else if (input.startsWith("close")){
                this.inCombat = false;
            } else if (input.startsWith("save")){
                System.out.println("What is the name of the file you want to save this file in?");
                String fileName = this.encounterScanner.nextLine().trim();
                this.saveEncounter(fileName);
            }
        } catch (RuntimeException e){
            System.out.println("Something went wrong. Perhaps you formatted your command incorrectly or tried damage a creature that is not in the encounter. Please try again.");
        }
    }

    /**
     * Prints a summary of the current initiative order with indices
     */
    private void printSummary(){
        for (int i=0; i<this.initiativeOrder.size(); i++){
            String line = i+"\t"+initiativeOrder.get(i);
            if (i==this.currentInitiative){
                line += " <- current";
            }
            System.out.println(line);
        }
    }

    /**
     * Gets a summary of the current initiative order with indices
     * @return A string summary of the initiative order
     */
    public String getSummary(){
        String summary = "";
        for (int i=0; i<this.initiativeOrder.size(); i++){
            summary += i+"\t"+initiativeOrder.get(i)+"\n";
        }
        return summary;
    }
    /**
     * Adds a Creature to the InitiativeTracker 
     * @param c the Creature to be added
     */
    public void addCreature(Creature c){
        if (this.inCombat){
            throw new RuntimeException("Creatures cannot be added to an encounter in progress.");
        } else {
            this.creatures.add(c);
        }
    }

    /**
     * Gets the list of creatures in the encounter
     * @return An ArrayList of Creatures in the encounter
     */
    public ArrayList<Creature> getCreatures(){
        return this.creatures;
    }
    
    /**
     * Removes a Creature from the InitiativeTracker 
     * @param c the Creature to be removed
     */
    public void removeCreature(Creature c){
        if (this.inCombat){
            throw new RuntimeException("Creatures cannot be removed from an encounter in progress.");
        } else {
            if (this.creatures.contains(c)){
                this.creatures.remove(c);
            }else{
                throw new RuntimeException("This creature is not in the initiative tracker and thus cannot be removed.");
            }
        }
    }

    /** 
     * Adds lair actions for creatures that have them
     */
    public void addLairs(){
        for (Creature c: this.creatures){
            if (c.getLair()){
                Placeholder lair = new Placeholder(20, c.getName()+"'s lair actions"); 
                creatures.add(lair);
            }
        }   
    }

    /** 
     * Rolls initiatives for all creatures in the InitiativeTracker
     * @param initiativeScanner a Scanner to read user input for initiatives
     */
    private void rollInitiatives(){
        if (!this.inCombat){
            Hashtable<Integer, ArrayList<Creature>> initiativeTable = new Hashtable<>();
            this.addLairs();
            Integer top = 20; // the 'top of the initiative order'
            Integer bottom = 0; // the 'bottom of the initiative order'
            for (Creature creature: this.creatures){
                Integer initiative = (int) creature.rollInitiative(this.encounterScanner);
                if (initiative > top){
                    top = initiative;
                } else if (initiative < bottom){
                    bottom = initiative;
                }
                if (initiativeTable.containsKey(initiative)){
                    initiativeTable.get(initiative).add(creature);
                } else {
                    ArrayList<Creature> startingArray = new ArrayList<>();
                    startingArray.add(creature);
                    initiativeTable.put(initiative, startingArray);
                }
            }

            initiativeOrder = new ArrayList<>();

            for (Integer i=top; i>=bottom; i-=1){
                if (initiativeTable.containsKey(i)) {
                    for (Creature c:initiativeTable.get(i)){
                        this.initiativeOrder.add(c);
                    }
                }
            }
        }
    }

    /** 
     * Takes a turn for the current creature in the initiative order
     * @param turnScanner a Scanner to read user input during the turn
     */
    private void takeTurn(){
        String command = this.encounterScanner.nextLine();
        this.doAction(command);
    }

    /**
     * Starts a round of the game, taking turns for each creature in initiative order
     */
    public void rollInitiative(){
        if (this.encounterScanner == null){
            this.encounterScanner = new Scanner(System.in);
        }
        this.rollInitiatives();
        this.inCombat = true;
        this.currentInitiative = 0;
        System.out.println(this.initiativeOrder.get(this.currentInitiative).turnPrompt());
        while (inCombat) {
            this.takeTurn();
        }
        this.encounterScanner.close();
    }

    /**
     * Saves the current encounter to a file
     * @param fileName the name of the file to save the encounter in
     */
    public void saveEncounter(String fileName){
        try {
            File encounterFile = new File("Encounters/"+fileName+".txt");
            BufferedWriter encounterWriter = new BufferedWriter(new FileWriter(encounterFile));
            ArrayList<Creature> creatureSource = this.creatures;
            if (this.inCombat){
                creatureSource = this.initiativeOrder;
            } else {
                encounterWriter.write("INACTIVE");
            }
            encounterWriter.newLine();
            for (int i = 0; i<creatureSource.size(); i++){
                encounterWriter.write((creatureSource.get(this.currentInitiative+i % creatureSource.size()).saveInfo()));
                if (i!= creatureSource.size()-1){
                    encounterWriter.newLine();
                }
            }
            encounterWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong and the encounter could not be saved");
        }
    }

    /**
     * Loads an encounter from a file
     * @param fileName the name of the file to load the encounter from
     * @return The loaded Encounter
     */
    public static Encounter loadEncounter(String fileName){
        try {
            File encounterFile = new File("Encounters/"+fileName+".txt");
            Scanner encounterScanner = new Scanner(encounterFile);
            Encounter encounter = new Encounter();
            ArrayList<Creature> storageLoc = encounter.creatures;
            if (!encounterScanner.nextLine().startsWith("INACTIVE")){
                encounter.initiativeOrder = new ArrayList<>();
                storageLoc = encounter.initiativeOrder;
                encounter.inCombat = true;
            } 
            while (encounterScanner.hasNextLine()) {
                String creatureLine = encounterScanner.nextLine()+" ";
                if (creatureLine.startsWith("MONSTER")){
                    storageLoc.add(Monster.parseMonster(creatureLine));
                } else if (creatureLine.startsWith("UNIT")){
                    storageLoc.add(MonsterGroup.parseMonster(creatureLine));
                } else if (creatureLine.startsWith("PLACEHOLDER")){
                    storageLoc.add(Placeholder.parsePlaceholder(creatureLine));
                } else if (creatureLine.startsWith("PLAYER")){
                    storageLoc.add(Player.parsePlayer(creatureLine));
                }
            }
            if (!encounter.initiativeOrder.isEmpty()){
                encounter.creatures = encounter.initiativeOrder;
            }
            encounterScanner.close();
            return encounter;
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong and the encounter could not be loaded");
        }
    }
}
