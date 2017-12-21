/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.composite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import uzdiz_zadaca_3.iterator.AlgoritamSlijedno;
import uzdiz_zadaca_3.iterator.FoiIterator;
import uzdiz_zadaca_3.mvc.MainView;
import uzdiz_zadaca_3.utils.Params;
import uzdiz_zadaca_3.utils.RandomNumber;

/**
 *
 * @author abenkovic
 */
public class Mjesto implements Foi, Serializable {

    public final String naziv;
    public final int tip;
    public final int brojSenzora;
    public final int brojAktuatora;
    public final int id;
    public final Statistika stat;

    List<Uredjaj> uredjaji; // koristim samo jednu listu uredjaja radi prikaza pridruzenosti senzora i aktuatora,
    // radi kompozicije ovdje bi radije stavio samo aktuatore
    // ali na taj nacin cu dobiti dupli prikaz pridruzenosti senzora


    public Mjesto(int id, String naziv, int tip, int brojSenzora, int brojAktuatora) {
        this.uredjaji = new ArrayList<>();
        this.naziv = naziv;
        this.tip = tip;
        this.brojSenzora = brojSenzora;
        this.brojAktuatora = brojAktuatora;
        this.id = id;
        stat = new Statistika(this.naziv, this.id, this.brojSenzora, this.brojAktuatora);
    }

    @Override
    public boolean provjera() {
        try {

            /* kreiram iterator klase X na temelju korisnickog unosa
            FoiIterator iterator = (FoiIterator) Class.forName(Params.params.get("-alg").toString())
                    .getConstructor(List.class).newInstance(this.uredjaji);*/
            
            FoiIterator iterator = new AlgoritamSlijedno(uredjaji);

            while (iterator.hasNext()) {
                Uredjaj u = (Uredjaj) iterator.next();
                if (!u.provjera()) { // ako provjera nije uspjela
                    MainView.prikazi("Radim zamjenu uredjaja", "warning");
                    this.uredjaji.add(u.zamjena());
                    this.uredjaji.remove(u);
                    if (u instanceof Senzor) {
                        this.stat.brojUklonjenihSenzora++;
                        this.stat.brojDodanihSenzora++;
                    } else {
                        this.stat.brojUklonjenihAktuatora++;
                        this.stat.brojDodanihAktuatora++;
                    }
                }

                if (u instanceof Aktuator) {
                    boolean obaviRadnju = false;
                    for (Senzor s : ((Aktuator) u).getSenzori()) {
                        if (s.imaNovuVrijednost && s.status > 0 && ((Aktuator) u).status > 0) {
                            obaviRadnju = true;
                        }
                    }
                    if (obaviRadnju) {
                        ((Aktuator) u).obaviRadnju();
                    }

                }
            }
        } catch (Exception e) {
            MainView.prikazi("Greska prilikom ucitavanja klase: " + e.getMessage(), "warning");
        }

        return true;
    }

    public void addUredjaj(Uredjaj uredjaj) {
        this.uredjaji.add(uredjaj);
        if (uredjaj instanceof Senzor) {
            this.stat.brojDodanihSenzora++;
        } else {
            this.stat.brojDodanihAktuatora++;
        }
    }

    public void removeUredjaj(Uredjaj uredjaj) {
        this.uredjaji.remove(uredjaj);
    }

    public List<Uredjaj> getUredjaji() {
        return uredjaji;
    }

    public void setUredjaji(List<Uredjaj> uredjaji) {
        this.uredjaji = uredjaji;
    }

    public int trenutniBrojUredjaja(boolean isSenzor) {
        int senzori = 0;
        int aktuatori = 0;
        for (Uredjaj uredjaj : uredjaji) {
            if (uredjaj instanceof Senzor) {
                senzori++;
            } else {
                aktuatori++;
            }
        }

        return isSenzor ? senzori : aktuatori;
    }

    @Override
    public boolean inicijalizacija() {
        ArrayList<Uredjaj> neispravniUredjaji = new ArrayList<>();
        for (Uredjaj uredjaj : this.uredjaji) {
            if (!uredjaj.inicijalizacija()) {
                MainView.prikazi(uredjaj.naziv + " [0]", "warning");
                neispravniUredjaji.add(uredjaj);
                if (uredjaj instanceof Senzor) {
                    this.stat.senzoriInitNeuspjeh++;
                } else {
                    this.stat.aktuatoriInitNeuspjeh++;
                }
            } else {
                MainView.prikazi(uredjaj.naziv + " [1]", "info");
            }
        }

        for (Uredjaj neispravniUredjaj : neispravniUredjaji) {
            this.uredjaji.remove(neispravniUredjaj);
        }
        return true;
    }

    public void opremanjeMjesta() {
        ArrayList<Senzor> senzori = new ArrayList<>();
        ArrayList<Aktuator> aktuatori = new ArrayList<>();

        for (Uredjaj uredjaj : this.uredjaji) {
            if (uredjaj instanceof Aktuator) {
                aktuatori.add((Aktuator) uredjaj);

            } else if (uredjaj instanceof Senzor) {
                senzori.add((Senzor) uredjaj);

            }
        }

        for (Aktuator aktuator : aktuatori) {
            for (int i = 1; i <= RandomNumber.dajSlucajniBroj(1, this.brojSenzora); i++) {
                if (!senzori.isEmpty()) {
                    try {
                        Senzor senzor = senzori.get(RandomNumber.dajSlucajniBroj(0, senzori.size() - 1));

                        // ukoliko je aktuatoru vec pridruzen senzor, preskoci ga
                        if (!aktuator.getSenzori().contains(senzor)) {
                            senzor.add(aktuator);
                            aktuator.add(senzor);
                        }

                    } catch (Exception e) {
                        MainView.prikazi("Greska kod dodjele senzora: " + e.toString(), "warning");
                    }

                }

            }

        }

        this.pridruzenostUredjaja();
    }

    public void pridruzenostUredjaja() {
        for (Uredjaj uredjaj : this.uredjaji) {
            uredjaj.pridruzenostUredjaja();
        }
    }

}
