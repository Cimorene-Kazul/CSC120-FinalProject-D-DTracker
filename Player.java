import java.util.Scanner;

public class Player extends Creature{
    String player;
    
    /**
     * Constructor to create a Player creature
     * @param PCName The name of the character being played
     * @param PCPlayer The name of the player controlling the character
     */
    public Player(String PCName, String PCPlayer){
        this.name = PCName;
        this.player = PCPlayer;
        this.subclass = CreatureType.PLAYER;
    }

    /**
     * Get the name of the character
     * @return The name of the character
     */
    public String getName(){
        return this.name;
    }

    /**
     * Prompts the player to take their turn
     * @return message prompt
     */
    public String turnPrompt(){
        return "It is "+this.name+"'s turn. "+this.player+" please take your turn as "+this.name+".";
    }

    /**
     * Asks for the player's initiative and returns it
     * @param initScanner
     * @return the initiative the player inputs
     */
    public int rollInitiative(Scanner initScanner){
        System.out.println(this.player+" what is "+this.name+"'s initiative?");
        int initiative = Integer.parseInt(initScanner.nextLine().trim());
        return initiative;
    }

    /**
     * Returns a string with the player's name and character name
     * @return player string
     */
    public String toString(){
        return this.player+"'s player character, "+this.name;
    }

    /**
     * Formats the player's info to be saved
     * @return the player's information in the format to be saved
     */
    public String saveInfo(){
        return "PLAYER <<<SPACING MARKER>>>"+this.name+"<<<SPACING MARKER>>>"+this.player;
    }

    /**
     * Turns a properly formatted String into a Player
     * @param saveInfo
     * @return player object
     */
    public static Player parsePlayer(String saveInfo){
        String[] pieces = saveInfo.split("<<<SPACING MARKER>>>");
        return new Player(pieces[1].trim(), pieces[2].trim());
    }
}