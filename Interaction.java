import java.util.Hashtable;

public class Interaction{
    String name;
    String description;
    Hashtable<String, DiceFormula> diceRolls;

    public Interaction(String text){
        this(text.substring(0, Math.min(text.indexOf("("), text.indexOf("-"))), text.substring(0, 0));
        try{this.diceRolls.put("DC", new DiceFormula(ParsingTools.nextWord(text, text.indexOf(" AC ")+2)));} catch (RuntimeException e){}
        try{this.diceRolls.put("to hit", new DiceFormula(ParsingTools.nextWord(text, text.indexOf("+"))));} catch (RuntimeException e){}
    }

    public Interaction(String name, String description){
        this(name, description, new Hashtable<>());
    }

    public Interaction(String name, String description, Hashtable<String,DiceFormula> diceRolls){
        this.name = name;
        this.description = description;
        this.diceRolls = diceRolls;
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
}