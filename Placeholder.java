public class Placeholder extends Creature {
    private int initative;
    
    public Placeholder(int initative, String name){
        this.initative=initative;
        this.name=name;
    }
    
    public int rollInitative(){
        return initative;
    }

    public String TurnPrompt(){
        return "Since it is initative count "+this.initative+" it is time to do "+this.name;
    }
}
