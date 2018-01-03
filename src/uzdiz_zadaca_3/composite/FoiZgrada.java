/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.composite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import uzdiz_zadaca_3.chain.MjestoHandler;
import uzdiz_zadaca_3.factory.FoiFactory;
import uzdiz_zadaca_3.factory.UredjajFactory;
import uzdiz_zadaca_3.iterator.MjestoIterator;
import uzdiz_zadaca_3.iterator.FoiIterator;
import uzdiz_zadaca_3.mvc.MainView;
import uzdiz_zadaca_3.utils.Params;
import uzdiz_zadaca_3.visitor.UredjajVisitor;

/**
 *
 * @author abenkovic
 */
public class FoiZgrada implements Foi, Serializable {

    private static List<Mjesto> mjesta = new ArrayList<>();
    private List<Uredjaj> uredjajModeli = new ArrayList<>();

    @Override
    public boolean provjera() {
        for (Mjesto m : mjesta) {
            MainView.prikazi("Radim provjeru uredjaja za " + m.id + " " + m.naziv, "title");
            m.provjera();
        }
        return true;
    }
    
    public MjestoHandler setChain(){
        for(int i=0; i<mjesta.size(); i++){
            if(i+1<mjesta.size()){
              mjesta.get(i).setSuccessor(mjesta.get(i+1));  
            } else {
                mjesta.get(i).setSuccessor(null);
            }    
        }
        return mjesta.get(0);
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
        MainView.prikazi("Ucitavam modele senzora", "title");
        uredjajModeli = factory.ucitajModeleUredjaja(true);
        MainView.prikazi("Ucitavam modele aktuatora", "title");
        uredjajModeli = factory.ucitajModeleUredjaja(false);
    }

