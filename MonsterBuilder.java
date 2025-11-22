import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MonsterBuilder {
    private String statBlock = "";
    private String name = null;
    private Integer AC = null;
    private Integer HP = null;
    private Integer initative = null;
    private String keyLine = null;

    public MonsterBuilder(String fileName){
        File monsterFile = new File("MonsterFiles/"+fileName+".txt");
        try (Scanner monsterReader = new Scanner(monsterFile)){
            while (monsterReader.hasNextLine()) {
                this.statBlock += monsterReader.nextLine() +" \n ";
            }
        }catch(FileNotFoundException e){
            throw new RuntimeException(fileName+" is not a valid file name in MonsterFiles.");
        }
    }

    public String getName(){
        if (this.name == null){
            int firstBreak = statBlock.indexOf(" \n ");
            this.name = statBlock.substring(0, firstBreak);
        }
        return this.name;
    }

    public String getStatBlock(){
        return this.statBlock;
    }

    private String getKeyLine(){
        if (this.keyLine == null){
            int firstBreak = statBlock.indexOf(" \n ");
            int secondBreak = statBlock.indexOf(" \n ", firstBreak+3);
            this.keyLine = statBlock.substring(firstBreak+3, secondBreak);
        }
        return this.keyLine;

    }

    public int getAC(){
        if (this.AC == null){
            this.AC = Integer.parseInt(nextWord(this.getKeyLine(), this.getKeyLine().indexOf("AC")+2));
        }
        return this.AC;
    }

    public int getHP(){
        if (this.HP == null){
            this.HP = Integer.parseInt(nextWord(this.getKeyLine(), this.getKeyLine().indexOf("HP")+2));
        }
        return this.HP;
    }

    public int getInitative(){
        if (this.initative == null){
            this.initative = Integer.parseInt(nextWord(this.getKeyLine(), this.getKeyLine().indexOf("Initative")+9));
        }
        return this.initative;
    }

    private static String nextWord(String string, int index){
        String trimmed = string.substring(index,string.length()).trim();
        int otherSide = trimmed.indexOf(" ");
        return trimmed.substring(0, otherSide);
    }

    public static void main(String[] args) {
        MonsterBuilder test = new MonsterBuilder("ancient_green_dragon");
        System.out.println(test.getAC());
    }
}
