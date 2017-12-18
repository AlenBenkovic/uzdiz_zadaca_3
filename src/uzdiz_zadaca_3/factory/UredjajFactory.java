/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.factory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import uzdiz_zadaca_3.composite.Aktuator;
import uzdiz_zadaca_3.composite.Senzor;
import uzdiz_zadaca_3.composite.Uredjaj;
import uzdiz_zadaca_3.mvc.ToFview;
import uzdiz_zadaca_3.utils.Params;
import uzdiz_zadaca_3.utils.RandomNumber;

/**
 *
 * @author abenkovic
 */
public class UredjajFactory extends FoiFactory {

    ArrayList<String[]> popisSenzora;
    ArrayList<String[]> popisAktuatora;
    List<Uredjaj> uredjajModeli;

    public UredjajFactory() {

        this.uredjajModeli = new ArrayList<>();
        // this.ucitajModeleUredjaja(true); // ucitavam senzore
        // this.ucitajModeleUredjaja(false); // ucitavam aktuatore
    }

    @Override
    public Uredjaj kreirajUredjaj(boolean isSenzor, int tip) {
        ArrayList<String[]> odgovarajuciUredjaji = new ArrayList<>();
        if (isSenzor) {
            for (String[] senzor : this.popisSenzora) {
                if (Integer.parseInt(senzor[2]) == tip || Integer.parseInt(senzor[2]) == 2) {
                    odgovarajuciUredjaji.add(senzor);
                }

            }
        } else {
            for (String[] aktuator : this.popisAktuatora) {
                if (Integer.parseInt(aktuator[2]) == tip || Integer.parseInt(aktuator[2]) == 2) {
                    odgovarajuciUredjaji.add(aktuator);
                }

            }
        }

        return isSenzor ? kreirajSenzor(odgovarajuciUredjaji.get(RandomNumber.dajSlucajniBroj(0, odgovarajuciUredjaji.size() - 1)))
                : kreirajAktuator(odgovarajuciUredjaji.get(RandomNumber.dajSlucajniBroj(0, odgovarajuciUredjaji.size() - 1)));
    }

    @Override
    public List<Uredjaj> ucitajModeleUredjaja(boolean isSenzor) {
        try {
            FileReader fr = new FileReader(isSenzor ? Params.params.get("-s").toString() : Params.params.get("-a").toString());
            BufferedReader br = new BufferedReader(fr);
            String s;
            int brojAtributa = 0;
            while ((s = br.readLine()) != null) {
                String[] podatak = s.trim().split(";");
                if (brojAtributa == 0) { //prva linija je sam opis podataka i ona je mjerodavna za broj atributa
                    brojAtributa = podatak.length;
                } else if (podatak.length == brojAtributa || podatak.length == brojAtributa - 1) {

                    if (!postojiUredjaj(Integer.parseInt(podatak[0]), uredjajModeli)) {
                        if (isSenzor) {
                            this.uredjajModeli.add(this.kreirajSenzor(podatak));
                            // this.popisSenzora.add(podatak);
                        } else {
                            this.uredjajModeli.add(this.kreirajAktuator(podatak));
                            // this.popisAktuatora.add(podatak);
                        }
                    } else {
                        ToFview.prikazi("Uredjaj sa ID " + podatak[0] + " vec postoji.", "warning");
                    }

                } else {
                    ToFview.prikazi("Format zapisa za " + podatak[1] + " nije valjan.", "warning");
                }
            }
        } catch (IOException e) {
            ToFview.prikazi("Greska prilikom citanja datoteke: " + e.toString(), "warning");
        }
        
        return uredjajModeli;
    }

    public Senzor kreirajSenzor(String[] senzor) {
        ToFview.prikazi("[Senzor] " + senzor[0] + " - " + senzor[1], "info");
        return new Senzor(Integer.parseInt(senzor[0]), senzor[1], Integer.parseInt(senzor[2]), Integer.parseInt(senzor[3]), Float.parseFloat(senzor[4]), Float.parseFloat(senzor[5]), senzor.length == 6 ? "-" : senzor[6]);
    }

    public Aktuator kreirajAktuator(String[] aktuator) {
        ToFview.prikazi("[Aktuator] " + aktuator[0] + " - " + aktuator[1], "info");
        return new Aktuator(Integer.parseInt(aktuator[0]), aktuator[1], Integer.parseInt(aktuator[2]), Integer.parseInt(aktuator[3]), Float.parseFloat(aktuator[4]), Float.parseFloat(aktuator[5]), aktuator.length == 6 ? "-" : aktuator[6]);
    }

    private boolean postojiUredjaj(int id, List<Uredjaj> uredjaj) {
        boolean status = false;
        for (Uredjaj u : uredjaj) {
            if (u.id == id) {
                status = true;
            }
        }

        return status;
    }

}
