import java.util.ArrayList;

public class EncounterController {
    private ArrayList<InitiativeEntityModel> entities;
    private ArrayList<InitiativeEntityModel> initiativeOrder;
    private boolean inCombat = false;
    private Integer currentInitiative;
    private EncounterView view;
    
    public EncounterController(EncounterView v){
        this.entities = new ArrayList<>();
        this.view = v;
    }
    public EncounterController(EncounterView v, InitiativeEntityModel... args){
        this(v);
        for (InitiativeEntityModel c: entities){
            this.entities.add(c);
        }
    }
    public EncounterController(EncounterView v, ArrayList<InitiativeEntityModel> entities){
        this.view = v;
        this.entities = entities;
    }

    public String toString(){
        String result = null;
        for (InitiativeEntityModel e: entities){
            if (result != null){
                result  += " \n ";
            }
            result += e;
        }
        return result;
    }

    public String heal(int entityIndex, int amt){
        return this.entities.get(entityIndex).heal(amt);
    }

    public String damage(int entityIndex, int amt){
        return this.entities.get(entityIndex).heal(amt);
    }

    public void takeNote(String message){
        this.entities.get(currentInitiative).takeNote(message);
    }
    public void takeNote(String message, int index){
        this.entities.get(index).takeNote(message);
    }

    public String nextTurn(){
        this.currentInitiative -= 1;
        if (this.currentInitiative >= this.initiativeOrder.size()){
            this.currentInitiative = 0;
        }
        return this.entities.get(currentInitiative).turnPrompt();
    }

    public String summary(){
        if (this.inCombat){
            String result = null;
            for (int i=0; i<this.initiativeOrder.size(); i++){
                if (result!= null){
                    result += "\n";
                }
               result += i+"\t"+initiativeOrder.get(i);
            }
            return result;
        } else {
            return this.toString();
        }
    }

    public void endCombat(){
        this.inCombat = false;
    }
    
    public void addEntity(InitiativeEntityModel e){
        this.entities.add(e);
    }

    public void removeEntity(InitiativeEntityModel e){
        if (this.inCombat){
            throw new RuntimeException("Entities cannot be removed from an encounter in progress.");
        } else {
            if (this.entities.contains(e)){
                this.entities.remove(e);
            }else{
                throw new RuntimeException("This entity is not in the encounter and thus cannot be removed from it.");
            }
        }
    }

    public double getInitiative(InitiativeEntityModel e){
        if (!e.calculatesOwnInitiative()){
            
        }
        return e.rollInitiative();
    }

    public void rollInitiatives(){

    }
}