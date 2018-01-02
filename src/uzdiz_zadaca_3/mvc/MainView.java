/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.mvc;

import java.util.Scanner;
import uzdiz_zadaca_3.utils.Params;

/**
 *
 * @author abenkovic
 */
public class MainView {

    public static final String ANSI_ESC = "\033[";
    private static int x = 1;
    private static final int X_MAX = Integer.parseInt(Params.params.get("-br").toString()) - Integer.parseInt(Params.params.get("-brk").toString());
    private static int y = 0;
    private static final int Y_MAX = Integer.parseInt(Params.params.get("-bs").toString());
    private static final int UNOS_MAX = Integer.parseInt(Params.params.get("-brk").toString());
    private static int unos_x = X_MAX + 1;
    private static int unos_y = 0;

    public MainView() {
        cleanScreen();
    }

    public static void cleanScreen() {

        x = 1;
        y = 0;
        System.out.print("\033" + "c");
    }

    public static void cleanLine() {
        System.out.print(ANSI_ESC + "2K");
    }
    

    public static void postavi(int j, int k, boolean isC) {
        if (isC) {
            unos_x = j;
            unos_y = k;
        } else {
            x = j;
            y = k;
        }

        System.out.print(ANSI_ESC + j + ";" + k + "f");
    }

    public static void unos(String tekst) {
        postavi(X_MAX, 0, true);
        System.out.print(ANSI_ESC + "2K");

        for (int i = 0; i < tekst.length(); i++) {
            if (unos_y <= Y_MAX) {
                postavi(unos_x, unos_y + 1, true);
            } else if (unos_x < UNOS_MAX) {
                postavi(unos_x + 1, 0, true);
            }
            System.out.println(tekst.charAt(i));
        }

        postavi(X_MAX + 1, 0, true);
        System.out.print(ANSI_ESC + "2K");

    }

    public static void prikazi(String tekst, String type) {
        switch (type) {
            case "info":
                System.out.print(ANSI_ESC + "37m");
                break;
            case "warning":
                System.out.print(ANSI_ESC + "41m");
                break;
            case "title":
                System.out.print(ANSI_ESC + "44m");
                break;
            case "title2":
                System.out.print(ANSI_ESC + "43m");
                break;
        }

        for (int i = 0; i < tekst.length(); i++) {
            if (y < Y_MAX) {
                postavi(x, y + 1, false);
            } else if (x < X_MAX) {
                postavi(x + 1, 0, false);
            }

            if (x == X_MAX) {
                pressAnyKey();
            }

            System.out.print(tekst.charAt(i));

        }

        // reset
        postavi(x + 1, 0, false);
        System.out.print(ANSI_ESC + "0m");

    }

    public static void pressAnyKey() {
        boolean status = false;

        while (!status) {
            System.out.print(ANSI_ESC + "0m");

            unos("Pritisnite n/N za nastavak...");
            Scanner scanner = new Scanner(System.in);
            String in = scanner.nextLine();

            if (in.toLowerCase().compareTo("n") == 0) {
                status = true;

            } else {
                System.out.print(ANSI_ESC + "2K");
            }
        }

        System.out.print("\033" + "c"); // clean screen
        postavi(1, 1, false);

    }

}
