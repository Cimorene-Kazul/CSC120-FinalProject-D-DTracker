import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class InitativeTracker {
    ArrayList<Creature> creatures;
    Hashtable<Integer, ArrayList<Creature>> initativeTable;
    ArrayList<Creature> initativeOrder;
    Integer top = 20; // the 'top of the initative order'
    Integer bottom = 0; // the 'bottom of the initative order'
    boolean inCombat = false;
    public String[] StartingActions = {"heal", "damage", "roll", "bonus action", "reaction", "legendary action", "legendary resistance", "action"};
    private String[] actions = {"end turn", "summary"};

    public void doActions(String input){
        for (int i = 0; i < actions.length; i++){
            if (input.startsWith(actions[i])){
                if (actions[i].equals("end turn")){
                    // call end turn method
                }
                else if (actions[i].equals("summary")){
                    // call summary method;
                }
                else if (actions[i].equals("roll")){
                    // call roll method
                }
            }
        }
        for (int j = 0; j < StartingActions.length; j++){
            if (input.startsWith(StartingActions[j])){
                if (StartingActions[j].equals("heal")){
                    String thingToBeHealed = input.substring(5).trim();
                    // call heal method on thingToBeHealed
                }
                else if (StartingActions[j].equals("damage")){
                    String thingToBeDamaged = input.substring(6).trim();
                    // call damage method on thingToBeDamaged
                }
                else if (StartingActions[j].equals("bonus action")){
                    String bonusActionName = input.substring(13).trim();
                    // call bonus action method on the name, or call this method again with the name (depending on implementation)
                }
                else if (StartingActions[j].equals("reaction")){
                    String reactionName = input.substring(8).trim();
                    // call reaction method on the name, or call this method again with the name
                }
                else if (StartingActions[j].equals("legendary action")){
                    String legendaryActionName = input.substring(16).trim();
                    // call legendary action method on the name, or call this method again with the name
                }
                else if (StartingActions[j].equals("legendary resistance")){
                    String legendaryResistanceName = input.substring(20).trim();
                    // call legendary resistance method on the same, or call this method again with the name
                }
                else if (StartingActions[j].equals("action")){
                    String actionName = input.substring(6).trim();
                    // call action method on the name, or call this method again with the name
                }
            }
        }
    }
    
    public InitativeTracker(){
        this(new ArrayList<Creature>());
    }

    public InitativeTracker(ArrayList<Creature> creatures){
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

    private void rollInitatives(){
        for (Creature creature: this.creatures){
            int initative = creature.rollInitative();

            if (initative > this.top){
                this.top = initative;
            }
            if (initative < this.bottom){
                this.bottom = initative;
            }


            if (this.initativeTable.containsKey(initative)){
                this.initativeTable.get(initative).add(creature);
            } else{
                ArrayList<Creature> startingArray = new ArrayList<>();
                startingArray.add(creature);
                this.initativeTable.put(initative, startingArray);
            }
        }

        for (Integer i=this.top; i<this.bottom; i--){
            if (this.initativeTable.get(i) != null) {
                for (Creature c:this.initativeTable.get(i)){
                    this.initativeOrder.add(c);
                }
            }
        }
    }

    private void takeTurn(){
        Scanner turnScanner = new Scanner(System.in);
        String command = turnScanner.nextLine();
        turnScanner.close();
    }

    public void rollInitative(){
        this.rollInitatives();
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
