import java.util.ArrayList;
import java.util.Random;
import java.util.Hashtable;
import java.util.Scanner;

public class Monster extends Creature{
    private String name;
    private int AC;
    private int HPmax;
    private int HP;
    private int initativeBonus;
    private String statBlock;
    private boolean preRolled = false;
    private int preRolledInitative;
    private boolean hasLair = false;
    private Hashtable<String, Integer> abilities;

    public Monster(String name, int health, int AC, int initativeBonus, String statBlock, int preRolledInitative){
        this(name, health, AC, initativeBonus, statBlock);
        this.preRolledInitative = preRolledInitative;
        this.preRolled = true;
    }
    
    public Monster(String name, int health, int AC, int initativeBonus, String statBlock, boolean hasLair, int preRolledInitative){
        this(name, health, AC, initativeBonus, statBlock, hasLair);
        this.preRolledInitative = preRolledInitative;
        this.preRolled = true;
    }

    public Monster(String name, int health, int AC, int initativeBonus, String statBlock, boolean hasLair){
        this(name, health, AC, initativeBonus, statBlock);
        this.hasLair = hasLair;
    }

    public Monster(String name, int health, int AC, int initativeBonus, String statBlock){
        this.subclass = CreatureType.MONSTER;
        this.name = name;
        this.HP = health;
        this.HPmax = health;
        this.AC = AC;
        this.initativeBonus = initativeBonus;
        this.statBlock = statBlock;
    }

    // gettors
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
        this.HP = Math.min(this.HP-amt, 0);
        return this.name+" has been hit for "+amt+" reducing it to "+this.HP+" hit points.";
    }

    public String heal(int amt){
        this.HP = Math.max(this.HP+amt, this.HPmax);
        return this.name+" has been healed for "+amt+" increasing it to "+this.HP+" hit points.";
    }

    public String toString(){
        return name+" at "+HP+"/"+HPmax+" HP, AC "+AC;
    }

    public String turnPrompt(){
        return "It is "+this.name+"'s turn. \n Here is a stat block for "+this.name+".\n" + this.statBlock+"\n"+this.name+" is at "+this.HP+" out of "+this.HPmax+" health.";
    }

    public int rollInitiative(Scanner initScanner){
        if (!this.preRolled){
            Random d20 = new Random();
            return d20.nextInt(20)+1+this.initativeBonus;
        }else{
            return preRolledInitative;
        }
    }


    public static void main(String[] args) {
        
    }
}