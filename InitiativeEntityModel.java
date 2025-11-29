abstract class InitiativeEntityModel {
    protected String name;
    protected CreatureType subclass;
    protected boolean hasLair = false;
    protected boolean calcInit = true;

    public boolean calculatesOwnInitiative(){
        return this.calcInit;
    }
    
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
        return this.name+" is not an entity that can record taking damage.";
    }    
    
    public String heal(int amt){
        return this.name+" is not an entity that can record being healed.";
    }

    public String takeNote(String note){
        return this.name+" is not an entity that can record notes.";
    }

    public String takeAction(String action){
        return this.name+" is not an entity that has actions implemented.";
    }
    public String takeBonusAction(String bonusAction){
        return this.name+" is not an entity that has bonus actions implemented.";
    }
    public String takeLegendaryAction(String legendaryAction){
        return this.name+" is not an entity that has legendary actions implemented.";
    }
    public String takeReaction(String reaction){
        return this.name+" is not an entity that has reactions implemented.";
    }
    public String useLegendaryResistance(){
        return this.name+" is not an entity that has legendary resistance implemented.";
    }

    abstract String turnPrompt();
    abstract double rollInitiative();
}
