import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import javax.management.monitor.StringMonitor;

public class InitativeTracker {
    ArrayList<Creature> creatures;
    Hashtable<Integer, ArrayList<Creature>> initativeOrder;
    Integer top = 20; // the 'top of the initative order'
    Integer bottom = 0; // the 'bottom of the initative order'
    boolean inCombat = false;
    
    public InitativeTracker(){
        this(new ArrayList<Creature>());
    }

    public InitativeTracker(ArrayList<Creature> creatures){
        this.creatures = creatures;
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


            if (this.initativeOrder.containsKey(initative)){
                this.initativeOrder.get(initative).add(creature);
            } else{
                ArrayList<Creature> startingArray = new ArrayList<>();
                startingArray.add(creature);
                this.initativeOrder.put(initative, startingArray);
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
        Monster luna = new Monster("Ilumetrix Luna", 300, 21, +5, "<substuitute for Luna's stats>");
    }
}
