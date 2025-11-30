import java.util.Hashtable;

public class Interaction{
    String name;
    String description;
    Hashtable<String, DiceFormula> diceRolls;
    boolean hasUses = false;
    int uses = 0;

    public Interaction(String text){
        this(text.substring(0, Math.min(text.indexOf("("), text.indexOf("-"))), text.substring(text.indexOf("-")));
        try{this.diceRolls.put("DC", new DiceFormula(ParsingTools.nextWord(text, text.indexOf(" AC ")+2)));} catch (RuntimeException e){}
        try{this.diceRolls.put("to hit", new DiceFormula(ParsingTools.nextWord(text, text.indexOf("+"))));} catch (RuntimeException e){}
        if (text.indexOf("/day")!=-1 && text.indexOf("/day")<text.indexOf("-")){
            for (int start = 0; start<text.indexOf("/day"); start++){
                try {
                    this.uses = Integer.parseInt(text.substring(start, text.indexOf("/day")));
                } finally {
                    this.hasUses = true;
                }
            }
        }
    }

    public Interaction(String name, String description){
        this(name, description, new Hashtable<>());
    }

    public Interaction(String name, String description, Hashtable<String,DiceFormula> diceRolls){
        this.name = name;
        this.description = description;
        this.diceRolls = diceRolls;
    }
    
    public Interaction(String name, String description, Hashtable<String,DiceFormula> diceRolls, int uses){
        this(name, description, diceRolls);
        this.hasUses = true;
        this.uses = uses;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public String getDiceRolls(){
        String result = "";
        for (String rollName: this.diceRolls.keySet()){
            if (result!= ""){
                result += "\n";
            }
            result += rollName +"\t"+this.diceRolls.get(rollName).roll();
        }
        return result;
    }

    public boolean hasUses(){
        return this.hasUses;
    }

    public int numUses(){
        return this.uses;
    }

    public void expendUse(){
        this.uses = Math.max(0, this.uses-1);
    }

    public String toString(){
        if (this.hasUses){
            if (this.uses == 0){
                return this.name + " has no uses remaining for this encounter.";
            } else {
                return this.name + " - " + this.description + "\n" + this.uses + " uses remaining." + "\n" + this.getDiceRolls();
            }
        } else {
            return this.name + " - " + this.description + "\n" + this.getDiceRolls();
        }
    }
}