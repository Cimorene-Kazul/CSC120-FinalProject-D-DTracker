// test built in methods in this file

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> a1 = new ArrayList<>();
        a1.add(2);
        a1.add(4);
        ArrayList<Integer> a2 = a1;
        a2.add(23);
        for (int i: a1){
            System.out.println(i);
        }
    }
}
