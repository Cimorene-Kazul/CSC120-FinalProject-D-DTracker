import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MonsterGroup extends Monster{
    int size;
    Integer individualHP;
    String baseName;
    

    public MonsterGroup(String fileName, int number){
        super(fileName);
        if (this.size<=0){
            throw new RuntimeException("Units must have positive integer size");
        }
        this.subclass = CreatureType.UNIT;
        this.size = number;
        this.HPmax *= number;
        this.HP = this.HPmax;
        this.individualHP = this.HP/number;
        this.baseName = this.name;
        this.name = number + " " + this.baseName;
    }


    public MonsterGroup(String fileName, int number, String originNotes){
        this(fileName, number);
        this.originNotes = originNotes; 
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

    private int getIndividualHP(){
        return this.individualHP;
    }

    private String getOriginalName(){
        return this.baseName;
    }

    public String turnPrompt(){
        String result = "It is "+this.name+"'s turn.";
        if (this.originNotes.trim()!= ""){
            result += "\n "+this.name+" is distinguished by "+this.originNotes;
        }
        result += "\n Here is a stat block for one of the creatures composing "+this.name+".\n" + this.statBlock+"\n"+this.name+" is at "+this.HP+" out of "+this.HPmax+" health.";
        if (this.notes.trim() != ""){
            result += " \n Additional notes for "+this.name+": \n "+this.notes;
        }
        return result;
    }

    public int getSize(){
        return this.size;
    }

    public String saveInfo(){
        if (this.fileOrigin == null){
            MonsterGroup.saveMonster(this);
            this.fileOrigin = (this.name.trim().replaceAll(" ", "_")).toLowerCase();
        }
        return "UNIT <<<SPACING MARKER>>>"+this.fileOrigin+"<<<SPACING MARKER>>>"+this.HP+"<<<SPACING MARKER>>>"+this.size+"<<<SPACING MARKER>>>  "+this.originNotes.replaceAll("\n", " ")+"<<<SPACING MARKER>>>"+this.notes.replaceAll("\n", " ");
    }

    public static void saveMonster(MonsterGroup m){
        String fileName = (m.getName().trim().replaceAll(" ", "_")).toLowerCase();
        File monsterFile = new File("MonsterFiles/"+fileName+".txt");
        if (!monsterFile.exists()){
            try {
                FileWriter monsterWriter = new FileWriter(monsterFile);
                String monsterStats = m.getStats();
                String keyLine = monsterStats.split("\n")[1];
                try {
                    Integer.parseInt(ParsingTools.nextWord(keyLine, keyLine.indexOf("AC")+2));
                    Integer.parseInt(ParsingTools.nextWord(keyLine, keyLine.indexOf("HP")+2));
                    Integer.parseInt(ParsingTools.nextWord(keyLine, keyLine.indexOf("Initiative")+11));
                } catch (Exception e) {
                    monsterWriter.write(m.getOriginalName()+"\n");
                    monsterWriter.write("AC "+m.getAC()+" \t HP "+m.getIndividualHP()+" \t Initiative "+m.getInitiative()+"\n");
                }
                monsterWriter.write(monsterStats);
                monsterWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(fileName+".txt had some issue. An IOException occured with message "+e.getMessage());
            }
        }
    }

    public static MonsterGroup parseMonster(String saveInfo){
        String[] pieces = saveInfo.split("<<<SPACING MARKER>>>");
        MonsterGroup m = new MonsterGroup(pieces[1].trim(), Integer.parseInt(pieces[3].trim()));
        m.damage(m.getHPmax()-Integer.parseInt(pieces[2].trim()));
        m.originNotes = pieces[4].trim();
        m.takeNote(pieces[5].trim());
        return m;
    }

    public static void main(String[] args) {
    }
}
