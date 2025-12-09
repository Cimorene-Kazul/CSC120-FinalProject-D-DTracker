public class DiceFormula {
    String value;

    public DiceFormula(){
        this("0");
    }

    public DiceFormula(String formula){
        this.value = formula;
    }

    public int roll(){
        return parseFormula(this.value);
    }

    public static int rollDie(int size){
        return (int)(Math.random()*size + 1);
    }

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

    public static int parseFormula (String formula){
        int result = 0;
        formula = formula.trim();
        try {
            for (String plusChunk: formula.split("+")){
                for (String minusChunk: plusChunk.split("-")){
                    int value = Integer.parseInt(minusChunk);
                    if (plusChunk.startsWith(minusChunk)){
                        result += value;
                    } else {
                        result -= value;
                    }
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(formula+" is not an appropriatly formmated dice formula.");
        }
        
        return result;
    }
}

