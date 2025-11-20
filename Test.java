import java.util.Hashtable;
import java.util.ArrayList;

// test built in methods in this file
public class Test {
    public static void main(String[] args) {
        Hashtable<Integer, ArrayList<Integer>> testTable = new Hashtable<>();
        ArrayList<Integer> testList = new ArrayList<>();
        testList.add(12);
        testTable.put(1, testList);
        testTable.get(1).add(2);
        for (Integer i: testTable.get(1)){
            System.out.println(i);
        }
        System.out.println(testTable.get(20));
        System.out.println("1 \t 2 \t 3");

        String myString = "heal 1 2";
        String[] pieces = myString.split(" ");
        System.out.println(Integer.parseInt(pieces[1]));
    }
}
