import java.util.Scanner;
public class Payment {
    public static void main (String[]args){
        Scanner kbd = new Scanner (System.in);
        int k=0;
        int num=0;
        while( k < 1) {
            num *= 10;
            num += args[0].charAt(k++) - '0';
        }
        int [] coinsLeft = new int [8];
        for(int i=0;i<coinsLeft.length;++i){
            coinsLeft[i]=num;
        }
        int [] coinsUsed = {0,0,0,0,0,0,0,0};
        double [] coinValue = {2,1,0.50,0.20,0.10,0.05,0.02,0.01};
        double price, received, change;
        boolean noChange = false;
        String receipt = "";
        String euro = "\u20ac";
        System.out.println("Maquina carregada com "+num+" moedas de cada tipo.");
        while(true){
            System.out.print("Preco (em euros)? ");
            price=kbd.nextDouble()*100;
            System.out.print(price);
            if(price==0)
                break;
            System.out.print("Quantia recebida (em euros)? ");
            received=kbd.nextDouble()*100;;
            System.out.print(received);
            change=(int)received-(int)price;
            if(change==0)
                System.out.println("Quantia certa");
            else if(change<0)
                System.out.println("Quantia insuficiente");
            else{
                receipt = "Demasia ("+(change/100)+") = ";
                while(change!=0) {
                    for (int i = 0; i < coinValue.length; i++) {
                        while (change >= (coinValue[i] * 100)) {
                            if (coinsLeft[i] != 0) {
                                change -= (coinValue[i] * 100);
                                coinsUsed[i]++;
                                coinsLeft[i]--;
                            }
                            else if (i < coinValue.length) {
                                i++;
                                if(i==8){
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
                            receipt = receipt + coinsUsed[i] + " x " + coinValue[i] + euro + " + ";
                    receipt = receipt.substring(0, receipt.length() - 3) + ".";
                    System.out.print(receipt);
                }
                else
                    System.out.print(receipt+" Nao ha troco. Venda interrompida.");
                System.out.println();

                for(int i=0;i<coinsUsed.length;i++)
                    coinsUsed[i] = 0;
                noChange=false;
            }
        }
    }
}
