/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.composite;

import java.util.ArrayList;
import java.util.List;
import uzdiz_zadaca_3.visitor.Visitable;
import uzdiz_zadaca_3.visitor.Visitor;

/**
 *
 * @author abenkovic
 */
public class Senzor extends Uredjaj implements Foi, Visitable {

    private List<Aktuator> aktuatori;
    public boolean imaNovuVrijednost = false;

    public Senzor(String naziv, int tip, int vrsta, float min, float max, String komentar) {
        super(naziv, tip, vrsta, min, max, komentar);
        this.aktuatori = new ArrayList<Aktuator>();
    }


    public List<Aktuator> getAktuatori() {
        return aktuatori;
    }

    public void setAktuatori(List<Aktuator> aktuatori) {
        this.aktuatori = aktuatori;
    }

    public void add(Aktuator aktuator) {
        this.aktuatori.add(aktuator);
    }

    public void remove(Aktuator aktuator) {
        this.aktuatori.remove(aktuator);
    }

    @Override
    public void pridruzenostUredjaja() {
        String poruka = "\n-----------"
                + "\n[Senzor] " + this.id + " " + this.naziv + " pridruzen je aktuatorima:  ";
        for (Aktuator a : this.aktuatori) {
            poruka = poruka + "\n>" + a.id + " " + a.naziv;
        }
        super.logger.log(poruka, "info");
    }

    @Override
    public Uredjaj zamjena() {
        return new Senzor(this.naziv, this.tip, this.vrsta, this.min, this.max, this.komentar);
    }

    public boolean isImaNovuVrijednost() {
        return imaNovuVrijednost;
    }

    public void setImaNovuVrijednost(boolean imaNovuVrijednost) {
        this.imaNovuVrijednost = imaNovuVrijednost;
    }

    @Override
    public float accept(Visitor visitor) {
        return visitor.visit(this);
    }



}
