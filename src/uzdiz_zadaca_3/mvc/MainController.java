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

            if (in.compareTo("I") == 0) {
                status = true;

            } else {
                MainView.cleanLine();
            }
        }
    }

}
