import java.util.Scanner;

public class Placeholder extends Creature {
    private int initiative;
    private String text;
    
    public Placeholder(int initiative, String name, String text){
        this.initiative=initiative;
        this.name=name;
        this.text=text;
    }

    public Placeholder(int initiative, String name){
        this.initiative=initiative;
        this.name=name;
        this.text="";
    }

    public String getName(){
        return this.name;
    }
    
    public double rollInitiative(Scanner initScanner){
        return initiative;
    }

    public String turnPrompt(){
        return "Since it is initiative count "+this.initiative+" it is time to do "+this.name +"\n"+this.text;
    }

    public String toString(){
        return "Placeholder in initiative order for "+this.name;
    }
}
