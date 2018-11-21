import java.util.Scanner;
import java.util.ArrayList;

public class EqualsArrayList {
    public static void main(String[] args) {
        Scanner kbd = new Scanner (System.in);
        ArrayList<Integer> list = new ArrayList<>();
        boolean equal = false;
        int eq = 0;

        do {
            list.add(kbd.nextInt());
        } while (kbd.hasNextInt());

        for (int i = 0; i < list.size(); ) {
            for (int j = i + 1; j < list.size(); ) {
                if (list.get(i) == list.get(j)) {
                    equal = true;
                    eq++;
                    list.remove(j);
                } else
                    j++;
            }
            if (equal)
                eq++;
            list.remove(i);
            equal = false;
        }
        System.out.println("Iguais: " + eq);
    }
}
