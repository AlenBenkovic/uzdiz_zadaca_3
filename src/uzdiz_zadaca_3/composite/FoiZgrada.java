/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.composite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import uzdiz_zadaca_3.factory.FoiFactory;
import uzdiz_zadaca_3.factory.UredjajFactory;
import uzdiz_zadaca_3.iterator.MjestoIterator;
import uzdiz_zadaca_3.iterator.FoiIterator;
import uzdiz_zadaca_3.mvc.ToFview;
import uzdiz_zadaca_3.visitor.UredjajVisitor;

/**
 *
 * @author abenkovic
 */
public class FoiZgrada implements Foi {

    private List<Mjesto> mjesta = new ArrayList<>();

    @Override
    public boolean provjera() {
        for (Mjesto m : mjesta) {
            ToFview.prikazi("Radim provjeru uredjaja za " + m.id + " " + m.naziv, "title");
            m.provjera();
        }
        return true;
    }

    public void add(Mjesto mjesto) {
        this.mjesta.add(mjesto);
    }

    public List<Mjesto> getMjesta() {
        return mjesta;
    }

    public void setMjesta(List<Mjesto> mjesta) {
        this.mjesta = mjesta;
    }

    public FoiIterator createIterator() {
        return new MjestoIterator(this.mjesta);
    }

    @Override
    public boolean inicijalizacija() {
        FoiIterator iterator = this.createIterator();
        while (iterator.hasNext()) {
            Mjesto m = (Mjesto) iterator.next();
            ToFview.prikazi("Inicijaliziram uredjaje za " + m.id + " " + m.naziv, "title");
            m.inicijalizacija();
        }
        return true;
    }

    public void postaviUredjaje() {
        FoiFactory factory = new UredjajFactory();
        for (Mjesto m : this.mjesta) {
            ToFview.prikazi("Postavljam uredjaje za " + m.naziv, "title");

            for (int i = 0; i < m.brojSenzora; i++) {
                m.addUredjaj(factory.kreirajUredjaj(true, m.tip));
            }

            for (int i = 0; i < m.brojAktuatora; i++) {
                m.addUredjaj(factory.kreirajUredjaj(false, m.tip));
            }
        }
    }

    public void opremanjeMjesta() {
        FoiIterator iterator = this.createIterator();
        while (iterator.hasNext()) {
            Mjesto m = (Mjesto) iterator.next();
            ToFview.prikazi("Opremam mjesto " + m.id + " " + m.naziv, "title");
            m.opremanjeMjesta();
        }
    }

    public void stanjeUredjaja() {

        FoiIterator iterator = this.createIterator();
        while (iterator.hasNext()) {
            Mjesto m = (Mjesto) iterator.next();
            ToFview.prikazi("Prikaz stanja uredjaja " + m.id + " " + m.naziv, "title");
            for (Uredjaj u : m.getUredjaji()) {
                UredjajVisitor uv = new UredjajVisitor();
                ToFview.prikazi("Uredjaj " + u.naziv + " (" + u.formatVrijednost(u.vrijednost) + "/" + u.formatVrijednost(u.max) + ") " + (int) u.accept(uv) + "%", "info");
            }

        }

    }

    public void statistika() {
        for (Mjesto mjesto : this.mjesta) {
            ToFview.prikazi("Statistika za " + mjesto.naziv, "info");
            for (Map.Entry<String, Integer> entry : mjesto.statistikaMjesta.entrySet()) {
                ToFview.prikazi(entry.getKey() + ": " + entry.getValue(), "info");
            }

        }
    }

}
