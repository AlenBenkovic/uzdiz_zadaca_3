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
import uzdiz_zadaca_3.composite.Mjesto;
import uzdiz_zadaca_3.mvc.MainView;

/**
 *
 * @author abenkovic
 */
public class MjestoFactory extends FoiFactory {


    @Override
    public List<Mjesto> kreirajMjesta(String datoteka) {
        return this.kreiramMjestaIzDatoteke(datoteka);
    }

    private List<Mjesto> kreiramMjestaIzDatoteke(String datoteka) {
        try {
            FileReader fr = new FileReader(datoteka);
            BufferedReader br = new BufferedReader(fr);
            List<Mjesto> mjesta = new ArrayList<Mjesto>();
            String s;
            int brojAtributa = 0;
            while ((s = br.readLine()) != null) {

                String[] podatak = s.trim().split(";");

                if (brojAtributa == 0) { //prva linija je sam opis podataka i ona je mjerodavna za broj atributa

                    brojAtributa = podatak.length;

                } else if (podatak.length == brojAtributa) {

                    if (postojiMjesto(Integer.parseInt(podatak[0]), mjesta)) {

                        MainView.prikazi("Mjesto " + podatak[1] + " već postoji.", "warning");

                    } else {

                        MainView.prikazi("Kreiram mjesto " + podatak[1] + " .", "info");
                        Mjesto mjesto = this.kreirajMjesto(podatak);
                        mjesta.add(mjesto);

                    }

                } else {

                    MainView.prikazi("Format zapisa za mjesto " + podatak[1] + " nije valjan.", "warning");

                }
            }

            return mjesta;

        } catch (IOException e) {

            System.out.println("Greska prilikom citanja datoteke: " + e.toString());
            return null;
        }

    }

    public Mjesto kreirajMjesto(String[] podaciMjesta) {
        Mjesto mjesto = null;
        try {
            mjesto = new Mjesto(Integer.parseInt(podaciMjesta[0]),
                    podaciMjesta[1],
                    Integer.parseInt(podaciMjesta[2]),
                    Integer.parseInt(podaciMjesta[3]),
                    Integer.parseInt(podaciMjesta[4]));

        } catch (Exception e) {
            MainView.prikazi("Format datoteke za mjesta nije valjan!", "warning");
            System.exit(0);
        }

        return mjesto;
    }

    private boolean postojiMjesto(int id, List<Mjesto> mjesta) {
        boolean status = false;
        for (Mjesto m : mjesta) {
            if (m.id == id) {
                status = true;
            }
        }

        return status;
    }

}
