/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.mvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import uzdiz_zadaca_3.composite.Aktuator;
import uzdiz_zadaca_3.composite.FoiZgrada;
import uzdiz_zadaca_3.composite.Mjesto;
import uzdiz_zadaca_3.composite.Senzor;
import static uzdiz_zadaca_3.mvc.MainView.prikazi;
import static uzdiz_zadaca_3.mvc.MainView.unos;
import uzdiz_zadaca_3.utils.Params;

/**
 *
 * @author abenkovic
 */
public class MainController {

    private FoiZgrada zgrada;
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
                    Mjesto m = this.zgrada.dohvatiMjesto(id);
                    if (m != null) {
                        MjestoView mv = new MjestoView();
                        mv.ispisMjesta(m);
                    } else {
                        view.prikazi("Ne postoji mjesto sa ID-om " + id, "warning");
                    }
                } catch (NumberFormatException e) {
                    view.prikazi("Neispravan format ID-a", "warning");
                }
            } else if (ulaz[0].equals("S") && ulaz.length > 1) {
                try {
                    int id = Integer.parseInt(ulaz[1]);
                    try {
                        Senzor s = (Senzor) zgrada.dohvatiUredjaj(id);
                        if (s != null) {
                            UredjajView uv = new UredjajView();
                            uv.ispisSenzora(s);
                        } else {
                            view.prikazi("Ne postoji senzor sa ID-om " + id, "warning");
                        }
                    } catch (ClassCastException e) {
                        view.prikazi("Trazeni uredjaj nije senzor!", "warning");
                    }

                } catch (NumberFormatException e) {
                    view.prikazi("Neispravan format ID-a", "warning");
                }
            } else if (ulaz[0].equals("A") && ulaz.length > 1) {

                try {
                    int id = Integer.parseInt(ulaz[1]);
                    try {
                        Aktuator a = (Aktuator) zgrada.dohvatiUredjaj(id);
                        if (a != null) {
                            UredjajView uv = new UredjajView();
                            uv.ispisAktuatora(a);
                        } else {
                            view.prikazi("Ne postoji aktuator sa ID-om " + id, "warning");
                        }
                    } catch (ClassCastException e) {
                        view.prikazi("Trazeni uredjaj nije aktuator!", "warning");
                    }

                } catch (NumberFormatException e) {
                    view.prikazi("Neispravan format ID-a", "warning");
                }
            } else if (in.equals("S")) {
                MjestoView mv = new MjestoView();
                for (Mjesto m : zgrada.getMjesta()) {
                    mv.prikazStatistike(m.stat);
                }

            } else if (in.equals("SP")) {

                ObjectOutputStream oos;
                try {
                    oos = new ObjectOutputStream(
                            new FileOutputStream(new File("zgrada.foi")));
                    oos.writeObject(zgrada);
                    oos.close();
                    view.prikazi("Podaci uspjesno spremljeni.", "info");

                } catch (IOException ex) {
                    view.prikazi("Greska prilikom spremanja podataka: " + ex.toString(), "warning");
                }

            } else if (in.equals("VP")) {

                try {
                    FileInputStream fileIn;
                    fileIn = new FileInputStream("zgrada.foi");
                    ObjectInputStream input = new ObjectInputStream(fileIn);
                    this.zgrada = (FoiZgrada) input.readObject();
                    view.prikazi("Podaci su uspjesno ucitani.", "info");
                } catch (FileNotFoundException ex) {
                    view.prikazi("Ne postoje spremljeni podaci!", "warning");
                } catch (ClassNotFoundException | IOException ex) {
                    view.prikazi("Greska prilikom ucitavanja podataka: " + ex.toString(), "warning");
                }

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
                        Params.setPi(n);
                        view.prikazi("Prosjecni % ispravnosti uredjaja pohranjen.", "info");
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
