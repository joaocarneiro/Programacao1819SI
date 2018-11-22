import java.util.Scanner;

public class Grid {
    public static void main(String[] args) {
        Scanner kbd = new Scanner(System.in);
        int nl, nc, al, lc;

        do {
            System.out.print("Linhas (2..5)? ");
            nl = kbd.nextInt();
        }
        while (nl < 2 || nl > 5);

        do {
            System.out.print("Colunas (2..8)? ");
            nc = kbd.nextInt();
        }
        while (nc < 2 || nc > 8);

        do {
            System.out.print("Altura (1..3)? ");
            al = kbd.nextInt();
        }
        while (al < 1 || al > 3);

        do {
            System.out.print("Largura (1..5)? ");
            lc = kbd.nextInt();
        }
        while (lc < 1 || lc > 5);

        for (int i = 0; i <= nc; ++i) {
            System.out.print("0");
            for (int j = 0; j < lc; ++j)
                if (i != nc)
                    System.out.print("-");
        }
        System.out.println();
        for (int i = 1; i <= nl; ++i) {
            for (int j = 0; j < al; ++j) {
                for (int k = 0; k <= nc; ++k) {
                    System.out.print("|");
                    for (int l = 0; l < lc; ++l)
                        System.out.print(" ");
                }
                System.out.println();
            }
            if (i != nl) {
                for (int m = 0; m <= nc; ++m) {
                    if (m == 0 || m == nc)
                        System.out.print("0");
                    else
                        System.out.print("+");
                    for (int n = 0; n < lc; ++n)
                        if (m != nc)
                            System.out.print("-");
                }
                System.out.println();
            }
        }
        for (int i = 0; i <= nc; ++i) {
            System.out.print("0");
            for (int j = 0; j < lc; ++j)
                if (i != nc)
                    System.out.print("-");
        }
    }
}
