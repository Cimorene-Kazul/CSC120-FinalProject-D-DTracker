import java.util.Scanner;

abstract class Creature {
    protected String name;
    protected CreatureType subclass;
    protected boolean hasLair = false;

    public boolean getLair(){
        return this.hasLair;
    }

    public String getName(){
        return this.name;
    }

    public CreatureType getType(){
        return this.subclass;
    }

    public String damage(int amt){
        return this.name+" is not a monster and thus cannot be damaged.";
    }    
    
    public String heal(int amt){
        return this.name+" is not a monster and thus cannot be healed.";
    }

    public String takeNote(String note){
        return this.name+" is not a monster and thus cannot take notes.";
    }
    abstract String saveInfo();
    abstract int rollInitiative(Scanner initScanner);
    abstract String turnPrompt();
}