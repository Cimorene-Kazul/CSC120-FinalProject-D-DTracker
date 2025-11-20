import java.util.Scanner;

public class Player extends Creature{
    String player;
    boolean hasLair = false;
    
    public Player(String PCName, String PCPlayer){
        this.name = PCName;
        this.player = PCPlayer;
        this.subclass = CreatureType.PLAYER;
    }

    public String TurnPrompt(){
        return "It is "+this.name+"'s turn. "+this.player+" please take your turn as "+this.name+".";
    }

    public int rollInitative(){
        Scanner initScanner = new Scanner(System.in);
        System.out.println(this.player+" what is "+this.name+"'s initative? \n");
        int initative = initScanner.nextInt();
        initScanner.close();
        return initative;
    }
}
