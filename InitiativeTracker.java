import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.Serializable;
import java.lang.Integer;

public class InitiativeTracker implements Serializable {
    ArrayList<Creature> creatures;
    ArrayList<Creature> initiativeOrder;
    boolean inCombat = false;
    Integer currentInitiative;
    Scanner encounterScanner = null;
    String commandOptions = """
        summary - prints a concise summary of characters in combat, with their indicies, in initiative order
        end turn - goes on to the next turn
        close - closes the whole program
        roll <dice formula> - rolls and prints the result of <dice formula> (ie, 3d6 rolls 3 6-sided dice and adds the results)
        damage <index> <amt> - damages the creature at index <index> for amount <amount>
        heal <index> <amt> - heals the creature at index <index> for amount <amount>
        take note <index> - adds a note to the creature at index <index> (no index means the creature at the current initative)
        stats <index> - prints stat block of creature <index>
        action <action name>
        reaction <reaction name> <index>
        bonus action <bonus action name>
        legendary action <legendary action name> <index>
        legendary resistance <index>
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

    public InitiativeTracker(String fileName){
        String content = Files.readString(Paths.get(fileName));
        String[] lines = content.split("\n");
        String section = "";
        ArrayList<Creature> creatures;
        for (int i = 0; i < lines.length; i++){
            if (lines[i].equals("MONSTERS: ") || lines[i].equals("PLAYERS: ") || lines[i].equals("INITIATIVE ORDER: ")){
                section = lines[i];
            } else if (lines[i].equals("")){
                section = "";
            } else if (section.equals("MONSTERS: ") && !lines[i].isEmpty()){
                Monster monster = new Monster(lines[i]);
                creatures.add(monster);
            } else if (section.equals("PLAYERS: ")){
                Player player = new Player(lines[i]);
                creatures.add(player);
            }
        }
        this.creatures = creatures;
        this.currentInitiative = content.substring(content.indexOf("CURRENT INITIATIVE: "+20)).trim();
    }

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
                roll(die);
            } else if (input.startsWith("options")){
                System.out.println(commandOptions);
            } else if (input.startsWith("legendary resistance")){
                Integer index = Integer.parseInt(input.substring(20).trim());
                System.out.println(((Monster)this.initiativeOrder.get(index)).useLegendaryResistance());
            } else if (input.startsWith("end turn")){
                this.currentInitiative += 1;
                if (this.currentInitiative >= this.initiativeOrder.size()){
                    this.currentInitiative = 0;
                }
                System.out.println(this.initiativeOrder.get(this.currentInitiative).turnPrompt());
            } else if (input.startsWith("summary")){
                this.printSummary();
            } else if (input.startsWith("close")){
                this.inCombat = false;
            } 
            // else if (input.indexOf("save") != -1){
            //      String thingToMakeSave = input.substring(input.indexOf("save")+4);
            //      String saveType = thingToBeSaved.split(" ")[0];
            //      // do the saving throw method
            //  }
        } catch (RuntimeException e){
            System.out.println("Something went wrong. Perhaps you formatted your command incorrectly or tried damage a creature that is not in the encounter. Please try again.");
        }
    }

    /**
     * Prints a summary of the current initiative order with indices
     */
    private void printSummary(){
        for (int i=0; i<this.initiativeOrder.size(); i++){
            System.out.println(i+"\t"+initiativeOrder.get(i));
        }
    }

    public String getSummary(){
        String summary = "";
        for (int i=0; i<this.initiativeOrder.size(); i++){
            summary += i+"\t"+initiativeOrder.get(i)+"\n";
        }
        return summary;
    }

    public static double parseDie(String value){
         value = value.trim().split(" ")[0];
         if (!value.contains("d")){
             return Double.parseDouble(value);
         } else {
             int numberOfDice = Integer.parseInt(value.substring(0,value.indexOf("d")));
             int sizeOfDie = Integer.parseInt(value.substring(value.indexOf("d")+1));
             double result = 0;
             for (int i = 0; i< numberOfDice; i++){
                 result += (int)(Math.random()*sizeOfDie + 1);
             }
             return result;
         }
     }

    /** 
     * Rolls dice or does math based on a dice formula input by the user
     * @param input the dice formula input by the user
     */
    public static void roll (String input){
        double result = 0;
        input = input.trim();
        for (String plusChunk: input.split("\\+")){
            for (String minusChunk: plusChunk.split("\\-")){
                double value = 1;
                for (String timesChunk: minusChunk.split("\\*")){
                    for (String divideChunk: timesChunk.split("\\/")){
                        if (timesChunk.startsWith(divideChunk)){
                            value = value * InitiativeTracker.parseDie(timesChunk);
                        } else {
                            value = value /InitiativeTracker.parseDie(timesChunk);
                        }
                    }
                }
                if (plusChunk.startsWith(minusChunk)){
                    result += value;
                } else {
                    result -= value;
                }
            }
        }
        System.out.println(result);
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



    public static void main(String[] args) {
    }
}
