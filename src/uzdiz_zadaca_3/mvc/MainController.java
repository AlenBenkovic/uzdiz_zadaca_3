/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.mvc;

import java.util.Scanner;
import uzdiz_zadaca_3.composite.FoiZgrada;
import static uzdiz_zadaca_3.mvc.MainView.prikazi;
import static uzdiz_zadaca_3.mvc.MainView.unos;

/**
 *
 * @author abenkovic
 */
public class MainController {

    private final FoiZgrada zgrada;
    private final MainView view;

    public MainController(FoiZgrada zgrada, MainView view) {
        this.zgrada = zgrada;
        this.view = view;
    }

    public void prikaziNaredbe() {
        view.cleanScreen();
        prikazi("M x - ispis podataka mjesta x", "info");
        prikazi("S x - ispis podataka senzora x", "info");
        prikazi("A x - ispis podataka aktuatora x", "info");
        prikazi("S - ispis statistike", "info");
        prikazi("SP - spremi podatke (mjesta, uređaja)", "info");
        prikazi("VP - vrati spremljene podatke (mjesta, uređaja)", "info");
        prikazi("C n - izvršavanje n ciklusa dretve (1-100)", "info");
        prikazi("VF - izvršavanje vlastite funkcionalnosti", "info");
        prikazi("PI n - prosječni % ispravnosti uređaja (0-100)", "info");
        prikazi("I - izlaz.", "info");
    }

    public void cekajNaredbu() {
        boolean status = false;
        while (!status) {
            unos("Unesite naredbu: ");
            Scanner scanner = new Scanner(System.in);
            String in = scanner.nextLine();
            String[] ulaz = in.split(" ");

            if (in.compareTo("I") == 0) {
                status = true;
            } else if (ulaz[0].equals("M") && ulaz.length > 1) {
                try {
                    int id = Integer.parseInt(ulaz[1]);
                    view.prikazi("Mjesto " + ulaz[1], "info");
                } catch (NumberFormatException e) {
                    view.prikazi("Neispravan format ID-a", "warning");
                }
            } else if (ulaz[0].equals("S") && ulaz.length > 1) {
                try {
                    int id = Integer.parseInt(ulaz[1]);
                    view.prikazi("SSS", "info");
                } catch (NumberFormatException e) {
                    view.prikazi("Neispravan format ID-a", "warning");
                }
            } else if (ulaz[0].equals("A") && ulaz.length > 1) {

                try {
                    int id = Integer.parseInt(ulaz[1]);
                    view.prikazi("AAA", "info");
                } catch (NumberFormatException e) {
                    view.prikazi("Neispravan format ID-a", "warning");
                }
            } else if (in.equals("S")) {
                view.prikazi("Stat", "info");
            } else if (in.equals("SP")) {
                view.prikazi("SP", "info");
            } else if (in.equals("VP")) {
                view.prikazi("VP", "info");
            } else if (ulaz[0].equals("C") && ulaz.length > 1) {
                try {
                    int n = Integer.parseInt(ulaz[1]);
                    if (n >= 1 && n <= 100) {
                        view.prikazi("C n", "info");
                    } else {
                        view.prikazi("Broj mora biti u rasponu od 1 do 100", "warning");
                    }
                } catch (NumberFormatException e) {
                    view.prikazi("Neispravan format broja", "warning");
                }
            } else if (in.equals("VF")) {
                view.prikazi("VF", "info");
            } else if (ulaz[0].equals("PI") && ulaz.length > 1) {
                try {
                    int n = Integer.parseInt(ulaz[1]);
                    if (n >= 0 && n <= 100) {
                        view.prikazi("PI", "info");
                    } else {
                        view.prikazi("Broj mora biti u rasponu od 0 do 100", "warning");
                    }
                } catch (NumberFormatException e) {
                    view.prikazi("Neispravan format broja", "warning");
                }
            } else {
                MainView.cleanLine();
            }
        }
    }

    private boolean provjeraNaredbe(String ulaz) {
        String[] naredbe = ulaz.split(" ");

        return true;
    }

}
