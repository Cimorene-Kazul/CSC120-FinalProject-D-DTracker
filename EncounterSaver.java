import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.BufferedWriter; // Import this class to write to a file
import java.util.Hashtable;

public class EncounterSaver {

    public void saveEncounter(InitiativeTracker tracker, String fileName) throws IOException {
        String encounter = fileName + ": \nMONSTERS: \n";
        for (int i = 0; i < tracker.creatures.size(); i++){
            Creature c = tracker.creatures.get(i);
            if (c.getType().equals(CreatureType.MONSTER)){
                saveMonster((Monster)c, ((Monster)c).getName());
                encounter += c.getName() + "\n";
            }
        }
        encounter += "\n PLAYERS: \n";
        for (int i = 0; i < tracker.creatures.size(); i++){
            Creature c = tracker.creatures.get(i);
            if (c.getType().equals(CreatureType.PLAYER)){
                encounter += c.getName() + "\n";
            }
        }
        encounter += "\nINITIATIVE ORDER: \n";
        encounter += tracker.getSummary();
        encounter += "\nCURRENT INITIATIVE: " + tracker.currentInitiative + "\n";
        
    }
    
    public static void saveMonster(Monster monster, String fileName) throws IOException {
        String monsterInfo = monster.getName() + "\n";
        monsterInfo += "AC: " + monster.getAC() + "\t HP: " + monster.getHPmax() + "\tInitiative +" + monster.getInitiative() + "\n";
        monsterInfo += monster.getStats(); // I think the stuff between AC and Traits is the stat block??
        if (!monster.getTraits().isEmpty()){
            Hashtable<String, Interaction> traits = monster.getTraits();
            monsterInfo += "\nTraits\n";
            for (String key : monster.getTraits().keySet()){
                monsterInfo += key;
                if (traits.get(key).hasUses()){
                    monsterInfo += "(" + traits.get(key).getUses() + "/day) ";
                }
                monsterInfo += "- " + traits.get(key).getDescription();
            }
        }
        if (!monster.getActions().isEmpty()){
            Hashtable<String, Interaction> actions = monster.getActions();
            monsterInfo += "\n";
            for (String key : monster.getActions().keySet()){
                monsterInfo += key;
                if (actions.get(key).hasUses()){
                    monsterInfo += "(" + actions.get(key).getUses() + "/day) ";
                }
                monsterInfo += "- " + actions.get(key).getDescription();
            }
        }
        if (!monster.getBonusActions().isEmpty()){
            Hashtable<String, Interaction> bonusActions = monster.getBonusActions();
            monsterInfo += "\n";
            for (String key : monster.getBonusActions().keySet()){
                monsterInfo += key;
                if (bonusActions.get(key).hasUses()){
                    monsterInfo += "(" + bonusActions.get(key).getUses() + "/day) ";
                }
                monsterInfo += "- " + bonusActions.get(key).getDescription();
            }
        }
        if (!monster.getLegendaryActions().isEmpty()){
            Hashtable<String, Interaction> legendaryActions = monster.getLegendaryActions();
            monsterInfo += "\n";
            for (String key : monster.getLegendaryActions().keySet()){
                monsterInfo += key;
                if (legendaryActions.get(key).hasUses()){
                    monsterInfo += "(" + legendaryActions.get(key).getUses() + "/day) ";
                }
                monsterInfo += "- " + legendaryActions.get(key).getDescription();
            }
        }
        if (!monster.getReactions().isEmpty()){
            Hashtable<String, Interaction> reactions = monster.getReactions();
            monsterInfo += "\n";
            for (String key : monster.getReactions().keySet()){
                monsterInfo += key;
                if (reactions.get(key).hasUses()){
                    monsterInfo += "(" + reactions.get(key).getUses() + "/day) ";
                }
                monsterInfo += "- " + reactions.get(key).getDescription();
            }
        }
        Files.write(Paths.get("Monsters/"+fileName+".txt"), monsterInfo.getBytes(), StandardOpenOption.CREATE);
    }
}
