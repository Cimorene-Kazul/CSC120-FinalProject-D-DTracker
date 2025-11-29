public class PlaceholderEntity extends InitiativeEntityModel{
    private double initiative;
    
    public PlaceholderEntity(double initiative, String name){
        this.initiative=initiative;
        this.name=name;
    }
    
    public double rollInitiative(){
        return initiative;
    }

    public String turnPrompt(){
        return "Since it is initiative count "+this.initiative+" it is time to do "+this.name;
    }

    public String toString(){
        return "Placeholder in initiative order for "+this.name;
    }
    
}
