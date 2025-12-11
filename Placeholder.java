import java.util.Scanner;

public class Placeholder extends Creature {
    private int initiative;

    /**
     * Constructor for Placeholder
     * @param initiative
     * @param name
     */
    public Placeholder(int initiative, String name){
        this.initiative=initiative;
        this.name=name;
        this.subclass = CreatureType.PLACEHOLDER;
    }

    /**
     * Gets Placeholder name
     * @return name
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Gets initiative <== WHY DOES THIS NEED A SCANNER PARAMETER
     * @return initiative
     */
    public int rollInitiative(Scanner initScanner){
        return initiative;
    }

    /**
     * Prompts for whatever the placeholder is for
     * @return placeholder message
     */
    public String turnPrompt(){
        return "Since it is initiative count "+this.initiative+" it is time to do "+this.name;
    }

    /**
     * String for the Placeholder
     * @return what the placeholder is for
     */
    public String toString(){
        return "Placeholder in initiative order for "+this.name;
    }

    /**
     * Saves the placeholder as a String with spacing markers
     * @return String
     */
    public String saveInfo(){
        return "PLACEHOLDER <<<SPACING MARKER>>>"+this.initiative+"<<<SPACING MARKER>>>"+this.name;
    }

    /**
     * Turns a properly formatted String back into a Placeholder
     * @param saveInfo
     * @return Placeholder
     */
    public static Placeholder parsePlaceholder(String saveInfo){
        String[] pieces = saveInfo.split("<<<SPACING MARKER>>>");
        return new Placeholder(Integer.parseInt(pieces[1].trim()), pieces[2].trim());
    }
}
