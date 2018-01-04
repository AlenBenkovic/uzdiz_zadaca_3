/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.builder;

import uzdiz_zadaca_3.composite.FoiZgrada;
import uzdiz_zadaca_3.factory.FoiFactory;
import uzdiz_zadaca_3.factory.MjestoFactory;
import uzdiz_zadaca_3.mvc.MainController;
import uzdiz_zadaca_3.mvc.MainView;
import uzdiz_zadaca_3.utils.Params;

/**
 *
 * @author abenkovic
 */
public class ToF {

    private FoiZgrada foiZgrada;
    private final MainView view;

    public ToF(Builder builder) {
        this.foiZgrada = builder.foiZgrada;
        this.view = builder.view;
    }

    public static class Builder {

        private FoiZgrada foiZgrada = new FoiZgrada();
        private final MainView view = new MainView();

        public Builder() {
            // this.logger.init(Params.params.get("-i").toString(), Integer.parseInt(Params.params.get("-brl").toString()));
        }

        public Builder kreirajMjesta() {
            FoiFactory factory = new MjestoFactory();

            factory.kreirajMjesta(Params.params.get("-m").toString()).forEach((m) -> {
                m.zgrada = this.foiZgrada; // referenca do zgrade kako bi mogao naci najveci id u zgradi
                this.foiZgrada.add(m);
            });

            return this;
        }

        public Builder ucitajModeleUredjaja() {
            this.foiZgrada.ucitajModeleUredjaja();
            return this;
        }

        public Builder ucitajraspored() {
            this.foiZgrada.ucitajRaspored();
            return this;
        }

        public Builder inicijalizacija() {
            this.foiZgrada.inicijalizacija();

            return this;
        }

        public ToF build() {
            return new ToF(this);
        }

    }

    public void pokreniProgram() {
        MainView.pressAnyKey();
        MainView mview = new MainView();
        MainController controller = new MainController(foiZgrada, mview);
        controller.prikaziNaredbe();
        controller.cekajNaredbu();
    }

}
