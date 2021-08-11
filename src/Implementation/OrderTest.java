package Implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OrderTest {

    static ArrayList<Integer> integerArrayList = new ArrayList<>();


    public static void main(String[] args) {
        integerArrayList.add(2);
        integerArrayList.add(3);
        integerArrayList.add(1);
        integerArrayList.add(4);

        Collections.sort(integerArrayList, new IntegerComparator());

        System.out.println(integerArrayList);
    }

    public static class IntegerComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }
}
