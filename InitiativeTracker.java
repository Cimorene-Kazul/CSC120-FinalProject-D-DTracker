import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.lang.Integer;

public class InitiativeTracker {
    ArrayList<Creature> creatures;
    ArrayList<Creature> initiativeOrder;
    boolean inCombat = false;
    Integer currentInitative;

    public void doAction(String input){
        if (input.startsWith("heal")){
            String[] commandPieces = input.trim().split(" ");
            Integer index = Integer.parseInt(commandPieces[1]);
            Integer amt = Integer.parseInt(commandPieces[2]);
            initiativeOrder.get(index).damage(amt);
        } else if (input.startsWith("damage")){
            String[] commandPieces = input.trim().split(" ");
            Integer index = Integer.parseInt(commandPieces[1]);
            Integer amt = Integer.parseInt(commandPieces[2]);
            initiativeOrder.get(index).heal(amt);
        } else if (input.startsWith("roll")){
            String die = input.substring(5);
            this.roll(die);
        } else if (input.startsWith("bonus action")){
            String bonusAction = input.substring(13);
            // do the bonus action method
        } else if (input.startsWith("reaction")){
            String reaction = input.substring(9);
            // do the reaction method
        } else if (input.startsWith("legendary action")){
            String legendaryAction = input.substring(17);
            // do the legendary action method
        } else if (input.startsWith("legendary resistance")){
            String legendaryResistance = input.substring(20);
            // do the legendary resistance method
        } else if (input.startsWith("action")){
            String action = input.substring(7);
            // do the action method
        } else if (input.startsWith("end turn")){
            this.currentInitative += 1;
            if (this.currentInitative >= this.initiativeOrder.size()){
                this.currentInitative = 0;
            }
            System.out.println(this.initiativeOrder.get(this.currentInitative).turnPrompt());
        } else if (input.startsWith("summary")){
            this.printSummary();
        } else if (input.startsWith("close")){
            this.inCombat = false;
        } else if (input.indexOf("save") != -1){
            String thingToBeSaved = input.substring(input.indexOf("save")+5);
            String saveType = thingToBeSaved.split(" ")[0];
            // do the saving throw method
        }
    }

    private void printSummary(){
        for (int i=0; i<this.initiativeOrder.size(); i++){
            System.out.println(i+"\t"+initiativeOrder.get(i));
        }
    }

    public void roll(String input){
        if (!input.contains("d")){
            int result = 0;
            if (input.contains("/")){
                int x = Integer.parseInt(input.substring(0, input.indexOf("/")));
                int y = Integer.parseInt(input.substring(input.indexOf("/")+1));
                result = x/y;
            } else if (input.contains("*")){
                int x = Integer.parseInt(input.substring(0, input.indexOf("*")));
                int y = Integer.parseInt(input.substring(input.indexOf("*")+1));
                result = x*y;
            } else if (input.contains("+")){
                int x = Integer.parseInt(input.substring(0, input.indexOf("+")));
                int y = Integer.parseInt(input.substring(input.indexOf("+")+1));
                result = x+y;
            } else if (input.contains("-")){
                int x = Integer.parseInt(input.substring(0, input.indexOf("-")));
                int y = Integer.parseInt(input.substring(input.indexOf("-")+1));
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
    
    public InitiativeTracker(){
        this(new ArrayList<Creature>());
    }

    public InitiativeTracker(ArrayList<Creature> creatures){
        this.creatures = creatures;
        for (Creature c: creatures){
            if (c.getLair()){
                Placeholder lair = new Placeholder(20, c.getName()+"'s lair actions"); 
                creatures.add(lair);
            }
        }
    }

    public void addCreature(Creature c){
        this.creatures.add(c);
        if (c.getLair()){
            Placeholder lair = new Placeholder(20, c.getName()+"'s lair actions"); 
            creatures.add(lair);
        }
    }

    private void rollInitiatives(Scanner initativeScanner){
        Hashtable<Integer, ArrayList<Creature>> initiativeTable = new Hashtable<>();
        Integer top = 20; // the 'top of the initiative order'
        Integer bottom = 0; // the 'bottom of the initiative order'
        for (Creature creature: this.creatures){
            int initiative = creature.rollInitiative(initativeScanner);
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

    private void takeTurn(Scanner turnScanner){
        String command = turnScanner.nextLine();
        this.doAction(command);
    }

    public void rollInitiative(){
        Scanner encounterScanner = new Scanner(System.in);
        this.rollInitiatives(encounterScanner);
        this.inCombat = true;
        this.currentInitative = 0;
        System.out.println(this.initiativeOrder.get(this.currentInitative).turnPrompt());
        while (inCombat) {
            this.takeTurn(encounterScanner);
        }
        encounterScanner.close();
    }



    public static void main(String[] args) {
        Player moth = new Player("Moth", "Elizabeth");
        Player emia = new Player("Emia", "Celina");
        Monster luna = new Monster("Ilumetrix Luna", 300, 21, +5, "<substitute for Luna's stats>", true);
        InitiativeTracker myEncounter = new InitiativeTracker();
        myEncounter.addCreature(luna);
        myEncounter.addCreature(emia);
        myEncounter.addCreature(moth);
        myEncounter.rollInitiative();
    }
}
