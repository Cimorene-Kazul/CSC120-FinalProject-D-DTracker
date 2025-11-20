import java.util.ArrayList;

public class TextReader {

    public String[] StartingActions = {"heal", "damage", "bonus action", "reaction", "legendary action", "legendary resistance", "action"};
    private String[] actions = {"end turn", "summary"};

    public static ArrayList<String> getWords(String input){
        String[] words = input.split(" ");
        ArrayList<String> wordList = new ArrayList<String>();
        for(String word : words){
            wordList.add(word);
        }
        return wordList;
    }

    public String[] getActions(){
        return this.actions;
    }

    public void doActions(String input){
        for (int i = 0; i < actions.length; i++){
            if (input.startsWith(actions[i])){
                if (actions[i].equals("end turn")){
                    // call end turn method
                }
                else if (actions[i].equals("summary")){
                    // call summary method;
                }
            }
        }
        for (int j = 0; j < StartingActions.length; j++){
            if (input.startsWith(StartingActions[j])){
                if (StartingActions[j].equals("heal")){
                    String thingToBeHealed = input.substring(5).trim();
                    // call heal method on thingToBeHealed
                }
                else if (StartingActions[j].equals("damage")){
                    String thingToBeDamaged = input.substring(6).trim();
                    // call damage method on thingToBeDamaged
                }
                else if (StartingActions[j].equals("bonus action")){
                    String bonusActionName = input.substring(13).trim();
                    // call bonus action method on the name, or call this method again on the name
                }
                else if (StartingActions[j].equals("reaction")){
                    // call reaction method
                }
                else if (StartingActions[j].equals("legendary action")){
                    // call legendary action method
                }
                else if (StartingActions[j].equals("legendary resistance")){
                    // call legendary resistance method
                }
                else if (StartingActions[j].equals("action")){
                    // call action method
                }
            }
        }
    }
    
}
