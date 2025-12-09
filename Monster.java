import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Monster extends Creature{
    private Integer AC;
    protected Integer HPmax;
    protected Integer HP;
    private Integer initiativeBonus;
    protected String statBlock;
    protected String notes = "";
    protected String originNotes = "";
    protected String fileOrigin = null;

    public Monster(String fileName, String originNotes){
        this(fileName);
        this.originNotes = originNotes; 
    }

    public Monster(String fileName){
        this.subclass = CreatureType.MONSTER;
        this.fileOrigin = fileName.trim();
        File statBlockFile = new File("MonsterFiles/"+fileName.trim()+".txt");
        try (Scanner fileReader = new Scanner(statBlockFile)){
            int lineNumber = 0;
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
            }
            if (this.statBlock.toLowerCase().contains("lair action")){
                this.hasLair = true;
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

    public int getInitiative(){
        return this.initiativeBonus;
    }

    public Hashtable<String, Interaction> getTraits(){
        return this.traits;
    }

    public Hashtable<String, Interaction> getActions(){
        return this.actions;
    }

    public Hashtable<String, Interaction> getBonusActions(){
        return this.bonusActions;
    }

    public Hashtable<String, Interaction> getLegendaryActions(){
        return this.legendaryActions;
    }

    public Hashtable<String, Interaction> getReactions(){
        return this.reactions;
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
        if (this.notes.trim() == ""){
            this.notes = note;
        } else {
            this.notes += " \n "+ note;
        }
        return "Notes have been saved.";
    }
    public String toString(){
        String result = name+" at "+HP+"/"+HPmax+" HP, AC "+AC;
        if (this.originNotes.trim() != ""){
            result += " ("+this.originNotes.replaceAll("\n", "\t")+") ";
        }
        if (this.notes.trim() != ""){
            result += " - "+this.notes.replaceAll("\n", "\t");
        }
        return result;
    }

    public String turnPrompt(){
        String result = "It is "+this.name+"'s turn.";
        if (this.originNotes.trim()!= ""){
            result += "\n "+this.name+" is distinguished by "+this.originNotes;
        }
        result += "\n Here is a stat block for "+this.name+".\n" + this.statBlock+"\n"+this.name+" is at "+this.HP+" out of "+this.HPmax+" health.";
        if (this.notes.trim() != ""){
            result += " \n Additional notes for "+this.name+": \n "+this.notes;
        }
        return result;
    }

    public int rollInitiative(Scanner initScanner){
        D20Test initiativeRoll = new D20Test(this.initiativeBonus);
        return initiativeRoll.roll();
    }

    public static void saveMonster(Monster m){
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
                    monsterWriter.write(m.getName()+"\n");
                    monsterWriter.write("AC "+m.getAC()+" \t HP "+m.getHPmax()+" \t Initiative "+m.getInitiative()+"\n");
                }
                monsterWriter.write(monsterStats);
                monsterWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(fileName+".txt had some issue. An IOException occured with message "+e.getMessage());
            }
        }
    }

    public static Monster parseMonster(String saveInfo){
        String[] pieces = saveInfo.split("<<<SPACING MARKER>>>");
        Monster m = new Monster(pieces[1].trim());
        m.damage(m.getHPmax()-Integer.parseInt(pieces[2].trim()));
        m.originNotes = pieces[3].trim();
        m.takeNote(pieces[4].trim());
        return m;
    }

    public String saveInfo(){
        if (this.fileOrigin == null){
            Monster.saveMonster(this);
            this.fileOrigin = (this.name.trim().replaceAll(" ", "_")).toLowerCase();
        }
        return "MONSTER <<<SPACING MARKER>>>"+this.fileOrigin+"<<<SPACING MARKER>>>"+this.HP+"<<<SPACING MARKER>>>"+this.originNotes.replaceAll("\n", " ")+"<<<SPACING MARKER>>>"+this.notes.replaceAll("\n", " ");
    }

    public static void main(String[] args) {
    }
}