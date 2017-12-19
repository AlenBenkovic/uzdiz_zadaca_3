/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.composite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import uzdiz_zadaca_3.factory.FoiFactory;
import uzdiz_zadaca_3.factory.UredjajFactory;
import uzdiz_zadaca_3.iterator.MjestoIterator;
import uzdiz_zadaca_3.iterator.FoiIterator;
import uzdiz_zadaca_3.mvc.ToFview;
import uzdiz_zadaca_3.utils.Params;
import uzdiz_zadaca_3.visitor.UredjajVisitor;

/**
 *
 * @author abenkovic
 */
public class FoiZgrada implements Foi {

    private List<Mjesto> mjesta = new ArrayList<>();
    private List<Uredjaj> uredjajModeli = new ArrayList<>();

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

    public void ucitajModeleUredjaja() {
        FoiFactory factory = new UredjajFactory();
        ToFview.prikazi("Ucitavam modele senzora", "title");
        uredjajModeli = factory.ucitajModeleUredjaja(true);
        ToFview.prikazi("Ucitavam modele aktuatora", "title");
        uredjajModeli = factory.ucitajModeleUredjaja(false);
    }

    public void ucitajRaspored() {
        UredjajFactory factory = new UredjajFactory();
        try {
            FileReader fr = new FileReader(Params.params.get("-r").toString());
            BufferedReader br = new BufferedReader(fr);
            String s;
            int redak = 0;
            while ((s = br.readLine()) != null) {
                String[] podatak = s.trim().split(";");
                if (redak > 2) { // prva tri reda su zaglavlje
                    if (Integer.parseInt(podatak[0]) == 0) {
                        // ToFview.prikazi("Raspored po mjestima", "info");
                        /*
                            0 - tip zapisa (mjesto ili aktuator)
                            1 - ID mjesta
                            2 - 0-senzor , 1-aktuator
                            3 - model uredjaja
                            4 - ID uredjaja
                         */
                        if (podatak.length == 5) {

                            Uredjaj u = this.dohvatiModel(Integer.parseInt(podatak[3]));
                            Mjesto m = this.dohvatiMjesto(Integer.parseInt(podatak[1]));
                            Uredjaj uredjaj;

                            if (u != null && m != null) {
                                if (Integer.parseInt(podatak[2]) == 0 && m.trenutniBrojUredjaja(true) < m.brojSenzora) {
                                    uredjaj = new Senzor(Integer.parseInt(podatak[4]), u.naziv, u.tip, u.vrsta, u.min, u.max, u.komentar);
                                    m.addUredjaj(uredjaj);
                                    ToFview.prikazi(m.naziv + " -> " + u.naziv, "info");
                                } else {
                                    ToFview.prikazi(m.naziv + " dosegnut je maksimalni broj senzora", "warning");
                                }

                                if (m.trenutniBrojUredjaja(false) < m.brojAktuatora) {
                                    uredjaj = new Aktuator(Integer.parseInt(podatak[4]), u.naziv, u.tip, u.vrsta, u.min, u.max, u.komentar);
                                    m.addUredjaj(uredjaj);
                                    ToFview.prikazi(m.naziv + " -> " + u.naziv, "info");
                                } else {
                                    ToFview.prikazi(m.naziv + " dosegnut je maksimalni broj aktuatora", "warning");
                                }

                                // radi nesta pametno
                            } else if (u == null) {
                                ToFview.prikazi("Ne mogu kreirati uredjaj jer ne postoji model sa ID-om " + podatak[3], "warning");
                            } else {
                                ToFview.prikazi("Mjesto sa ID-om " + podatak[1] + " ne postoji.", "warning");
                            }

                        } else {
                            ToFview.prikazi("Format rasporeda uredjaja po mjestima nije valjan", "warning");
                        }
                    } else if (Integer.parseInt(podatak[0]) == 1) {
                        /*
                            0 - tip zapisa (mjesto ili aktuator)
                            1 - ID aktuatora
                            2 - ID senzora [više njih]
                         */
                        ToFview.prikazi("Raspored senzora po aktuatorima", "info");
                        if (podatak.length == 3) {
                            // radi nesta pametno
                        } else {
                            ToFview.prikazi("Format rasporeda senzora po aktuatorima nije valjan", "warning");
                        }
                    }
                }

                redak++;
            }
        } catch (IOException e) {
            ToFview.prikazi("Greska prilikom citanja datoteke: " + e.toString(), "warning");
        }
    }

    private Uredjaj dohvatiModel(int id) {
        for (Uredjaj uredjaj : this.uredjajModeli) {
            if (uredjaj.id == id) {
                return uredjaj;
            }
        }
        return null;
    }

    private Mjesto dohvatiMjesto(int id) {
        for (Mjesto mjesto : mjesta) {
            if (mjesto.id == id) {
                return mjesto;
            }
        }
        return null;
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
