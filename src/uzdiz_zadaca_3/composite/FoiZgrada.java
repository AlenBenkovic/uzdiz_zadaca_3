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
import uzdiz_zadaca_3.logs.FoiLogger;
import uzdiz_zadaca_3.iterator.FoiIterator;
import uzdiz_zadaca_3.visitor.UredjajVisitor;

/**
 *
 * @author abenkovic
 */
public class FoiZgrada implements Foi {

    private final FoiLogger logger = FoiLogger.getInstance();

    private List<Mjesto> mjesta = new ArrayList<>();

    @Override
    public boolean provjera() {
        for (Mjesto m : mjesta) {
            String poruka = "\n-------------------------------------------------------------"
                    + "\n\tRadim provjeru uredjaja za " + m.id + " " + m.naziv
                    + "\n-------------------------------------------------------------\n";
            this.logger.log(poruka, "info");
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
            String poruka = "\n-------------------------------------------------------------"
                    + "\n\tInicijaliziram uredjaje za " + m.id + " " + m.naziv
                    + "\n-------------------------------------------------------------\n";
            this.logger.log(poruka, "info");
            m.inicijalizacija();
        }
        return true;
    }

    public void postaviUredjaje() {
        FoiFactory factory = new UredjajFactory();
        for (Mjesto m : this.mjesta) {
            String poruka = "\n-------------------------------------------------------------"
                    + "\n\tPostavljam uredjaje za " + m.naziv
                    + "\n-------------------------------------------------------------\n";
            this.logger.log(poruka, "info");

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
            String poruka = "\n-------------------------------------------------------------"
                    + "\n\tOpremam mjesto " + m.id + " " + m.naziv
                    + "\n-------------------------------------------------------------\n";
            this.logger.log(poruka, "info");
            m.opremanjeMjesta();
        }
    }
    
    public void stanjeUredjaja(){
        
        FoiIterator iterator = this.createIterator();
        while (iterator.hasNext()) {
            Mjesto m = (Mjesto) iterator.next();
            String poruka = "\n-------------------------------------------------------------"
                    + "\n\tPrikaz stanja uredjaja " + m.id + " " + m.naziv
                    + "\n-------------------------------------------------------------\n";
            for(Uredjaj u: m.getUredjaji()){
                UredjajVisitor uv = new UredjajVisitor();
                poruka = poruka + "\nUredjaj " + u.naziv + " (" + u.formatVrijednost(u.vrijednost) + "/" + u.formatVrijednost(u.max) + ") " + (int)u.accept(uv) + "%";
            }
            this.logger.log(poruka, "info");            
            
        }
        
    }
    
    public void statistika() {
        for (Mjesto mjesto : this.mjesta) {
            String log = "\n-------------------------------------------------------------"
                    + "\n\tStatistika za " + mjesto.naziv
                    + "\n-------------------------------------------------------------\n";
            for (Map.Entry<String, Integer> entry : mjesto.statistikaMjesta.entrySet()) {

                log = log + entry.getKey() + ": " + entry.getValue() + "\n";
            }
            this.logger.log(log, "info");
        }
    }

}
