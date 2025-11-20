import java.util.ArrayList;

public class TextReader {

    public ArrayList<String> actions;

    public TextReader(ArrayList<String> actions){
        this.actions = actions;
    }

    public static ArrayList<String> getWords(String input){
        String[] words = input.split(" ");
        ArrayList<String> wordList = new ArrayList<String>();
        for(String word : words){
            wordList.add(word);
        }
        return wordList;
    }

    public ArrayList<String> getActions(){
        return this.actions;
    }

    public void doActions(String input){
        ArrayList<String> words = getWords(input);
        for (String word : words) {
            if (actions.contains(word)) {
                
            }
        }
    }
    
}
