/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.composite;

import java.io.Serializable;
import uzdiz_zadaca_3.mvc.MainView;
import uzdiz_zadaca_3.utils.Params;
import uzdiz_zadaca_3.utils.RandomNumber;
import uzdiz_zadaca_3.visitor.Visitable;
import uzdiz_zadaca_3.visitor.Visitor;

/**
 *
 * @author abenkovic
 */
public abstract class Uredjaj implements Foi, Visitable, Serializable {

    public String naziv;
    public int tip;
    public int vrsta;
    public float min;
    public float max;
    public String komentar;
    public float vrijednost;
    public int id;
    public int status = 1;

    public boolean onemogucen = false;
    public int neuspjesneProvjere = 0;

    public Uredjaj(int id, String naziv, int tip, int vrsta, float min, float max, String komentar) {
        this.naziv = naziv;
        this.tip = tip;
        this.vrsta = vrsta;
        this.min = min;
        this.max = max;
        this.komentar = komentar;
        this.vrijednost = this.kreirajVrijednost();
        this.id = id;
    }

    @Override
    public boolean provjera() {
        this.status = this.status();
        float staraVrijednost = this.vrijednost;
        this.vrijednost = this.kreirajVrijednost();
        if (this instanceof Senzor) {
            Senzor s = (Senzor) this;
            if (staraVrijednost != vrijednost) {
                s.setImaNovuVrijednost(true);
            }
        }

        if (this.status < 1) {
            this.neuspjesneProvjere++;
            if (this.neuspjesneProvjere > 2) {
                this.onemogucen = true;
            }
        }

        MainView.prikazi("Uredjaj: " + this.id + " " + this.naziv, "title2");
        MainView.prikazi("Status: " + status + " (neuspjesne provjere: " + this.neuspjesneProvjere + ")", status>0? "info": "warning");

        if (status > 0) {
            MainView.prikazi("Vrijednost: " + this.formatVrijednost(this.vrijednost), "info");
        } else {
            MainView.prikazi("Vrijednost: " + "nepoznato", "info");
        }

        return !this.onemogucen;

    }

    @Override
    public boolean inicijalizacija() {
        return RandomNumber.dajSlucajniBroj(0, 100) < 90;
    }

    public abstract void pridruzenostUredjaja();

    public int status() {
        return RandomNumber.dajSlucajniBroj(0, 100) < Integer.parseInt(Params.params.get("-pi").toString()) ? 1 : 0;
    }

    public abstract Uredjaj zamjena();

    public float kreirajVrijednost() {
        switch (this.vrsta) {
            case 0:
            case 1:
            case 2:
                return RandomNumber.dajSlucajniBroj(this.min, this.max);
            case 3:
                return RandomNumber.dajSlucajniBroj((int) this.min, (int) this.max);
        }
        return 0;

    }

    public String formatVrijednost(float v) {
        switch (this.vrsta) {
            case 0:
                return String.valueOf((int) v);
            case 1:
                return String.format("%.1f", v);
            case 2:
                return String.format("%.5f", v);
            case 3:
                return (int) v > 0 ? "da" : "ne";
        }
        return "nema";
    }

    @Override
    public float accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public void setId(int id) {
        this.id = id;
    }

}
