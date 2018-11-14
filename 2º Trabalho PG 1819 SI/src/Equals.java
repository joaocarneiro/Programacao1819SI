import java.util.Scanner;

public class Equals {
    public static void main(String[] args) {
        Scanner kbd = new Scanner (System.in);
        String [] a = new String [2];
        String [] b = new String [2];
        String number;
        boolean aFull = false;
        boolean aOrB = true;
        boolean equal = false;
        int eq=0;
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
                b=new String [a.length*2];
                for(int i=0;i<a.length;++i)
                    b[i]=a[i];
                aOrB = false;
            }
            else if(!aFull && !aOrB){
                a=new String [b.length*2];
                for(int i=0;i<b.length;++i)
                    a[i]=b[i];
                aOrB = true;
            }
            l++;
        }
        if(aOrB) {
        	for(int i=0;i<a.length;++i) {
        		for(int j=i+1;j<a.length;++j) {
        			if(a[i]!=null) { 
        				if(a[i].equals(a[j]) && a[i]!=" ") {
	        				equal=true;
	        				eq++;
	        				a[j]=" ";
	        			}
        			}
        			else
        				break;
        		}
        		if(equal)
        			eq++;
        		equal=false;
        	}
        }
        else {
        	for(int i=0;i<b.length;++i) {
        		for(int j=i+1;j<b.length;++j) {
        			if(b[i]!=null) { 
        				if(b[i].equals(b[j]) && b[i]!=" ") {
	        				equal=true;
	        				eq++;
	        				b[j]=" ";
	        			}
        			}
        			else
        				break;
        		}
        		if(equal)
        			eq++;
        		equal=false;
        	}
        }
		System.out.println(eq);
    }
}
