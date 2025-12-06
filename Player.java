import java.util.Scanner;

public class Player extends Creature{
    String player;
    
    public Player(String PCName, String PCPlayer){
        this.name = PCName;
        this.player = PCPlayer;
        this.subclass = CreatureType.PLAYER;
    }

    public String getName(){
        return this.name;
    }

    public String turnPrompt(){
        return "It is "+this.name+"'s turn. "+this.player+" please take your turn as "+this.name+".";
    }

    public int rollInitiative(Scanner initScanner){
        System.out.println(this.player+" what is "+this.name+"'s initiative?");
        int initiative = Integer.parseInt(initScanner.nextLine().trim());
        return initiative;
    }

    public String toString(){
        return this.player+"'s player character, "+this.name;
    }

    public String saveInfo(){
        return "PLAYER <<<SPACING MARKER>>>"+this.name+"<<<SPACING MARKER>>>"+this.player;
    }

    public static Player parsePlayer(String saveInfo){
        String[] pieces = saveInfo.split("<<<SPACING MARKER>>>");
        return new Player(pieces[1].trim(), pieces[2].trim());
    }
}