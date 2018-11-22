public class MyInteger {

	public static void main(String[] args) {
		System.out.println(parseInt("12345a"));
	}

	public static int parseInt(String number) {
		int a = 0;
		int pow = 1;
		for (int i = number.length() - 1; i >= 0; --i) {
			char dig = number.charAt(i);
			if (dig >= '0' && dig <= '9') {
				a = a + (dig - '0') * pow;
				pow *= 10;
			} else {
				System.out.println("A string inserida nao e um numero");
				return -1;
			}
		}
		return a;
	}
}
