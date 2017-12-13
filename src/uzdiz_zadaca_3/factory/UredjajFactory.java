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
import uzdiz_zadaca_3.composite.Aktuator;
import uzdiz_zadaca_3.composite.Senzor;
import uzdiz_zadaca_3.composite.Uredjaj;
import uzdiz_zadaca_3.logs.FoiLogger;
import uzdiz_zadaca_3.utils.Params;
import uzdiz_zadaca_3.utils.RandomNumber;

/**
 *
 * @author abenkovic
 */
public class UredjajFactory extends FoiFactory {

    FoiLogger logger = FoiLogger.getInstance();
    ArrayList<String[]> popisSenzora;
    ArrayList<String[]> popisAktuatora;

    public UredjajFactory() {
        this.popisAktuatora = new ArrayList<>();
        this.popisSenzora = new ArrayList<>();
        this.ucitajPopisUredjaja(true); // ucitavam senzore
        this.ucitajPopisUredjaja(false); // ucitavam aktuatore
    }

    @Override
    public Uredjaj kreirajUredjaj(boolean isSenzor, int tip) {
        ArrayList<String[]> odgovarajuciUredjaji = new ArrayList<>();
        if (isSenzor) {
            for (String[] senzor : this.popisSenzora) {
                if (Integer.parseInt(senzor[1]) == tip || Integer.parseInt(senzor[1]) == 2) {
                   odgovarajuciUredjaji.add(senzor);
                }

            }
        } else {
            for (String[] aktuator : this.popisAktuatora) {
                if (Integer.parseInt(aktuator[1]) == tip || Integer.parseInt(aktuator[1]) == 2) {
                   odgovarajuciUredjaji.add(aktuator);
                }

            }
        }

        return isSenzor? kreirajSenzor(odgovarajuciUredjaji.get(RandomNumber.dajSlucajniBroj(0, odgovarajuciUredjaji.size()-1))):
                         kreirajAktuator(odgovarajuciUredjaji.get(RandomNumber.dajSlucajniBroj(0, odgovarajuciUredjaji.size()-1)));
    }
    

    public void ucitajPopisUredjaja(boolean isSenzor) {
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

                    if (isSenzor) {
                        this.popisSenzora.add(podatak);
                    } else {
                        this.popisAktuatora.add(podatak);
                    }

                } else {
                    this.logger.log("Format zapisa za " + podatak[0] + " nije valjan.", "warning");
                }
            }
        } catch (IOException e) {
            System.out.println("Greska prilikom citanja datoteke: " + e.toString());
        }

    }

    public Senzor kreirajSenzor(String[] senzor) {
        this.logger.log("[Senzor] " + senzor[0], "info");
        return new Senzor(senzor[0], Integer.parseInt(senzor[1]), Integer.parseInt(senzor[2]), Float.parseFloat(senzor[3]), Float.parseFloat(senzor[4]), senzor.length == 5 ? "-" : senzor[5]);
    }

    public Aktuator kreirajAktuator(String[] aktuator) {
        this.logger.log("[Aktuator] " + aktuator[0], "info");
        return new Aktuator(aktuator[0], Integer.parseInt(aktuator[1]), Integer.parseInt(aktuator[2]), Float.parseFloat(aktuator[3]), Float.parseFloat(aktuator[4]), aktuator.length == 5 ? "-" : aktuator[5]);
    }

}
