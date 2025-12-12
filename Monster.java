import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Monster class represents a monster creature with stats loaded from a file.
 */
public class Monster extends Creature{
    private Integer AC;
    protected Integer HPmax;
    protected Integer HP;
    private Integer initiativeBonus;
    protected String statBlock = "";
    protected String notes = "";
    protected String originNotes = "";
    protected String fileOrigin = null;

    /**
     * Constructor to create a Monster with specified file name and origin notes.
     * @param fileName The name of the file containing the monster's stats.
     * @param originNotes Notes about the monster's origin.
     */
    public Monster(String fileName, String originNotes){
        this(fileName);
        this.originNotes = originNotes; 
    }

    /**
     * Constructor to create a Monster with specified file name
     * @param fileName The name of the file containing the monster's stats.
     */
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

    /**
     * Constructor to create a Monster with specified attributes.
     * @param name
     * @param health
     * @param AC
     * @param initiativeBonus
     * @param statBlock
     */
    public Monster(String name, int health, int AC, int initiativeBonus, String statBlock){
        this.subclass = CreatureType.MONSTER;
        this.name = name;
        this.HP = health;
        this.HPmax = health;
        this.AC = AC;
        this.initiativeBonus = initiativeBonus;
        this.statBlock = statBlock;
    }

    /**
     * Get the name of the monster.
     * @return The name of the monster.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Get the stats of the monster.
     * @return statBlock, the string representation of the monster's stats
     */
    public String getStats(){
        return this.statBlock;
    }

    /**
     * Get the current HP of the monster
     * @return HP
     */
    public int getHP(){
        return this.HP;
    }

    /**
     * Get the maximum HP of the monster
     * @return HPmax
     */
    public int getHPmax(){
        return this.HPmax;
    }

    /**
     * Get the AC of the monster
     * @return AC
     */
    public int getAC(){
        return this.AC;
    }

    /**
     * Get the initiative bonus of the monster
     * @return initiativeBonus
     */
    public int getInitiative(){
        return this.initiativeBonus;
    }

    /**
     * Apply damage to the monster
     * @param amt of damage
     * @return message about the damage taken and remaining HP
     */
    public String damage(int amt){
        this.HP = Math.max(this.HP-amt, 0);
        return this.name+" has been hit for "+amt+" reducing it to "+this.HP+" hit points.";
    }

    /**
     * Heal the monster
     * @param amt of health
     * @return message about the healing and new HP
     */
    public String heal(int amt){
        this.HP = Math.min(this.HP+amt, this.HPmax);
        return this.name+" has been healed for "+amt+" increasing it to "+this.HP+" hit points.";
    }

    /**
     * Add a note to the monster
     * @param note
     * @return message signifiying the note has been saved
     */
    public String takeNote(String note){
        if (this.notes.trim() == ""){
            this.notes = note;
        } else {
            this.notes += " \n "+ note;
        }
        return "Notes have been saved.";
    }

    public String getNotes(){
        String notes = "";
        if (this.originNotes.trim() != ""){
            notes += "On creation, the following was noted about "+this.name+": "+this.originNotes + ".\n";
        }
        if (this.notes.trim() != ""){
            notes += "During the course of combat, the following was noted about "+this.name+":\n"+this.notes;
        }
        if (this.notes.trim() == "" && this.originNotes.trim() == ""){
            notes = this.name + " has no notes.";
        }
        return notes;
    }

    /**
     * Turns the monster into a String
     * @return The monster's name, current HP out of max HP, AC, and any notes
     */
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

    /**
     * Prompts for the monster's turn
     * @return Prompt for the monster's turn and provides stat block and notes
     */
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

    /**
     * Creates D20Test object to roll dice
     * @param initScanner The Scanner to get user input from
     * @return the result of the dice roll
     */
    public int rollInitiative(Scanner initScanner){
        D20Test initiativeRoll = new D20Test(this.initiativeBonus);
        return initiativeRoll.roll();
    }

    /**
     * Saves a monster to a file
     * @param m The Monster to be saved
     */
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

    /**
     * Takes a String of Monster info and turns it into a Monster
     * @param saveInfo
     * @return the Monster
     */
    public static Monster parseMonster(String saveInfo){
        String[] pieces = saveInfo.split("<<<SPACING MARKER>>>");
        Monster m = new Monster(pieces[1].trim());
        m.damage(m.getHPmax()-Integer.parseInt(pieces[2].trim()));
        m.originNotes = pieces[3].trim();
        m.takeNote(pieces[4].trim());
        return m;
    }

    /**
     * Turns the info of the current Monster into a String
     * @return properly formatted String of Monster info
     */
    public String saveInfo(){
        if (this.fileOrigin == null){
            Monster.saveMonster(this);
            this.fileOrigin = (this.name.trim().replaceAll(" ", "_")).toLowerCase();
        }
        return "MONSTER <<<SPACING MARKER>>>"+this.fileOrigin+"<<<SPACING MARKER>>>"+this.HP+"<<<SPACING MARKER>>>"+this.originNotes.replaceAll("\n", " ")+"<<<SPACING MARKER>>>"+this.notes.replaceAll("\n", " ");
    }
}