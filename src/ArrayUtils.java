
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
		System.out.println();
		int[][]matrixA = {{1,2,3,4},{5,6,7,8}};
		int nLines = matrixA.length; //número de linhas
		int nCols = matrixA[0].length; //número de colunas
		for (int i=0;i<matrixA.length;++i)
			System.out.print(getColumn(1,matrixA)[i]);
		System.out.println();
		for (int i=0;i<matrixA[1].length;++i)
			System.out.print(getRow(1,matrixA)[i]);
		System.out.println();
//		for (int i=0;i<matrixA[1].length;++i)
//			System.out.print(sumLines(matrixA));
//		System.out.println();
//		for (int i=0, j=0;i<matrixA[1].length;++i, ++j)
//			System.out.print(sumCols(matrixA));
		System.out.println();
		for(int i = 0; i<createTriangle(4).length;++i){
			for (int j = 0; j<createTriangle(4)[i].length;++j){
				System.out.print(createTriangle(4)[i][j]);
			}
			System.out.println();
		}
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
	
	public static int[][] createTriangle (int h){
		int nLines = h, low=0, nCols = 2*h-1, high=nCols-1;
		int[][]triangle = new int [nLines][nCols];
		for(int i = nLines-1; i>=0; --i){
			for(int j = low; j<=high; ++j){
				triangle[i][j]=1;
			}
			++low;
			--high;
		}
		return triangle;
	}

	public static int[] getColumn ( int col, int [][] n){
		int nLines = n.length;
		int nCols = n[0].length;
		int[] columnToReturn = new int [nLines];
		for ( int i = 0; i< nLines; ++i){
			columnToReturn[i]= n[i][col];
		}
		return columnToReturn;
	}

	public static int[] getRow(int line, int [][] n){
		int nLines, nCols;
		nLines = n.length;
		nCols = n[0].length;
		int [] row = new int [nCols];
		for ( int i = 0 ; i< nCols ; ++i){
			row[i]=n[line][i];
		}
		return row;
	}

	public static int[]sumLines (int[][]m){
		int nLines = m.length;
		int nCols = m[0].length;
		int sum = 0;
		int[]s=new int[nLines];
		for(int i =0;i<nLines;++i){
			for(int j=0; j<nCols;++j){
				sum+=m[i][j];
			}
			s[i]=sum;
		}
		return s;
	}

	public static int[]sumCols (int[][]m){
		int nLines = m.length;
		int nCols = m[0].length;
		int sum;
		int[]s=new int[nCols];
		for(int i =0;i<nCols;++i){
			sum = 0;
			for(int j=0; j<nLines;++j){
				sum+=m[i][j];
			}
			s[i]=sum;
		}
		return s;
	}
}
