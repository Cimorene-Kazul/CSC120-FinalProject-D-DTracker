public class Ability {
    private String name;
    private String description;
    private int uses;

    public Ability(String name, String description, int uses){
        this.name = name;
        this.description = description;
        this.uses = uses;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public int getUses(){
        return this.uses;
    }
}
