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

    abstract int rollInitiative();
    abstract String TurnPrompt();
}
