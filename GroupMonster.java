public class GroupMonster extends Monster{
    int size;
    Integer individualHP;
    String baseName;

    public GroupMonster(String fileName, int number){
        super(fileName);
        this.size = number;
        this.individualHP = Math.max(this.HP,1);
        this.HP = this.HP*number;
        this.HPmax = this.HP*number;
        this.baseName = this.name;
        this.name = number + " " + this.baseName;
    }

     public String damage(int amt){
        this.HP = Math.min(this.HP-amt, 0);

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
}
