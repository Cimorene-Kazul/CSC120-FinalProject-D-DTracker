import java.util.Random;

public class Monster extends Creature{
    private String name;
    private int AC;
    private int HPmax;
    private int HP;
    private int initativeBonus;
    private String statBlock;
    private boolean preRolled = false;
    private int preRolledInitative;

    public Monster(String name, int health, int AC, int initativeBonus, String statBlock, int preRolledInitative){
        this(name, health, AC, initativeBonus, statBlock);
        this.preRolledInitative = preRolledInitative;
        this.preRolled = true;
    }
    
    public Monster(String name, int health, int AC, int initativeBonus, String statBlock){
        this.name = name;
        this.HP = health;
        this.HPmax = health;
        this.AC = AC;
        this.initativeBonus = initativeBonus;
        this.statBlock = statBlock;
    }

    public String getStats(){
        return this.statBlock;
    }

    public String toString(){
        return name+" at "+HP+"/"+HPmax+" HP, AC "+AC;
    }

    public String TurnPrompt(){
        return "It is "+this.name+"'s turn. \n Here is a stat block for "+this.name+".\n" + this.statBlock+"\n"+this.name+" is at "+this.HP+" out of "+this.HPmax+" health.";
    }

    public int rollInitative(){
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