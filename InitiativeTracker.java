import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.Integer;

public class InitiativeTracker implements Serializable {
    private ArrayList<Creature> creatures;
    private ArrayList<Creature> initiativeOrder;
    private boolean inCombat = false;
    private Integer currentInitiative;
    private Scanner encounterScanner = null;
    private String commandOptions = """
        summary - prints a concise summary of characters in combat, with their indicies, in initiative order
        end turn - goes on to the next turn
        close - closes the whole program
        roll <dice formula> - rolls and prints the result of <dice formula> (ie, 3d6 rolls 3 6-sided dice and adds the results)
        damage <index> <amt> - damages the creature at index <index> for amount <amount>
        heal <index> <amt> - heals the creature at index <index> for amount <amount>
        take note <index> - adds a note to the creature at index <index> (no index means the creature at the current initative)
        stats <index> - prints stat block of creature <index>
        """;

    /** 
     * Constructor for InitiativeTracker given an array of Creatures
     * @param creatureList an array of Creatures to be added to the InitiativeTracker
     */
    public InitiativeTracker(Creature[] creatureList){
        ArrayList<Creature> creatures = new ArrayList<>();
        for (Creature c: creatureList){
            creatures.add(c);
        }
        this.creatures = creatures;
    }

    /** 
     * Default constructor for InitiativeTracker
     */
    public InitiativeTracker(){
        this.creatures=new ArrayList<Creature>();
    }

    /** 
     * Constructor for InitiativeTracker given an ArrayList of Creatures
     * @param creatures an ArrayList of Creatures to be added to the InitiativeTracker
     */
    public InitiativeTracker(ArrayList<Creature> creatures){
        this.creatures = creatures;
    }

    public InitiativeTracker(Scanner encounterScanner){
        this.encounterScanner = encounterScanner;
        this.creatures=new ArrayList<Creature>();
    }

    // public InitiativeTracker(String fileName){
    //     String content = Files.readString(Paths.get("Encounters/"+fileName+".txt"));
    //     this.inCombat = content.startsWith("active");
    //     String[] lines = content.split("\n");
    //     String section = "";
    //     ArrayList<Creature> creatures;
    //     for (int i = 0; i < lines.length; i++){
    //         if (lines[i].equals("MONSTERS: ") || lines[i].equals("PLAYERS: ") || lines[i].equals("INITIATIVE ORDER: ")){
    //             section = lines[i];
    //         } else if (lines[i].equals("")){
    //             section = "";
    //         } else if (section.equals("MONSTERS: ") && !lines[i].isEmpty()){
    //             Monster monster = new Monster(lines[i]);
    //             creatures.add(monster);
    //         } else if (section.equals("PLAYERS: ")){
    //             Player player = new Player(lines[i]);
    //             creatures.add(player);
    //         }
    //     }
    //     this.creatures = creatures;
    //     this.currentInitiative = content.substring(content.indexOf("CURRENT INITIATIVE: "+20)).trim();
    // }

    // public void saveEncounter(String fileName){
    //     try {
    //         FileWriter encounterWriter = new FileWriter(new File("Encounters/"+fileName+".txt"));
    //         if (this.inCombat){
    //             encounterWriter.write("active \n");
    //             for (Creature c:this.initiativeOrder){
    //                 if (c.getType() == CreatureType.MONSTER){
    //                     Monster m = (Monster) c;
    //                     encounterWriter.write("MONSTER \t"+m.getHP()+""+m.getName());
    //                     Monster.saveMonster(m);
    //                 } else if (c.getType() == CreatureType.UNIT){

    //                 }
    //             }
    //         } else {
    //             encounterWriter.write("inactive \n");
    //         }
    //         encounterWriter.close();
    //     } catch (IOException e) {
    //         throw new RuntimeException("Something went wrong when saving this encounter. There was an IOException with message"+e.getMessage());
    //     }
    // }

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
     * Method to parse and execute a command input by the user
     * @param input the command input by the user
     */
    public void doAction(String input, Scanner inputScanner){
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
                String note = inputScanner.nextLine();
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

    public ArrayList<Creature> getCreatures(){
        return this.creatures;
    }
    
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
    private void rollInitiatives(Scanner initiativeScanner){
        Hashtable<Integer, ArrayList<Creature>> initiativeTable = new Hashtable<>();
        this.addLairs();
        Integer top = 20; // the 'top of the initiative order'
        Integer bottom = 0; // the 'bottom of the initiative order'
        for (Creature creature: this.creatures){
            Integer initiative = (int) creature.rollInitiative(initiativeScanner);
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

    /** 
     * Takes a turn for the current creature in the initiative order
     * @param turnScanner a Scanner to read user input during the turn
     */
    private void takeTurn(Scanner turnScanner){
        String command = turnScanner.nextLine();
        this.doAction(command, turnScanner);
    }

    /**
     * Starts a round of the game, taking turns for each creature in initiative order
     */
    public void rollInitiative(){
        if (this.encounterScanner == null){
            this.encounterScanner = new Scanner(System.in);
        }
        this.rollInitiatives(this.encounterScanner);
        this.inCombat = true;
        this.currentInitiative = 0;
        System.out.println(this.initiativeOrder.get(this.currentInitiative).turnPrompt());
        while (inCombat) {
            this.takeTurn(this.encounterScanner);
        }
        this.encounterScanner.close();
    }

    public void saveEncounter(String fileName){
        try {
            File encounterFile = new File("Encounters/"+fileName+".txt");
            BufferedWriter encounterWriter = new BufferedWriter(new FileWriter(encounterFile));
            if (!this.inCombat){
                encounterWriter.write("INACTIVE");
            }
            for (int i = 0; i<this.initiativeOrder.size(); i++){
                encounterWriter.write((this.initiativeOrder.get(this.currentInitiative+i % this.initiativeOrder.size()).saveInfo()));
            }
            encounterWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong and the encounter could not be saved");
        }
    }

    public InitiativeTracker loadEncounter(String fileName){
        try {
            File encounterFile = new File("Encounters/"+fileName+".txt");
            Scanner encounterScanner = new Scanner(encounterFile);
            InitiativeTracker encounter = new InitiativeTracker();
            ArrayList<Creature> storageLoc = encounter.initiativeOrder;
            if (encounterScanner.nextLine().startsWith("INACTIVE")){
                storageLoc = encounter.creatures;
            } 
            while (encounterScanner.hasNextLine()) {
                String creatureLine = encounterScanner.nextLine();
                String[] pieces = creatureLine.split("\t");
                if (creatureLine.startsWith("MONSTER")){
                    storageLoc.add(Monster.getMonster(pieces[1].trim(), Integer.getInteger(pieces[2].trim())));
                } else if (creatureLine.startsWith("UNIT")){
                    storageLoc.add(MonsterGroup.getMonsterGroup(pieces[1].trim(), Integer.getInteger(pieces[2].trim()), Integer.getInteger(pieces[3].trim())));
                } else if (creatureLine.startsWith("PLACEHOLDER")){
                    storageLoc.add(new Placeholder(Integer.getInteger(pieces[1].trim()), pieces[2].trim()));
                } else if (creatureLine.startsWith("PLAYER")){
                    storageLoc.add(new Player(pieces[1].trim(), pieces[2].trim()));
                }
            }
            encounterScanner.close();
            return encounter;
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong and the encounter could not be loaded");
        }
    }
}
