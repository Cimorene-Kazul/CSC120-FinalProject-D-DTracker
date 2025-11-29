import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MonsterFileLoader {
    private String statBlock = "";
    private String name = null;
    private Integer AC = null;
    private Integer HP = null;
    private Integer initiative = null;
    private File statBlockFile;

    public MonsterFileLoader(String fileName){
    }

    public void parseStatBlock(){
        try (Scanner fileReader = new Scanner(this.statBlockFile)){
            int lineNumber = 0;
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                this.statBlock += line +" \n ";
                lineNumber += 1;

                if (lineNumber == 1){
                    this.name = line.trim();
                } else if (lineNumber == 2){
                    this.AC = Integer.parseInt(nextWord(line, line.indexOf("AC")+2));
                    this.HP = Integer.parseInt(nextWord(line, line.indexOf("HP")+2));
                    this.AC = Integer.parseInt(nextWord(line, line.indexOf("Initiative")+11));
                }
            }
        }catch(FileNotFoundException e){
            throw new RuntimeException(this.statBlockFile.getName()+" is not a valid file name in MonsterFiles.");
        }
    }

    public String getName(){
        this.name = statBlock.substring(0, statBlock.indexOf(" \n "));
        return this.name;
    }

    public String getStatBlock(){
        return this.statBlock;
    }

    public int getAC(){
        return this.AC;
    }

    public int getHP(){
        return this.HP;
    }

    public int getInitiative(){
        return this.initiative;
    }

    private static String nextWord(String string, int index){
        String trimmed = string.substring(index).trim()+" ";
        int otherSide = trimmed.indexOf(" ");
        return trimmed.substring(0, otherSide);
    }

    public static void main(String[] args) {
    }
}
