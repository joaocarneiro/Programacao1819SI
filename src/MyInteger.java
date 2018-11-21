public class MyInteger {

//	public static void main(String[] args) {
//		System.out.println(parseInt("12345"));
//	}

	public static int parseInt(String number) {
		String newNum = number;
		int a = 0;
		int pow = 1;
		for (int i = newNum.length() - 1; i >= 0; --i) {
			char dig = number.charAt(i);
			if (dig >= '0' && dig <= '9') {
				a = a + (dig - '0') * pow;
				pow *= 10;
			} else {
				System.out.println("A string inserida não é um numero");
				return -1;
			}
		}
		return a;
	}
}
