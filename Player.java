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

    public double rollInitiative(Scanner initScanner){
        System.out.println(this.player+" what is "+this.name+"'s initiative?");
        int initiative = Integer.parseInt(initScanner.nextLine().trim());
        return initiative;
    }

    public String toString(){
        return this.player+"'s player character, "+this.name;
    }
}