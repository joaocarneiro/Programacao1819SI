import java.util.Scanner;
public class Teste {
    public static void main(String[]args){

        char ch = 'c';

        //1ª opção
        int dist = ch - 'a';
        char chM = (char)('A'+dist);

        //2ª opção
        char chM2 = (char)(ch-'a'+'A');

        String str = "36847";
        int i1 = str. charAt (0) % 128 + str. charAt (1) % 128 + str. charAt (2) % 128 + str.
                charAt (3) % 128 + str. charAt (4) % 128;

        System.out.println(i1);
        System.out.println (str. charAt (3));
        System.out.println (( int) str. charAt (3));
        System.out.println (( int)( str. charAt (3) - '0'));
        System.out.println (str. charAt (3) - '0');
        System.out.println ("a" + str);
        System.out.println (( char )( ("a" + str). charAt (0) - 'a' + 'A'));
        System.out.println (( ("a" + str). charAt (0) - 'a' + 'A'));

        System.out.println(dist);
        System.out.println(chM);
        System.out.println(chM2);
    }
}
