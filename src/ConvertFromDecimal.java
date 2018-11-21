import java.util.Scanner;
public class ConvertFromDecimal {

	public static void main(String[] args) {
		Scanner kbd = new Scanner (System.in);
		System.out.print("Insira um numero na base 10: ");
		int n = kbd.nextInt();
		System.out.print("Insira a base de conversao: ");
		int base = kbd.nextInt();
		int copy = n;
		int dig, i=0;
		String numBase="";
		while(copy>0) {
			dig=copy%base;
			copy/=base;
			if(dig>=10) {
				char digChar = (char) ('A'+dig-10);
				String digString = digChar+"";
				numBase = digString+""+numBase;
			}
			else {
				numBase = dig+""+numBase;
			}
		}
		System.out.println("O numero "+n+" na base 10, corresponde a "+numBase+" na base "+base);
	}

}
