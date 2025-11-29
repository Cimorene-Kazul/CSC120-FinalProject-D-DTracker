import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class Monster extends Creature{
    private Integer AC;
    protected Integer HPmax;
    protected Integer HP;
    private Integer initiativeBonus;
    protected String statBlock;
    private int legendaryResistances = 0;
    protected String locationNotes = null;
    private String generalNotes = null;
    private Hashtable<String, Interaction> actions;
    private Hashtable<String, Interaction> legendaryActions;
    private Hashtable<String, Interaction> reactions;
    private Hashtable<String, Interaction> bonusActions;

    public Monster(String fileName, String locationNotes){
        this(fileName);
        this.locationNotes = locationNotes;
    }

    public Monster(String fileName){
        this.subclass = CreatureType.MONSTER;
        File statBlockFile = new File("StatBlocks/"+fileName+".txt");
        try (Scanner fileReader = new Scanner(statBlockFile)){
            int lineNumber = 0;
            String nextThing = "";
            String type = "Trait";
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                this.statBlock += line +" \n ";
                lineNumber += 1;
                if (lineNumber == 1){
                    this.name = line.trim();
                } else if (lineNumber == 2){
                    this.AC = Integer.parseInt(ParsingTools.nextWord(line, line.indexOf("AC")+2));
                    this.HPmax = Integer.parseInt(ParsingTools.nextWord(line, line.indexOf("HP")+2));
                    this.HP = this.HPmax;
                    this.initiativeBonus = Integer.parseInt(ParsingTools.nextWord(line, line.indexOf("Initiative")+11));
                }

                if (line.trim().startsWith("Actions")){
                    type = "Actions";
                } else if (line.trim().startsWith("Reactions")){
                    type = "Reactions";
                } else if (line.trim().startsWith("Legendary Actions")){
                    type = "Legendary Actions";
                } else if (line.trim().startsWith("Bonus Actions")){
                    type = "Bonus Actions";
                }else{
                    if (!line.startsWith("\t")){
                        if (type=="Actions"){
                            Interaction action = new Interaction(nextThing);
                            this.actions.put(action.getName(), action);
                        } else if (type=="Reactions"){
                            Interaction reaction = new Interaction(nextThing);
                            this.reactions.put(reaction.getName(), reaction);
                        } else if (type=="Legendary Actions"){
                            Interaction legendaryAction = new Interaction(nextThing);
                            this.legendaryActions.put(legendaryAction.getName(), legendaryAction);
                        } else if (type=="Bonus Actions"){
                            Interaction bonusAction = new Interaction(nextThing);
                            this.bonusActions.put(bonusAction.getName(), bonusAction);
                        }
                        nextThing = "";
                    }
                }
                nextThing += line;
            }
        }catch(FileNotFoundException e){
            throw new RuntimeException(statBlockFile.getName()+" is not a valid file name in MonsterFiles.");
        }
    }

    public Monster(String name, int health, int AC, int initiativeBonus, String statBlock){
        this.subclass = CreatureType.MONSTER;
        this.name = name;
        this.HP = health;
        this.HPmax = health;
        this.AC = AC;
        this.initiativeBonus = initiativeBonus;
        this.statBlock = statBlock;
    }

    // gettors
    public String getName(){
        return this.name;
    }

    public String getStats(){
        return this.statBlock;
    }

    public int getHP(){
        return this.HP;
    }

    public int getHPmax(){
        return this.HPmax;
    }

    public int getAC(){
        return this.AC;
    }

    public String damage(int amt){
        this.HP = Math.max(this.HP-amt, 0);
        return this.name+" has been hit for "+amt+" reducing it to "+this.HP+" hit points.";
    }

    public String heal(int amt){
        this.HP = Math.min(this.HP+amt, this.HPmax);
        return this.name+" has been healed for "+amt+" increasing it to "+this.HP+" hit points.";
    }

    public String takeNote(String note){
        if (this.generalNotes == null){
            this.generalNotes = note;
        } else {
            this.generalNotes += " \n "+ note;
        }
        return "Notes have been saved.";
    }
    public String toString(){
        if (this.locationNotes == null){
            return name+" at "+HP+"/"+HPmax+" HP, AC "+AC;
        } else {
            return name+" at "+HP+"/"+HPmax+" HP, AC "+AC + " ("+this.locationNotes+")";
        }
    }

    public String turnPrompt(){
        String result = "It is "+this.name+"'s turn.";
        if (this.locationNotes != null){
            result += " \n Specific location notes for "+this.name+" are "+ this.locationNotes +".";
        }
        result += "\n Here is a stat block for "+this.name+".\n" + this.statBlock+"\n"+this.name+" is at "+this.HP+" out of "+this.HPmax+" health.";
        if (this.generalNotes != null){
            result += " \n Additional notes for "+this.name+": \n "+this.generalNotes;
        }
        return result;
    }

    public double rollInitiative(Scanner initScanner){
        D20Test initiativeRoll = new D20Test(this.initiativeBonus);
        return initiativeRoll.roll();
    }

    public String useLegendaryResistance(){
        if (this.legendaryResistances > 0){
            this.legendaryResistances -= 1;
            return (this.name+" uses a legendary resistance! It has "+this.legendaryResistances+" remaining.");
        } else {
            return (this.name+" has no legendary resistances remaining!");
        }
    }

    public String action(String action){
        String message = "";
        message += name+ " uses "+action+".";
        message += "\n"+actions.get(action).getName() +" - "+ actions.get(action).getDescription();
        message += "\n"+actions.get(action).getDiceRolls();
        return message;
    }

    public String bonusAction(String bonusAction){
        String message = "";
        message += name+ " uses "+bonusAction+".";
        message += "\n"+bonusActions.get(bonusAction).getName() +" - "+ bonusActions.get(bonusAction).getDescription();
        message += "\n"+bonusActions.get(bonusAction).getDiceRolls();
        return message;
    }

    public String reaction(String reaction){
        String message = "";
        message += name+ " uses "+reaction+".";
        message += "\n"+reactions.get(reaction).getName() +" - "+ reactions.get(reaction).getDescription();
        message += "\n"+reactions.get(reaction).getDiceRolls();
        return message;
    }

    public String legendaryAction(String legendaryAction){
        String message = "";
        message += name+ " uses "+legendaryAction+".";
        message += "\n"+legendaryActions.get(legendaryAction).getName() +" - "+ legendaryActions.get(legendaryAction).getDescription();
        message += "\n"+legendaryActions.get(legendaryAction).getDiceRolls();
        return message;
    }


    public static void main(String[] args) {
    }
}