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
        super.prikazi(String.format("%-35s %15s", "Naziv uredjaja:", s.naziv), "info");
        super.prikazi(String.format("%-35s %15s", "ID uredjaja:", String.valueOf(s.id)), "info");
        super.prikazi(String.format("%-35s %15s", "Min. vrijednost:", s.min), "info");
        super.prikazi(String.format("%-35s %15s", "Maks. vrijednost:", s.max), "info");
        super.prikazi(String.format("%-35s %15s", "Tip uredjaja:", s.tip), "info");
        super.prikazi(String.format("%-35s %15s", "Vrsta uredjaja:", s.vrsta), "info");
        super.prikazi(String.format("%-35s %15s", "Trenutna vrijednost:", s.vrijednost), "info");
        super.prikazi(String.format("%-35s %15s", "Neuspjesne provjere:", s.neuspjesneProvjere), "info");
        super.prikazi(String.format("%-35s %15s", "Uredjaj onemogucen:", s.onemogucen), "info");
        super.prikazi(String.format("%-35s %15s", "Status uredjaja:", s.status), "info");
        super.prikazi(String.format("%-35s %15s", "Ima novu vrijednost:", s.imaNovuVrijednost), "info");
        super.prikazi(String.format("%-35s %15s", "Komentar:", s.komentar), "info");
        super.prikazi("", "info");
        super.prikazi(String.format("%-35s", "Popis aktuatora kojima je senzor pridruzen:"), "info");
        for (Aktuator a : s.getAktuatori()) {
            super.prikazi(String.format("%-35s %15s", a.naziv, a.id), "info");
        }
    }

}
