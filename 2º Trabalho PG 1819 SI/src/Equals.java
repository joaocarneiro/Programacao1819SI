import java.util.Scanner;
import java.util.ArrayList;

public class Equals {
    public static void main(String[] args) {
        Scanner kbd = new Scanner (System.in);
        String [] a = new String [2];
        String [] b = new String [2];
        String number="";
        boolean aFull = false;
        boolean aOrB = true;
        boolean isNeg = false;
        int l=0;

        while(kbd.hasNextInt()){
            number=kbd.next();

            if(aOrB)
                a[l] = number;
            else
                b[l] = number;

            if(a[a.length-1]!=null && aOrB)
                aFull = true;
            if(b[b.length-1]!=null && !aOrB)
                aFull = false;

            if(aFull && aOrB){
                b=new String[a.length*2];
                for(int i=0;i<a.length;++i)
                    b[i]=a[i];
                aOrB = false;
            }
            else if(!aFull && !aOrB){
                a=new String[b.length*2];
                for(int i=0;i<b.length;++i)
                    a[i]=b[i];
                aOrB = true;
            }
            l++;
        }

        if(aOrB) {
            for(int i=0;i<a.length;++i) {
                if(a[i]!=null) {
                    System.out.println(a[i]);
                }
            }
        }
        else if(!aOrB) {
            for(int j=0;j<b.length;++j) {
                if(b[j]!=null) {
                    System.out.println(b[j]);
			    }
			}
		}
        
        



//        ArrayList<Integer> list = new ArrayList<>();
//        boolean equal = false;
//        int eq = 0;
//
//        do {
//            list.add(kbd.nextInt());
//        } while (kbd.hasNextInt());
//
//        for (int i = 0; i < list.size(); ) {
//            for (int j = i + 1; j < list.size(); ) {
//                if (list.get(i) == list.get(j)) {
//                    equal = true;
//                    eq++;
//                    list.remove(j);
//                } else
//                    j++;
//            }
//            if (equal)
//                eq++;
//            list.remove(i);
//            equal = false;
//        }
//        System.out.println("Iguais: " + eq);
    }
}
