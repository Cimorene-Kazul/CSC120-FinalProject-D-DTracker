public class DiceFormula {
    String value;

    /**
     * Blank constructor for DiceFormula
     */
    public DiceFormula(){
        this("0");
    }

    /**
     * Constructor with String parameter
     * @param formula The dice formula as a string
     */
    public DiceFormula(String formula){
        this.value = formula;
    }

    /**
     * Rolls the dice according to the formula
     * @return The result of the dice roll
     */
    public int roll(){
        return parseFormula(this.value);
    }

    /**
     * Rolls a single die of given size
     * @param size The size of the die
     * @return The result of the die roll
     */
    public static int rollDie(int size){
        return (int)(Math.random()*size + 1);
    }

    /**
     * Parses a die string and rolls it
     * @param value The die string (e.g., "2d6" or "5")
     * @return The result of the die roll
     */
    public static int parseDie(String value){
         value = (value.trim().split(" ")[0]).toLowerCase();
         try {
            if (!value.contains("d")){
                return Integer.parseInt(value);
            } else {
                int numberOfDice = Integer.parseInt(value.substring(0,value.indexOf("d")));
                int sizeOfDie = Integer.parseInt(value.substring(value.indexOf("d")+1));
                int result = 0;
                for (int i = 0; i< numberOfDice; i++){
                    result += rollDie(sizeOfDie);
                }
                return result;
            } 
         } catch (RuntimeException e) {
            throw new RuntimeException(value+" is not an integer or a dice value of the form ndm, where n and m are integers.");
         }
     }

     /**
      * Parses a full dice formula and rolls it
      * @param formula The full dice formula (e.g., "2d6+3-1d4")
      * @return The result of the dice roll
      */
    public static int parseFormula (String formula){
        int result = 0;
        formula = formula.trim();
        try {
            for (String plusChunk: formula.split("\\+")){
                for (String minusChunk: plusChunk.split("\\-")){
                    int value = DiceFormula.parseDie(minusChunk);
                    if (plusChunk.startsWith(minusChunk)){
                        result += value;
                    } else {
                        result -= value;
                    }
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(formula+" is not an appropriatly formated dice formula.");
        }
        
        return result;
    }
}

