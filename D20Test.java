public class D20Test extends DiceFormula{
    /**
     * Constructor for D20Test with string bonus
     * @param bonus The bonus to be added to the d20 roll
     */
    public D20Test(String bonus){
        this(Integer.parseInt(bonus));
    }
    
    /**
     * Constructor of D20Test with int bonus
     * @param bonus the bonus to be added to the d20 roll
     */
    public D20Test(int bonus){
        this.value = "1d20";
        if (bonus < 0){
            this.value += bonus;
        } else {
            this.value += "+"+bonus;
        }
    }
}
