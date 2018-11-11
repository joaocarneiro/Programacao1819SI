public class Ascii {
    public static void main (String[]args){
        System.out.println("ASCII - American Standard Code for Information Interchange");
        System.out.println(" Decimal || Sinal ");
        System.out.println("---------||-------");

        //ASCII TABLE USING FOR LOOP

//        for(int i=0;i<=126;i++){
//            if(i<10) System.out.println(" "+i+"       || "+(char)i+" ");
//            else if(i<100) System.out.println(" "+i+"      || "+(char)i+" ");
//            else System.out.println(" "+i+"     || "+(char)i+" ");
//        }

        //ASCII TABLE USING WHILE LOOP
        int i=0;
        while(i<=127){
            if(i<10) System.out.println(" "+i+"       || "+(char)i+" ");
            else if(i<100) System.out.println(" "+i+"      || "+(char)i+" ");
            else System.out.println(" "+i+"     || "+(char)i+" ");
            ++i;
        }
    }
}
