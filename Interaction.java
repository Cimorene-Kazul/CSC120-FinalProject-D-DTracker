public class Interaction{
    String name;
    String description;
    String diceRolls;

    public Interaction(String name, String description, String diceRolls){
        this.name = name;
        this.description = description;
        this.diceRolls = diceRolls;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public String getDiceRolls(){
        return this.diceRolls;
    }
}