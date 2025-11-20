import java.util.Scanner;

public class Player extends Creature{
    String player;
    boolean hasLair = false;
    
    public Player(String PCName, String PCPlayer){
        this.name = PCName;
        this.player = PCPlayer;
        this.subclass = CreatureType.PLAYER;
    }

    public String turnPrompt(){
        return "It is "+this.name+"'s turn. "+this.player+" please take your turn as "+this.name+".";
    }

    public int rollInitiative(Scanner initScanner){
        System.out.println(this.player+" what is "+this.name+"'s initative?");
        int initative = Integer.parseInt(initScanner.nextLine().trim());
        return initative;
    }

    public String toString(){
        return this.player+"'s player character, "+this.name;
    }
}