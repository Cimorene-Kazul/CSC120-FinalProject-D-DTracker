import java.util.Scanner;

abstract class Creature {
    protected String name;
    protected CreatureType subclass;
    protected boolean hasLair = false;

    /**
     * Getter for lair
     * @return hasLair
     */
    public boolean getLair(){
        return this.hasLair;
    }

    /**
     * Getter for name
     * @return name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Getter for subclass
     * @return subclass
     */
    public CreatureType getType(){
        return this.subclass;
    }

    /**
     * Placeholder for incorrect use of damage method to avoid an error
     * @return message saying that the creature cannot be damaged
     */
    public String damage(int amt){
        return this.name+" is not a monster and thus cannot be damaged.";
    }    
    
    /** 
     * Placeholder for incorrect use of heal method to avoid an error
     * @return message saying that the creature cannot be healed
    */
    public String heal(int amt){
        return this.name+" is not a monster and thus cannot be healed.";
    }

    /**
     * Placeholder for incorrect use of takeNote method to avoid an error
     * @return message saying that the creature cannot take notes
     */
    public String takeNote(String note){
        return this.name+" is not a monster and thus cannot take notes.";
    }

    // Abstract methods to be implemented by subclasses
    abstract String saveInfo();
    abstract int rollInitiative(Scanner initScanner);
    abstract String turnPrompt();
}