import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Hashtable;
import java.util.Scanner;

public class Monster extends Creature{
    protected String name;
    private Integer AC;
    protected Integer HPmax;
    protected Integer HP;
    private Integer initiativeBonus;
    protected String statBlock;
    private boolean preRolled = false;
    private int preRolledInitiative;
    private int legendaryResistances;
    protected String locationNotes = null;
    private String generalNotes = null;
    private Hashtable<String, Interaction> actions;
    private Hashtable<String, Interaction> legendaryActions;
    private Hashtable<String, Interaction> reactions;
    private Hashtable<String, Interaction> bonusActions;
    private Hashtable<String, Integer> abilities;

    public Monster(String fileName){
        MonsterBuilder constructor = new MonsterBuilder(fileName);
        this.statBlock = constructor.getStatBlock();
        this.name = constructor.getName();
        this.AC = constructor.getAC();
        this.HPmax = constructor.getHP();
        this.HP = this.HPmax;
        this.initiativeBonus = constructor.getInitiative();
    }

    public Monster(String fileName, String locationNotes){
        this(fileName);
        this.locationNotes = locationNotes;
    }

    public Monster(String name, int health, int AC, int initiativeBonus, String statBlock, int preRolledInitiative){
        this(name, health, AC, initiativeBonus, statBlock);
        this.preRolledInitiative = preRolledInitiative;
        this.preRolled = true;
    }
    
    public Monster(String name, int health, int AC, int initiativeBonus, String statBlock, boolean hasLair, int preRolledInitiative){
        this(name, health, AC, initiativeBonus, statBlock, hasLair);
        this.preRolledInitiative = preRolledInitiative;
        this.preRolled = true;
    }

    public Monster(String name, int health, int AC, int initiativeBonus, String statBlock, boolean hasLair){
        this(name, health, AC, initiativeBonus, statBlock);
        this.hasLair = hasLair;
    }

    public Monster(String name, int health, int AC, int initiativeBonus, String statBlock){
        this.subclass = CreatureType.MONSTER;
        this.name = name;
        this.HP = health;
        this.HPmax = health;
        this.AC = AC;
        this.initiativeBonus = initiativeBonus;
        this.statBlock = statBlock;
    }

    // gettors
    public String getName(){
        return this.name;
    }

    public String getStats(){
        return this.statBlock;
    }

    public int getHP(){
        return this.HP;
    }

    public int getHPmax(){
        return this.HPmax;
    }

    public int getAC(){
        return this.AC;
    }

    public String damage(int amt){
        this.HP = Math.max(this.HP-amt, 0);
        return this.name+" has been hit for "+amt+" reducing it to "+this.HP+" hit points.";
    }

    public String heal(int amt){
        this.HP = Math.min(this.HP+amt, this.HPmax);
        return this.name+" has been healed for "+amt+" increasing it to "+this.HP+" hit points.";
    }

    public String takeNote(String note){
        if (this.generalNotes == null){
            this.generalNotes = note;
        } else {
            this.generalNotes += " \n "+ note;
        }
        return "Notes have been saved.";
    }
    public String toString(){
        if (this.locationNotes == null){
            return name+" at "+HP+"/"+HPmax+" HP, AC "+AC;
        } else {
            return name+" at "+HP+"/"+HPmax+" HP, AC "+AC + " ("+this.locationNotes+")";
        }
    }

    public String turnPrompt(){
        String result = "It is "+this.name+"'s turn.";
        if (this.locationNotes != null){
            result += " \n Specific location notes for "+this.name+" are "+ this.locationNotes +".";
        }
        result += "\n Here is a stat block for "+this.name+".\n" + this.statBlock+"\n"+this.name+" is at "+this.HP+" out of "+this.HPmax+" health.";
        if (this.generalNotes != null){
            result += " \n Additional notes for "+this.name+": \n "+this.generalNotes;
        }
        return result;
    }

    public int rollInitiative(Scanner initScanner){
        if (!this.preRolled){
            Random d20 = new Random();
            return d20.nextInt(20)+1+this.initiativeBonus;
        }else{
            return preRolledInitiative;
        }
    }

    public void useLegendaryResistance(){
        if (this.legendaryResistances > 0){
            this.legendaryResistances -= 1;
            System.out.println(this.name+" uses a legendary resistance! It has "+this.legendaryResistances+" remaining.");
        } else {
            System.out.println(this.name+" has no legendary resistances remaining!");
        }
    }

    public void action(String action){
        System.out.println(name+" uses "+action+".");
        System.out.println(actions.get(action).getDescription());
        InitiativeTracker.roll(actions.get(action).getDiceRolls());
    }

    public void bonusAction(String bonusAction){
        System.out.println(name+" uses "+bonusAction+".");
        System.out.println(bonusActions.get(bonusAction).getDescription());
        InitiativeTracker.roll(bonusActions.get(bonusAction).getDiceRolls());
    }

    public void reaction(String reaction){
        System.out.println(name+" uses "+reaction+".");
        System.out.println(reactions.get(reaction).getDescription());
        InitiativeTracker.roll(reactions.get(reaction).getDiceRolls());
    }

    public void legendaryAction(String legendaryAction){
        System.out.println(name+" uses "+legendaryAction+".");
        System.out.println(legendaryActions.get(legendaryAction).getDescription());
        InitiativeTracker.roll(legendaryActions.get(legendaryAction).getDiceRolls());
    }


    public static void main(String[] args) {
    }
}