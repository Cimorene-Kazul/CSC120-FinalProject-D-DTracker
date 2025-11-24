import java.util.Scanner;

public class Placeholder extends Creature {
    private int initiative;
    
    public Placeholder(int initiative, String name){
        this.initiative=initiative;
        this.name=name;
    }

    public String getName(){
        return this.name;
    }
    
    public int rollInitiative(Scanner initScanner){
        return initiative;
    }

    public String turnPrompt(){
        return "Since it is initiative count "+this.initiative+" it is time to do "+this.name;
    }

    public String toString(){
        return "Placeholder in initiative order for "+this.name;
    }
}
