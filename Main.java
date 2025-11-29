import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        EncounterBuilder myEncounterBuilder = new EncounterBuilder();
        myEncounterBuilder.buildEncounter();

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("encounter.json"), myEncounterBuilder);
            System.out.println("Encounter saved to encounter.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
