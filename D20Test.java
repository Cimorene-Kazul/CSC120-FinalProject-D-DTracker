public class D20Test extends DiceFormula{
    public D20Test(String bonus){
        this(Integer.parseInt(bonus));
    }
    
    public D20Test(int bonus){
        this.value = "1d20";
        if (bonus < 0){
            this.value += bonus;
        } else {
            this.value += "+"+bonus;
        }
    }
}
