public class Grupo1Exer1 {
    public static void main (String[]args){
        System.out.println("= "+f(36847));
        System.out.println();
        System.out.println("= "+f1(36847));
    }

    public static int f(int n){
        if(n==0) return 0;
        int p = (n%10)%2 == 0 ? 1:0;
        System.out.print(p+".");
        return p+f(n/10);
    }

    public static int f1(int n){
        int p, res=0;
        for(;n>0;){
            p = (n%10)%2 == 0 ? 1:0;
            System.out.print(p+".");
            n/=10;
            res+=p;
        }
        return res;
    }
}
