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
    boolean bonusActionUsed = false;
    boolean actionUsed = false;
    String commandOptions = """
        summary - prints a concise summary of characters in combat, with their indicies, in initiative order
        end turn - goes on to the next turn
        close - closes the whole program
        roll <dice formula> - rolls and prints the result of <dice formula> (ie, 3d6 rolls 3 6-sided dice and adds the results)
        damage <index> <amt> - damages the creature at index <index> for amount <amount>
        heal <index> <amt> - heals the creature at index <index> for amount <amount>
        take note <index> - adds a note to the creature at index <index> (no index means the creature at the current initative)
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
                Integer index = this.currentInitiative;
                if (input.substring(10).trim() != ""){
                    index = Integer.parseInt(input.substring(10).trim());
                }
                System.out.println("What do you want to note?");
                String note = inputScanner.nextLine();
                initiativeOrder.get(index).takeNote(note);
            } else if (input.startsWith("roll")){
                String die = input.substring(5);
                roll(die);
            } else if (input.startsWith("options")){
                System.out.println(commandOptions);
            } else if (input.startsWith("bonus action")){
                if (bonusActionUsed){
                    System.out.println("You have already used your bonus action this turn.");
                    return;
                }
                String bonusAction = input.substring(13);
                ((Monster)this.initiativeOrder.get(currentInitiative)).bonusAction(bonusAction);
                bonusActionUsed = true;
            } else if (input.startsWith("reaction")){
                String reaction = input.substring(9);
                Integer index = currentInitiative;
                for (int i = 0; i < reaction.length(); i++) {
                    if (Character.isDigit(reaction.charAt(i))) {
                        String num = reaction.substring(i);
                        reaction = reaction.substring(0, i-1);
                        index = Integer.parseInt(num.trim());
                    }
                }
                ((Monster)this.initiativeOrder.get(index)).reaction(reaction);
            } else if (input.startsWith("legendary action")){
                String legendaryAction = input.substring(17);
                Integer index = currentInitiative;
                for (int i = 0; i < legendaryAction.length(); i++) {
                    if (Character.isDigit(legendaryAction.charAt(i))) {
                        String num = legendaryAction.substring(i);
                        legendaryAction = legendaryAction.substring(0, i-1);
                        index = Integer.parseInt(num.trim());
                    }
                }
                ((Monster)this.initiativeOrder.get(index)).legendaryAction(legendaryAction);
            } else if (input.startsWith("legendary resistance")){
                String legendaryResistance = input.substring(20);
                Integer index = Integer.parseInt(legendaryResistance.trim());
                ((Monster)this.initiativeOrder.get(index)).useLegendaryResistance();
            } else if (input.startsWith("action")){
                if (actionUsed){
                    System.out.println("You have already used your action this turn.");
                    return;
                }
                String action = input.substring(7);
                ((Monster)this.initiativeOrder.get(currentInitiative)).action(action);
                actionUsed = true;
            } else if (input.startsWith("end turn")){
                this.currentInitiative += 1;
                if (this.currentInitiative >= this.initiativeOrder.size()){
                    this.currentInitiative = 0;
                }
                System.out.println(this.initiativeOrder.get(this.currentInitiative).turnPrompt());
                this.bonusActionUsed = false;
                this.actionUsed = false;
            } else if (input.startsWith("summary")){
                this.printSummary();
            } else if (input.startsWith("close")){
                this.inCombat = false;
            } else if (input.indexOf("save") != -1){
                String thingToBeSaved = input.substring(input.indexOf("save")+5);
                String saveType = thingToBeSaved.split(" ")[0];
                // do the saving throw method
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
            System.out.println(i+"\t"+initiativeOrder.get(i));
        }
    }

    /** 
     * Rolls dice or does math based on a dice formula input by the user
     * @param input the dice formula input by the user
     */
    public static void roll(String input){
        if (!input.contains("d")){
            int result = 0;
            if (input.contains("/")){
                int x = Integer.parseInt(input.substring(0, input.indexOf("/")).trim());
                int y = Integer.parseInt(input.substring(input.indexOf("/")+1).trim());
                result = x/y;
            } else if (input.contains("*")){
                int x = Integer.parseInt(input.substring(0, input.indexOf("*")).trim());
                int y = Integer.parseInt(input.substring(input.indexOf("*")+1).trim());
                result = x*y;
            } else if (input.contains("+")){
                int x = Integer.parseInt(input.substring(0, input.indexOf("+")).trim());
                int y = Integer.parseInt(input.substring(input.indexOf("+")+1).trim());
                result = x+y;
            } else if (input.contains("-")){
                int x = Integer.parseInt(input.substring(0, input.indexOf("-")).trim());
                int y = Integer.parseInt(input.substring(input.indexOf("-")+1).trim());
                result = x-y;
            }
            System.out.println(input + " = " + result);
            return;
        }
        String[] dice = input.split(" ");
        int rollresult = 0;
        for (String die : dice){
            int numberOfDice = Integer.parseInt(die.substring(0,die.indexOf("d")));
            int sizeOfDie = Integer.parseInt(die.substring(die.indexOf("d")+1));
            rollresult += numberOfDice*(int)(Math.random()*sizeOfDie + 1);
        }
        System.out.println(rollresult);
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
            int initiative = creature.rollInitiative(initiativeScanner);
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
