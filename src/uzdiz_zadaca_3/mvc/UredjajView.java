/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.mvc;

import uzdiz_zadaca_3.composite.Aktuator;
import uzdiz_zadaca_3.composite.Senzor;

/**
 *
 * @author abenkovic
 */
public class UredjajView extends MainView {

    public void ispisSenzora(Senzor s) {
        super.cleanScreen();
        super.prikazi("PRIKAZ SENZORA " + s.id + " " + s.naziv, "title");
        super.prikazi("", "info");
        super.prikazi(String.format("%-35s %15s", "ID uredjaja:", String.valueOf(s.id)), "info");
        super.prikazi(String.format("%-35s %15s", "Min. vrijednost:", s.min), "info");
        super.prikazi(String.format("%-35s %15s", "Maks. vrijednost:", s.max), "info");
        super.prikazi(String.format("%-35s %15s", "Tip uredjaja:", s.tip), "info");
        super.prikazi(String.format("%-35s %15s", "Vrsta uredjaja:", s.vrsta), "info");
        super.prikazi(String.format("%-35s %15s", "Trenutna vrijednost:", s.vrijednost), "info");
        super.prikazi(String.format("%-35s %15s", "Neuspjesne provjere:", s.neuspjesneProvjere), "info");
        super.prikazi(String.format("%-35s %15s", "Uredjaj onemogucen:", s.onemogucen), s.onemogucen? "warning":"info");
        super.prikazi(String.format("%-35s %15s", "Status uredjaja:", s.status), "info");
        super.prikazi(String.format("%-35s %15s", "Ima novu vrijednost:", s.imaNovuVrijednost), "info");
        super.prikazi(String.format("%-35s %15s", "Komentar:", s.komentar), "info");
        super.prikazi("", "info");
        super.prikazi(String.format("%-35s", "Popis aktuatora kojima je senzor pridruzen:"), "info");
        for (Aktuator a : s.getAktuatori()) {
            super.prikazi(String.format("%-35s %15s", a.naziv, a.id), "info");
        }
    }

    public void ispisAktuatora(Aktuator a) {
        super.cleanScreen();
        super.prikazi("PRIKAZ AKTUATORA " + a.id + " " + a.naziv, "title");
        super.prikazi("", "info");
        super.prikazi(String.format("%-35s %15s", "ID uredjaja:", String.valueOf(a.id)), "info");
        super.prikazi(String.format("%-35s %15s", "Min. vrijednost:", a.min), "info");
        super.prikazi(String.format("%-35s %15s", "Maks. vrijednost:", a.max), "info");
        super.prikazi(String.format("%-35s %15s", "Tip uredjaja:", a.tip), "info");
        super.prikazi(String.format("%-35s %15s", "Vrsta uredjaja:", a.vrsta), "info");
        super.prikazi(String.format("%-35s %15s", "Trenutna vrijednost:", a.vrijednost), "info");
        super.prikazi(String.format("%-35s %15s", "Neuspjesne provjere:", a.neuspjesneProvjere), "info");
        super.prikazi(String.format("%-35s %15s", "Uredjaj onemogucen:", a.onemogucen), "info");
        super.prikazi(String.format("%-35s %15s", "Status uredjaja:", a.status), "info");
        super.prikazi(String.format("%-35s %15s", "Komentar:", a.komentar), "info");
        super.prikazi("", "info");
        super.prikazi(String.format("%-35s", "Popis senzora kojima je aktuator pridruzen:"), "info");
        for (Senzor s : a.getSenzori()) {
            super.prikazi(String.format("%-35s %15s", s.naziv, s.id), "info");
        }
    }

}
