/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.mvc;

import uzdiz_zadaca_3.composite.Aktuator;
import uzdiz_zadaca_3.composite.Mjesto;
import uzdiz_zadaca_3.composite.Senzor;
import uzdiz_zadaca_3.composite.Uredjaj;

/**
 *
 * @author abenkovic
 */
public class MjestoView extends MainView {

    public void ispisMjesta(Mjesto m) {
        super.cleanScreen();
        super.prikazi("PRIKAZ MJESTA " + m.id + " " + m.naziv, "title");
        super.prikazi("", "info");
        super.prikazi(String.format("%-35s %15s", "ID mjesta:", String.valueOf(m.id)), "info");
        super.prikazi(String.format("%-35s %15s", "Broj aktuatora:", m.brojAktuatora), "info");
        super.prikazi(String.format("%-35s %15s", "Broj senzora:", m.brojSenzora), "info");
        super.prikazi(String.format("%-35s %15s", "Tip mjesta:", m.tip), "info");
        super.prikazi("", "info");
        super.prikazi(String.format("%-35s", "Popis pridruzenih uredjaja:"), "info");
        for (Uredjaj u : m.getUredjaji()) {
            super.prikazi(String.format("%-35s %15s", u.naziv, u.id), "info");
        }
    }

}
