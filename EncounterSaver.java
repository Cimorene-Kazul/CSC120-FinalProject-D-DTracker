import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
public class EncounterSaver {

    public static void saveEncounter(InitiativeTracker tracker, String fileName){
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
        Files.write(Paths.get(fileName+".txt"), encounter.getBytes(), StandardOpenOption.CREATE);
    }

    
    public static void saveMonster(Monster monster, String fileName){
        String monsterInfo = monster.getName() + "\n";
        // Add monster info to MonsterInfo string
        Files.write(Paths.get("Monsters/"+fileName+".txt"), data.getBytes(), StandardOpenOption.CREATE);
    }
}
