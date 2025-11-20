abstract class Creature {
    protected String name;
    protected CreatureType subclass;
    protected boolean hasLair;

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

    abstract int rollInitiative();
    abstract String turnPrompt();
}
