import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class EncounterView { 
    InputStream in;
    PrintStream out;
    Scanner input;
    String commandOptions = """
        summary - prints a concise summary of characters in combat, with their indicies, in initiative order
        end turn - goes on to the next turn
        close - closes the whole program
        roll <dice formula> - rolls and prints the result of <dice formula> (ie, 3d6 rolls 3 6-sided dice and adds the results)
        damage <index> <amt> - damages the creature at index <index> for amount <amount>
        heal <index> <amt> - heals the creature at index <index> for amount <amount>
        take note <index> - adds a note to the creature at index <index> (no index means the creature at the current initative)
        stats <index> - prints stat block of creature <index>
        """;

    public EncounterView(){
        this(System.in, System.out);
    }
    public EncounterView(InputStream in, PrintStream out){
        this.in = in;
        this.out = out;
    }

    public void open(){
        this.input = new Scanner(this.in);
    }
    public void close(){
        this.input.close();
    }
    public void print(String message){
        this.out.println(message);
    }
    public String nextLine(){
        return this.input.nextLine();
    }
    public int nextInt(){
        return this.input.nextInt();
    }

    public void doNextTask(){
        String command = this.input.nextLine().trim();
        try{
            if (command.startsWith("heal")){
                String[] commandPieces = command.trim().split(" ");
                Integer index = Integer.parseInt(commandPieces[1]);
                Integer amt = Integer.parseInt(commandPieces[2]);
                this.out.println(this.encounter.heal(index, amt));
            } else if (command.startsWith("damage")){
                String[] commandPieces = command.trim().split(" ");
                Integer index = Integer.parseInt(commandPieces[1]);
                Integer amt = Integer.parseInt(commandPieces[2]);
                this.out.println(this.encounter.damage(index, amt));
            } else if (command.startsWith("take note")) {
                this.out.println("What do you want to note?");
                String note = input.nextLine();
                if (command.substring(9).trim() != ""){
                    Integer index = Integer.parseInt(command.substring(9).trim());
                    this.encounter.takeNote(note, index);
                } else {
                    this.encounter.takeNote(note);
                }
            } else if (command.startsWith("roll")){
                this.out.println(DiceFormula.parseFormula(command.substring(4)));
            } else if (command.startsWith("options")){
                this.out.println(commandOptions);
            } else if (command.startsWith("end turn")){
                this.out.println(encounter.nextTurn());
            } else if (command.startsWith("summary")){
                this.encounter.summary();
            } else if (command.startsWith("close")){
                this.encounter.endCombat();
            }
        } catch (RuntimeException e){
            System.out.println("Something went wrong. Perhaps you formatted your command incorrectly or tried damage a creature that is not in the encounter. Please try again.");
        }
    }
}
