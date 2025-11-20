abstract class Creature {
    String name;
    private boolean hasLair;

    public boolean getLair(){
        return this.hasLair;
    }

    abstract int rollInitative();
    abstract String TurnPrompt();
}
