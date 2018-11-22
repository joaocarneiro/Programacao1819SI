
public class ArrayUtils {
	
	public static void main (String[]args) {
		int[]arr= {1,2,4,2,1,2};
		int val = 1;
		int newVal = 3;
		System.out.print("Replace the value "+val+" with the value "+newVal+" in the array ");
		print(arr);
		System.out.print(": ");
		replace(arr,1,3);
		System.out.println();
		System.out.println("Min = "+findMaxMin(arr)[0]);
		System.out.println("Max = "+findMaxMin(arr)[1]);
		System.out.print("Rotate ");
		replace(arr,1,3);
		System.out.print(" to the left: ");
		rotateLeft(arr);
	}
	
	//RODA O ARRAY PARA A ESQUERDA 
	public static void rotateLeft (int[]arr) {
		int val = arr[0];
		for(int i=1;i<arr.length;++i) {
			arr[i-1]=arr[i];
		}
		arr[arr.length-1]=val;
		print(arr);
	}
	
	//SUBSTITUIR NO ARRAY TODAS AS OCCORRENCIA DE VAL POR NEWVAL
	public static void replace(int[]arr, int val, int newVal) {
		for(int i = 0; i<arr.length;++i) {
			if(arr[i]==val)
				arr[i]=newVal;
		}
		print(arr);
	}
	
	//ENCONTRAR MAIOR E MENOR
	public static int[] findMaxMin (int[]arr) {
		int [] maxMin = new int[2];
		maxMin[0] = arr[0];
		maxMin[1] = arr[0];
		for(int i = 1; i<arr.length;++i) {
			if(arr[i]>maxMin[1])
				maxMin[1] = arr[i];
			if(arr[i]<maxMin[0])
				maxMin[0] = arr[i];
		}
		return maxMin;
	}
	
	public static void print(int [] arr) {
		for(int arrIndex : arr)
			System.out.print(arrIndex);
	}
	
//	public static int[][] createTriangle (int h){
//		int[][]triangle;
//
//		return triangle;
//	}
}
