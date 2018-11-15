import java.util.Scanner;
public class Equals {
    public static void main(String[] args) {
        Scanner kbd = new Scanner (System.in);
        String [] a = new String [2], b;
        boolean aFull = false, equal = false;
        int eq=0, l=0;
        
        while(kbd.hasNextInt()){
        	a[l]=kbd.next();
            if(a[a.length-1]!=null) aFull = true;
            if(aFull){
                b=new String [a.length];
                for(int i=0;i<a.length;++i) b[i]=a[i];
                a=new String [b.length*2];
                for(int i=0;i<b.length;++i) a[i]=b[i];
            } l++;
        }
    	for(int i=0;i<a.length;++i) {
    		for(int j=i+1;j<a.length;++j) {
    			if(a[i]!=null) {
    				if(a[i].equals(a[j]) && a[i]!=" ") {
        				equal=true;
        				eq++;
        				a[j]=" ";
        			}
    			}
    			else break;
    		}
    		if(equal) eq++;
    		equal=false;
    	}
		System.out.println(eq);
		kbd.close();
    }
}
