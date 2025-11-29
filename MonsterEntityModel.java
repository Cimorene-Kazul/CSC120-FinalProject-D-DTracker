public class MonsterEntityModel extends InitiativeEntityModel{
    private Integer AC;
    protected Integer HPmax;
    protected Integer HP;
    private Integer initiativeBonus;
    protected String statBlock;
    private boolean preRolled = false;
    private int preRolledInitiative;
    private int legendaryResistances = 0;
    protected String locationNotes = null;
    private String generalNotes = null;

    public MonsterEntityModel(String fileName, String locationNotes){
        this(fileName);
        this.locationNotes = locationNotes;
    }
    public MonsterEntityModel(String fileName){
        StatBlockParser parser = new StatBlockParser(fileName);
        parser.parseStatBlock();
        this.AC = parser.getAC();
        this.HPmax = parser.getHP();
        this.HP = this.HPmax;
        this.initiativeBonus = parser.getInitiative();
        this.statBlock = parser.getStatBlock();
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

    public double rollInitiative(){
        if (!this.preRolled){
            D20Test initiativeRoll = new D20Test(this.initiativeBonus);
            return initiativeRoll.roll();
        }else{
            return preRolledInitiative;
        }
    }

    public String useLegendaryResistance(){
        if (this.legendaryResistances > 0){
            this.legendaryResistances -= 1;
            return (this.name+" uses a legendary resistance! It has "+this.legendaryResistances+" remaining.");
        } else {
            return (this.name+" has no legendary resistances remaining!");
        }
    }
}
