public class Placeholder extends Creature {
    private int initative;
    
    public Placeholder(int initative, String name){
        this.initative=initative;
        this.name=name;
    }
    
    public int rollInitiative(){
        return initative;
    }

    public String turnPrompt(){
        return "Since it is initative count "+this.initative+" it is time to do "+this.name;
    }

    public String toString(){
        return "Placeholder in initative order for "+this.name;
    }
}
