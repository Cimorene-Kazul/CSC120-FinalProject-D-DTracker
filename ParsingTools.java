public class ParsingTools {
    public static String nextWord(String string, int index){
        String trimmed = string.substring(index).trim()+" ";
        int otherSide = trimmed.indexOf(" ");
        return trimmed.substring(0, otherSide);
    }
}
