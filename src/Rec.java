public class Rec {

    public static void main (String[]args){
        int f = fatorial((4));
        System.out.println(f);
    }

    public static int fatorial (int n){
        if(n==0||n==1)
            return 1;
        return n*fatorial(n-1);
    }
}
