import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class InitiativeTracker {
    ArrayList<Creature> creatures;
    Hashtable<Integer, ArrayList<Creature>> initiativeTable;
    ArrayList<Creature> initiativeOrder;
    Integer top = 20; // the 'top of the initiative order'
    Integer bottom = 0; // the 'bottom of the initiative order'
    boolean inCombat = false;

    public void doAction(String input){
        if (input.startsWith("heal")){
            String thingToBeHealed = input.substring(5);
            // heal the thingToBeHealed
        } else if (input.startsWith("damage")){
            String thingToBeDamaged = input.substring(7);
            // damage the thingToBeDamaged
        } else if (input.startsWith("roll")){
            String die = input.substring(5);
            int 
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
            this.inCombat = false;
        } else if (input.startsWith("summary")){
            // do the summary method
        } else if (input.indexOf("save") != -1){
            String thingToBeSaved = input.substring(input.indexOf("save")+5);
            String saveType = thingToBeSaved.split(" ")[0];
            // do the saving throw method
        }
    }

    public roll(String die){
        index = indexOf()
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

    private void rollInitiatives(){
        for (Creature creature: this.creatures){
            int initiative = creature.rollInitiative();

            if (initiative > this.top){
                this.top = initiative;
            }
            if (initiative < this.bottom){
                this.bottom = initiative;
            }


            if (this.initiativeTable.containsKey(initiative)){
                this.initiativeTable.get(initiative).add(creature);
            } else{
                ArrayList<Creature> startingArray = new ArrayList<>();
                startingArray.add(creature);
                this.initiativeTable.put(initiative, startingArray);
            }
        }

        for (Integer i=this.top; i<this.bottom; i--){
            if (this.initiativeTable.get(i) != null) {
                for (Creature c:this.initiativeTable.get(i)){
                    this.initiativeOrder.add(c);
                }
            }
        }
    }

    private void takeTurn(){
        Scanner turnScanner = new Scanner(System.in);
        String command = turnScanner.nextLine();
        turnScanner.close();
    }

    public void rollInitiative(){
        this.rollInitiatives();
        this.inCombat = true;
        while (inCombat) {
            this.takeTurn();
        }
    }



    public static void main(String[] args) {
        Player moth = new Player("Moth", "Elizabeth");
        Player emia = new Player("Emia", "Celina");
        Monster luna = new Monster("Ilumetrix Luna", 300, 21, +5, "<substitute for Luna's stats>");
    }
}
