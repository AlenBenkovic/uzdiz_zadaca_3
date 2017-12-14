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
    private int x = 1;
    private int x_max = Integer.parseInt(Params.params.get("-br").toString()) - Integer.parseInt(Params.params.get("-brk").toString());
    private int y = 0;
    private int y_max = Integer.parseInt(Params.params.get("-bs").toString());
    private int c_max = Integer.parseInt(Params.params.get("-brk").toString());
    private int c_x = x_max + 1;
    private int c_y = 0;

    public ToFview() {
        ekran();
    }

    private void ekran() {
        System.out.print("\033" + "c"); // clean screen

        prikazi(37, "123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789");
        prikazi(37, "123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789");
        prikazi(37, "123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789");
        prikazi(37, "123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789");
        prikazi(37, "123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789");
        prikazi(37, "123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789");
        prikazi(37, "123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789");
        prikazi(37, "123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789");
        prikazi(37, "123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789");
        prikazi(37, "123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123456789123546789123456789123456789123456789123456789123456789");

    }

    private void postavi(int x, int y, boolean isC) {
        if (isC) {
            this.c_x = x;
            this.c_y = y;
        } else {
            this.x = x;
            this.y = y;
        }

        System.out.print(ANSI_ESC + x + ";" + y + "f");
    }

    private void unos(String tekst) {
        postavi(this.x_max + 1, 0, true);
        System.out.print(ANSI_ESC + "2K");

        for (int i = 0; i < tekst.length(); i++) {
            if (this.c_y <= this.y_max) {
                postavi(this.c_x, this.c_y + 1, true);
            } else if (this.c_x <= this.c_max) {
                postavi(this.c_x + 1, 0, true);
            }
            System.out.println(tekst.charAt(i));

        }
        
        postavi(this.x_max + this.c_max, 0, true);
        System.out.print(ANSI_ESC + "2K");

    }

    private void prikazi(int boja, String tekst) {

        System.out.print(ANSI_ESC + boja + "m");

        for (int i = 0; i < tekst.length(); i++) {
            if (this.y <= this.y_max) {
                postavi(this.x, this.y + 1, false);
            } else if (this.x < this.x_max) {
                postavi(this.x + 1, 0, false);
            } else {
                boolean status = false;

                while (!status) {

                    unos("Pritisnite n/N za nastavak...");
                    Scanner scanner = new Scanner(System.in);
                    String in = scanner.nextLine();

                    if (in.toLowerCase().compareTo("n") == 0) {
                        System.out.println("daa");
                        status = true;
                        this.x = 0;
                        this.y = 1;
                        System.out.print("\033" + "c"); // clean screen

                    } else {
                        System.out.print(ANSI_ESC + "2K");
                    }
                }

            }

            System.out.print(tekst.charAt(i));

        }

    }

}
