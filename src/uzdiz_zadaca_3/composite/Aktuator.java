/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.composite;

import java.util.ArrayList;
import java.util.List;
import uzdiz_zadaca_3.mvc.MainView;
import uzdiz_zadaca_3.utils.RandomNumber;

/**
 *
 * @author abenkovic
 */
public class Aktuator extends Uredjaj implements Foi {

    private List<Senzor> senzori;
    boolean gore = true;

    public Aktuator(int id, String naziv, int tip, int vrsta, float min, float max, String komentar) {
        super(id, naziv, tip, vrsta, min, max, komentar);
        this.senzori = new ArrayList<Senzor>();
    }
    
    public Aktuator(int id, String naziv, int tip, int vrsta, float min, float max, String komentar, List<Senzor> senzori) {
        super(id, naziv, tip, vrsta, min, max, komentar);
        this.senzori = new ArrayList<Senzor>();
        this.senzori = senzori;
    }

    public void add(Senzor senzor) {
        this.senzori.add(senzor);
    }

    public void remove(Senzor senzor) {
        this.senzori.remove(senzor);
    }

    public List<Senzor> getSenzori() {
        return senzori;
    }

    public void setSenzori(List<Senzor> senzori) {
        this.senzori = senzori;
    }

    @Override
    public void pridruzenostUredjaja() {
        String poruka = "\n-----------"
                + "\n[Aktuator] " + this.id + " " + this.naziv + " pridruzeni su senzori:  ";
        for (Senzor s : this.senzori) {
            poruka = poruka + "\n>" + s.id + " " + s.naziv;
        }
        MainView.prikazi(poruka, "info");
    }

    @Override
    public Uredjaj zamjena() {
        return new Aktuator(this.id, this.naziv, this.tip, this.vrsta, this.min, this.max, this.komentar, this.senzori);
    }
    
    public void obaviRadnju() {
        switch (this.vrsta) {
            case 0:
            case 1:
            case 2:
                if (this.vrijednost == this.max) {
                    this.gore = false;
                } else if (this.vrijednost == this.min) {
                    this.gore = true;
                }
                if (this.gore) {
                    this.vrijednost = RandomNumber.dajSlucajniBroj(this.vrijednost, this.max);
                } else {
                    this.vrijednost = RandomNumber.dajSlucajniBroj(this.min, this.vrijednost);
                }
                break;
            case 3:
                if (this.vrijednost > 0) {
                    this.vrijednost = 0;
                } else {
                    this.vrijednost = 1;
                }

        }
        MainView.prikazi("Aktuator izvršava radnju", "info");
        MainView.prikazi("Nova vrijednost: " + this.formatVrijednost(this.vrijednost), "info");

    }
    
}
