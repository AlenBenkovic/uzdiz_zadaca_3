/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.builder;

import uzdiz_zadaca_3.composite.FoiZgrada;
import uzdiz_zadaca_3.factory.FoiFactory;
import uzdiz_zadaca_3.factory.MjestoFactory;
import uzdiz_zadaca_3.logs.FoiLogger;
import uzdiz_zadaca_3.utils.Params;

/**
 *
 * @author abenkovic
 */
public class ToF {

    private final FoiLogger logger = FoiLogger.getInstance();
    private final FoiZgrada foiZgrada;

    public ToF(Builder builder) {
        this.foiZgrada = builder.foiZgrada;
    }

    public static class Builder {

        private final FoiLogger logger = FoiLogger.getInstance();
        private final FoiZgrada foiZgrada = new FoiZgrada();

        public Builder() {
            this.logger.init(Params.params.get("-i").toString(), Integer.parseInt(Params.params.get("-brl").toString()));
        }

        public Builder kreirajMjesta() {
            FoiFactory factory = new MjestoFactory();

            factory.kreirajMjesta(Params.params.get("-m").toString()).forEach((m) -> {
                this.foiZgrada.add(m);
            });

            return this;
        }

        public Builder postaviUredjaje() {
            this.foiZgrada.postaviUredjaje();

            return this;
        }

        public Builder inicijalizacija() {
            this.foiZgrada.inicijalizacija();

            return this;
        }

        public Builder opremanjeMjesta() {
            this.foiZgrada.opremanjeMjesta();

            return this;

        }

        public ToF build() {
            return new ToF(this);
        }

    }

    public void radiProvjere() {
        Runnable dretva = () -> {
            int i = 0;
            while (i < Integer.parseInt(Params.params.get("-bcd").toString())) {
                try {
                    i++;
                    Thread.sleep(Integer.parseInt(Params.params.get("-tcd").toString()) * 1000);

                    this.foiZgrada.provjera();
                    this.foiZgrada.stanjeUredjaja();

                } catch (InterruptedException ex) {
                    this.logger.log("Problem u radu sa dretvom", "warning");
                }

            }
            this.foiZgrada.statistika();
        };

        new Thread(dretva).start();

    }

}
