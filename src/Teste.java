import java.util.Scanner;
public class Teste {
    public static void main(String[]args){
        Scanner kbd = new Scanner (System.in);
        String [] a = new String [2];
        String [] b = new String [2];
        String number="";
        boolean aFull = false;
        boolean aOrB = true;
        boolean isNeg = false;

        int k = 0;
        int num = 0;
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

        if(aOrB){
            for(int i=0;i<a.length;++i){
                if(a[i]!=null)
                    System.out.println(a[i]);
            }
        }
        else if(!aOrB)
            for(int i=0;i<b.length;++i)
                if(b[i]!=null)
                    System.out.println(b[i]);

//        //Check for negative sign; if it's there, set the isNeg flag
//        if (number.charAt(0) == '-') {
//            isNeg = true;
//            k = 1;
//        }
//
//        //Process each character of the string;
//        while( k < number.length()) {
//            num *= 10;
//            num += number.charAt(k++) - '0'; //Minus the ASCII code of '0' to get the value of the charAt(i++).
//        }
//
//        if (isNeg)
//            num = -num;



//        char ch = 'c';
//
//        //1ª opção
//        int dist = ch - 'a';
//        char chM = (char)('A'+dist);
//
//        //2ª opção
//        char chM2 = (char)(ch-'a'+'A');
//
//        String str = "36847";
//        int i1 = str. charAt (0) % 128 + str. charAt (1) % 128 + str. charAt (2) % 128 + str.
//                charAt (3) % 128 + str. charAt (4) % 128;
//
//        System.out.println(i1);
//        System.out.println (str. charAt (3));
//        System.out.println (( int) str. charAt (3));
//        System.out.println (( int)( str. charAt (3) - '0'));
//        System.out.println (str. charAt (3) - '0');
//        System.out.println ("a" + str);
//        System.out.println (( char )( ("a" + str). charAt (0) - 'a' + 'A'));
//        System.out.println (( ("a" + str). charAt (0) - 'a' + 'A'));
//
//        System.out.println(dist);
//        System.out.println(chM);
//        System.out.println(chM2);
    }
}
