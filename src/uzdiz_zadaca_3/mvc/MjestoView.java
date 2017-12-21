/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.mvc;

import uzdiz_zadaca_3.composite.Mjesto;
import uzdiz_zadaca_3.composite.Statistika;
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
        super.prikazi(String.format("%-35s %15s", "Maksimalni broj aktuatora:", m.brojAktuatora), "info");
        super.prikazi(String.format("%-35s %15s", "Maksimalni broj senzora:", m.brojSenzora), "info");
        super.prikazi(String.format("%-35s %15s", "Tip mjesta:", m.tip), "info");
        super.prikazi("", "info");
        super.prikazi(String.format("%-35s", "Popis pridruzenih uredjaja:"), "info");
        for (Uredjaj u : m.getUredjaji()) {
            super.prikazi(String.format("%-35s %15s", u.naziv, u.id), "info");
        }
    }

    public void prikazStatistike(Statistika s) {
        super.cleanScreen();
        super.prikazi("PRIKAZ STATISTIKE  ZA " + s.idMjesta + " " + s.nazivMjesta, "title");
        super.prikazi(String.format("%-40s %15s", "Ukupan broj senzora:", s.ukupanBrojSenzora), "info");
        super.prikazi(String.format("%-40s %15s", "Ukupan broj aktuatora:", s.ukupanBrojAktuatora), "info");
        super.prikazi(String.format("%-40s %15s", "Broj dodanih senzora:", s.brojDodanihSenzora), "info");
        super.prikazi(String.format("%-40s %15s", "Broj dodanih aktuatora:", s.brojDodanihAktuatora), "info");
        super.prikazi(String.format("%-40s %15s", "Broj uklonjenih senzora:", s.brojUklonjenihSenzora), "info");
        super.prikazi(String.format("%-40s %15s", "Broj uklonjenih aktuatora:", s.brojUklonjenihAktuatora), "info");
        super.prikazi(String.format("%-40s %15s", "Neuspjesne inicijalizacije senzora:", s.senzoriInitNeuspjeh), "info");
        super.prikazi(String.format("%-40s %15s", "Neuspjesne inicijalizacije aktuatora:", s.aktuatoriInitNeuspjeh), "info");
        super.pressAnyKey();

    }

}
