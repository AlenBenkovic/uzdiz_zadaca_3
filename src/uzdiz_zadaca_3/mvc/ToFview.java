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
public class ToFview {

    public static final String ANSI_ESC = "\033[";
    private int x = 0;
    private int x_max = Integer.parseInt(Params.params.get("-br").toString());
    private int y = 0;
    private int y_max = Integer.parseInt(Params.params.get("-bs").toString());
    
    private String input = "a";

    public ToFview() {
        for (int i = 0; i < 24; i++) {
            prikazi(i, 0, 21, "*");

        }
        while (!input.contains("x")) {
            postavi(23,0);
            Scanner scanner = new Scanner(System.in);
            System.out.print("Type some data for the program: ");
            String input = scanner.nextLine();
            for (int i = 15; i < 20; i++) {
                postavi(i, 0);
                System.out.println("test");
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ex) {
                }

            }
        }

    }

    static void postavi(int x, int y) {
        System.out.print(ANSI_ESC + x + ";" + y + "f");
    }

    static void prikazi(int x, int y, int boja, String tekst) {
        postavi(x, y);
        System.out.print(ANSI_ESC + boja + "m");
        System.out.print(tekst);
    }

}
