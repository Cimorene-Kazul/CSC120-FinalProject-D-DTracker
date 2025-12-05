public class MonsterGroup extends Monster{
    int size;
    Integer individualHP;
    String baseName;
    

    public MonsterGroup(String fileName, int number){
        super(fileName);
        this.subclass = CreatureType.UNIT;
        this.size = number;
        this.HPmax *= number;
        this.HP = this.HPmax;
        this.individualHP = this.HP/number;
        this.baseName = this.name;
        this.name = number + " " + this.baseName;
    }

     public String damage(int amt){
        this.HP = Math.max(this.HP-amt, 0);

        if (this.HP == this.individualHP*(this.HP/this.individualHP)){
            this.size = this.HP/this.individualHP;
        } else {
            this.size = this.HP/this.individualHP + 1;
        }
        this.name = this.size + " " + this.baseName;
        this.HPmax = this.individualHP*this.size;
        return this.name+" has been hit for "+amt+" reducing it to "+this.HP+" hit points and size "+this.size+".";
    }

    public String toString(){
        return super.toString() + " size "+this.size;
    }

    public String turnPrompt(){
        return "It is "+this.name+"'s turn. \n Here is a stat block for one of the creatures composing "+this.name+".\n" + this.statBlock+"\n"+this.name+" is at "+this.HP+" out of "+this.HPmax+" health.";
    }

    public int getSize(){
        return this.size;
    }

    public String saveInfo(){
        return "UNIT \t"+this.baseName+"\t"+this.HP+"\t"+this.size;
    }

    public static MonsterGroup getMonsterGroup(String groupName, int currentHP, int size){
        MonsterGroup m = new MonsterGroup((groupName.replaceAll(" ", "_")).toLowerCase(), size);
        m.damage(m.getHPmax()-currentHP);
        return m;
    }

    public static void main(String[] args) {
    }
}
