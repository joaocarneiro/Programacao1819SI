import java.util.Scanner;
public class Payment {
    public static void main (String[]args){
        Scanner kbd = new Scanner (System.in);
        int num = 0;
        for(int i=0;i<1;++i) {
        	num*=10;
        	num=args[0].charAt(0)-'0';
        }
        int [] coinsLeft = new int [8];
        for(int i=0;i<coinsLeft.length;++i)
            coinsLeft[i]=num;
        int [] coinsUsed = new int [8];
        final double [] COIN_VALUE = {2,1,0.50,0.20,0.10,0.05,0.02,0.01};
        double price, received, change;
        boolean noChange = false;
        String receipt = "";
        String euro = "\u20ac";
         
        System.out.println("Maquina carregada com "+num+" moedas de cada tipo.");
        while(true){
        	
            System.out.print("Preco (em euros)? ");
            price=kbd.nextDouble()*100;
            if(price==0)
                break;
            int priceInt = (int)price;
        	double priceDecimal = price-(int)price;
            if(priceDecimal>=0.5)
            	priceInt+=1;
            
            System.out.print("Quantia recebida (em euros)? ");
            received=kbd.nextDouble()*100;
            int receivedInt = (int)received;
        	double receivedDecimal = received-(int)received;
            if(receivedDecimal>=0.5)
            	receivedInt+=1;
            
            change=receivedInt-priceInt;
            if(change==0)
                System.out.println("Quantia certa");
            else if(change<0)
                System.out.println("Quantia insuficiente");
            else{
                receipt = "Demasia ("+(change/100)+") = ";
                while(change!=0 && !noChange) {
                    for (int i = 0; i < COIN_VALUE.length; i++) {
                        while (change >= (COIN_VALUE[i] * 100)) {
                            if (coinsLeft[i] != 0) {
                                change -= (COIN_VALUE[i] * 100);
                                coinsUsed[i]++;
                                coinsLeft[i]--;
                            }
                            else if (i < COIN_VALUE.length) {
                                i++;
                                if(i==COIN_VALUE.length){
                                    noChange=true;
                                    for(int j=0;j<coinsLeft.length;j++)
                                        coinsLeft[j]=coinsLeft[j]+coinsUsed[j];
                                    break;
                                }
                            }
                        }
                    }
                }
                if(!noChange) {
                    for (int i = 0; i < coinsUsed.length; i++)
                        if (coinsUsed[i] > 0)
                            receipt = receipt + coinsUsed[i] + " x " + COIN_VALUE[i] + euro + " + ";
                    receipt = receipt.substring(0, receipt.length() - 3) + ".";
                    System.out.print(receipt);
                }
                else {
                    System.out.print(receipt+" Nao ha troco. Venda interrompida.");
                    noChange=false;
                }
                System.out.println();

                for(int i=0;i<coinsUsed.length;i++)
                    coinsUsed[i] = 0;
            }
        } kbd.close();
    }
}
