abstract class Creature {
    String name;
    CreatureType subclass;
    private boolean hasLair;

    public Creature(String name, CreatureType subclass, boolean hasLair){
        this.name = name;
        this.subclass = subclass;
        this.hasLair = hasLair;
    }

    public boolean getLair(){
        return this.hasLair;
    }

    public CreatureType getType(){
        return this.subclass;
    }

    abstract int rollInitative();
    abstract String TurnPrompt();
}
