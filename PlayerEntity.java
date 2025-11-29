public class PlayerEntity extends InitiativeEntityModel{
    String player;
    boolean calcInit = false;
    double initative = 10;
    
    public PlayerEntity(String PCName, String PCPlayer){
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

    public String toString(){
        return this.player+"'s player character, "+this.name;
    }

    public double rollInitiative(){
        return this.initative;
    }

    public void setInitiative(double value){
        this.initative = value;
    }
}
