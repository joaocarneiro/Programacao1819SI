import java.util.Scanner;
public class ConvertToDecimal {

	public static void main(String[] args) {
		Scanner kbd = new Scanner (System.in);
		System.out.print("Insira um numero: ");
		long n = kbd.nextInt();
		System.out.print("Insira a base: ");
		int base = kbd.nextInt();
		long copy = n;
		long dig, numBase10=0, pot=1;
		while(copy>0) {
			dig=copy%10;
			copy/=10;
			numBase10 += dig*pot;
			pot*=base;
		}
		System.out.println("O numero "+n+" na base "+base+" corresponde a "+numBase10+" na base 10.");
		kbd.close();
	}

}
