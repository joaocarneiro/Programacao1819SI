/*
RECURSIVIDADE

Uma função recursiva é uma função que se chama a ela propria, fazendo um processamento equivalente a um ciclo.
Contudo, as chamadas recursivas são eficientes em termos de espaço ocupado relativamente a um ciclo.
Certos programas ficam mais simples de implementar com recursividade quando comparado com um ciclo
(Ex.: Algoritmo quick sort recursivo VS iterativo)
 */

import java.util.Scanner;
public class Factorial {

	public static void main(String[] args) {
		Scanner kbd = new Scanner (System.in);
		System.out.print("Insira um valor: ");
		System.out.println(factorial(kbd.nextInt()));
	}
	
	public static int factorial (int n) {
       if(n==0 || n==1) return 1;
       return factorial(n-1) * n;
	}
}
