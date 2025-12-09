import java.util.Scanner;

public class Placeholder extends Creature {
    private int initiative;

    public Placeholder(int initiative, String name){
        this.initiative=initiative;
        this.name=name;
        this.subclass = CreatureType.PLACEHOLDER;
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

    public String saveInfo(){
        return "PLACEHOLDER <<<SPACING MARKER>>>"+this.initiative+"<<<SPACING MARKER>>>"+this.name;
    }

    public static Placeholder parsePlaceholder(String saveInfo){
        String[] pieces = saveInfo.split("<<<SPACING MARKER>>>");
        return new Placeholder(Integer.parseInt(pieces[1].trim()), pieces[2].trim());
    }
}
