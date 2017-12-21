/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.builder;

import java.util.Scanner;
import uzdiz_zadaca_3.composite.FoiZgrada;
import uzdiz_zadaca_3.factory.FoiFactory;
import uzdiz_zadaca_3.factory.MjestoFactory;
import uzdiz_zadaca_3.mvc.MainView;
import static uzdiz_zadaca_3.mvc.MainView.ANSI_ESC;
import static uzdiz_zadaca_3.mvc.MainView.unos;
import uzdiz_zadaca_3.utils.Params;

/**
 *
 * @author abenkovic
 */
public class ToF {

    private final FoiZgrada foiZgrada;
    private final MainView view;

    public ToF(Builder builder) {
        this.foiZgrada = builder.foiZgrada;
        this.view = builder.view;
    }

    public static class Builder {

        private final FoiZgrada foiZgrada = new FoiZgrada();
        private final MainView view = new MainView();

        public Builder() {
            // this.logger.init(Params.params.get("-i").toString(), Integer.parseInt(Params.params.get("-brl").toString()));
        }

        public Builder kreirajMjesta() {
            FoiFactory factory = new MjestoFactory();

            factory.kreirajMjesta(Params.params.get("-m").toString()).forEach((m) -> {
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
        MainView.prikaziNaredbe();
        boolean status = false;
        while (!status) {
            unos("Unesite naredbu: ");
            Scanner scanner = new Scanner(System.in);
            String in = scanner.nextLine();

            if (in.compareTo("I") == 0) {
                status = true;

            } else {
                MainView.cleanLine();
            }
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
                    MainView.prikazi("Problem u radu sa dretvom", "warning");
                }

            }
            this.foiZgrada.statistika();
        };

        new Thread(dretva).start();

    }

}