    public void ucitajRaspored() {
        MainView.prikazi("Ucitavam raspored uredjaja", "title");
        List<String[]> drugiPokusaj = new ArrayList<>();
        try {
            FileReader fr = new FileReader(Params.params.get("-r").toString());
            BufferedReader br = new BufferedReader(fr);
            String s;
            int redak = 0;
            while ((s = br.readLine()) != null) {
                String[] podatak = s.trim().split(";");
                if (redak > 2) { // prva tri reda su zaglavlje
                    if (Integer.parseInt(podatak[0]) == 0) {
                        // MainView.prikazi("Raspored po mjestima", "info");
                        /*
                            0 - tip zapisa (mjesto ili aktuator)
                            1 - ID mjesta
                            2 - 0-senzor , 1-aktuator
                            3 - model uredjaja
                            4 - ID uredjaja
                         */
                        if (podatak.length == 5) {

                            Uredjaj model = this.dohvatiModel(Integer.parseInt(podatak[3]));
                            Mjesto mjesto = this.dohvatiMjesto(Integer.parseInt(podatak[1]));
                            Uredjaj uredjaj;

                            if (model != null && mjesto != null) {
                                if (Integer.parseInt(podatak[2]) == 0 && mjesto.trenutniBrojUredjaja(true) < mjesto.brojSenzora) {
                                    if (mjesto.tip == model.tip || model.tip == 2) {
                                        uredjaj = new Senzor(Integer.parseInt(podatak[4]), model.naziv, model.tip, model.vrsta, model.min, model.max, model.komentar);
                                        mjesto.addUredjaj(uredjaj);
                                        MainView.prikazi(mjesto.naziv + " -> [senzor] " + uredjaj.id + " " + uredjaj.naziv, "info");
                                    } else {
                                        MainView.prikazi("Tip senzora ne odgovara mjestu!", "warning");
                                    }
                                } else if (Integer.parseInt(podatak[2]) == 1 && mjesto.trenutniBrojUredjaja(false) < mjesto.brojAktuatora) {
                                    if (mjesto.tip == model.tip || model.tip == 2) {
                                        uredjaj = new Aktuator(Integer.parseInt(podatak[4]), model.naziv, model.tip, model.vrsta, model.min, model.max, model.komentar);
                                        mjesto.addUredjaj(uredjaj);
                                        MainView.prikazi(mjesto.naziv + " -> [aktuator] " + uredjaj.id + " " + uredjaj.naziv, "info");
                                    } else {
                                        MainView.prikazi("Tip aktuatora ne odgovara mjestu!", "warning");
                                    }

                                } else {
                                    if (Integer.parseInt(podatak[2]) == 0) {
                                        MainView.prikazi(mjesto.naziv + " -> dosegnut je maksimalni broj senzora", "warning");
                                    } else {
                                        MainView.prikazi(mjesto.naziv + " -> dosegnut je maksimalni broj aktuatora", "warning");
                                    }
                                }

                            } else if (model == null) {
                                MainView.prikazi("Ne mogu kreirati uredjaj jer ne postoji model sa ID-om " + podatak[3], "warning");
                            } else {
                                MainView.prikazi("Mjesto sa ID-om " + podatak[1] + " ne postoji.", "warning");
                            }

                        } else {
                            MainView.prikazi("Format rasporeda uredjaja po mjestima nije valjan", "warning");
                        }
                    } else if (Integer.parseInt(podatak[0]) == 1) {

                        /*
                            0 - tip zapisa (mjesto ili aktuator)
                            1 - ID aktuatora
                            2 - ID senzora [više njih]
                         */
                        // MainView.prikazi("Raspored senzora po aktuatorima", "info");
                        if (podatak.length == 3) {
                            Aktuator aktuator = (Aktuator) this.dohvatiUredjaj(Integer.parseInt(podatak[1]));
                            String[] senzori = podatak[2].trim().split(",");
                            for (int i = 0; i < senzori.length; i++) {
                                Senzor senzor = (Senzor) this.dohvatiUredjaj(Integer.parseInt(senzori[i]));
                                if (aktuator != null && senzor != null) {
                                    aktuator.add(senzor);
                                    senzor.add(aktuator);
                                } else {
                                    drugiPokusaj.add(podatak);
                                }
                            }

                            // radi nesta pametno
                        } else {
                            MainView.prikazi("Format rasporeda senzora po aktuatorima nije valjan", "warning");
                        }
                    }
                }

                redak++;
            }
        } catch (IOException | NumberFormatException e) {
            MainView.prikazi("Greska prilikom citanja datoteke: " + e.toString(), "warning");
        }

        if (!drugiPokusaj.isEmpty()) {
            for (String[] podatak : drugiPokusaj) {
                Aktuator aktuator = (Aktuator) this.dohvatiUredjaj(Integer.parseInt(podatak[1]));
                String[] senzori = podatak[2].trim().split(",");
                for (int i = 0; i < senzori.length; i++) {
                    Senzor senzor = (Senzor) this.dohvatiUredjaj(Integer.parseInt(senzori[i]));
                    if (aktuator != null && senzor != null) {
                        aktuator.add(senzor);
                        senzor.add(aktuator);
                    } else {
                        MainView.prikazi("Ne postoji trazeni uredjaj ID " + senzori[i], "warning");
                    }
                }
            }
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

    public Mjesto dohvatiMjesto(int id) {
        for (Mjesto mjesto : mjesta) {
            if (mjesto.id == id) {
                return mjesto;
            }
        }
        return null;
    }

    public Uredjaj dohvatiUredjaj(int id) {
        for (Mjesto mjesto : mjesta) {
            for (Uredjaj u : mjesto.getUredjaji()) {
                if (u.id == id) {
                    return u;
                }
            }
        }
        return null;
    }

    @Override
    public boolean inicijalizacija() {
        FoiIterator iterator = this.createIterator();
        while (iterator.hasNext()) {
            Mjesto m = (Mjesto) iterator.next();
            MainView.prikazi("Inicijaliziram uredjaje za " + m.id + " " + m.naziv, "title");
            m.inicijalizacija();
        }
        return true;
    }

    public void stanjeUredjaja() {

        FoiIterator iterator = this.createIterator();
        while (iterator.hasNext()) {
            Mjesto m = (Mjesto) iterator.next();
            MainView.prikazi("Prikaz stanja uredjaja " + m.id + " " + m.naziv, "title");
            for (Uredjaj u : m.getUredjaji()) {
                UredjajVisitor uv = new UredjajVisitor();
                MainView.prikazi("Uredjaj " + u.naziv + " (" + u.formatVrijednost(u.vrijednost) + "/" + u.formatVrijednost(u.max) + ") " + (int) u.accept(uv) + "%", "info");
            }

        }

    }

    public List<Statistika> statistika() {
        List<Statistika> stat = new ArrayList<>();
        for (Mjesto mjesto : this.mjesta) {
            stat.add(mjesto.stat);
        }

        return stat;
    }

    public static int najveciIdUredjaja() {
        int max = 0;
        for (Mjesto m : mjesta) {
            for (Uredjaj u : m.uredjaji) {
                if (u.id > max) {
                    max = u.id;
                }
            }
        }

        return max;
    }

}
