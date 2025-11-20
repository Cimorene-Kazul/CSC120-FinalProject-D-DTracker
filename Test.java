import java.util.Hashtable;
import java.util.ArrayList;

// test built in methods in this file
public class Test {
    public static void main(String[] args) {
        // testing modifying elements directly in hashtable
        Hashtable<Integer, ArrayList<Integer>> testTable = new Hashtable<>();
        ArrayList<Integer> testList = new ArrayList<>();
        testList.add(12);
        testTable.put(1, testList);
        testTable.get(1).add(2);
        for (Integer i: testTable.get(1)){
            System.out.println(i);
        }

        
    }
}
