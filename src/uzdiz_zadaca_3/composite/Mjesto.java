/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.composite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import uzdiz_zadaca_3.iterator.FoiIterator;
import uzdiz_zadaca_3.mvc.ToFview;
import uzdiz_zadaca_3.utils.Params;
import uzdiz_zadaca_3.utils.RandomNumber;

/**
 *
 * @author abenkovic
 */
public class Mjesto implements Foi {

    public final String naziv;
    public final int tip;
    public final int brojSenzora;
    public final int brojAktuatora;
    public final int id;

    List<Uredjaj> uredjaji; // koristim samo jednu listu uredjaja radi prikaza pridruzenosti senzora i aktuatora,
    // radi kompozicije ovdje bi radije stavio samo aktuatore
    // ali na taj nacin cu dobiti dupli prikaz pridruzenosti senzora

    HashMap<String, Integer> statistikaMjesta = new HashMap<String, Integer>();

    public Mjesto(int id, String naziv, int tip, int brojSenzora, int brojAktuatora) {
        this.uredjaji = new ArrayList<>();
        this.naziv = naziv;
        this.tip = tip;
        this.brojSenzora = brojSenzora;
        this.brojAktuatora = brojAktuatora;
        this.id = id;
        this.statistikaMjesta.put("Ukupan broj senzora", this.brojSenzora);
        this.statistikaMjesta.put("Ukupan broj aktuatora", this.brojAktuatora);
        this.statistikaMjesta.put("Broj senzora koji nisu prošli inicijalizaciju", 0);
        this.statistikaMjesta.put("Broj aktuatora koji nisu prošli inicijalizaciju", 0);
        this.statistikaMjesta.put("Broj dodanih senzora", 0);
        this.statistikaMjesta.put("Broj dodanih aktuatora", 0);
        this.statistikaMjesta.put("Broj uklonjenih senzora", 0);
        this.statistikaMjesta.put("Broj uklonjenih aktuatora", 0);
    }

    @Override
    public boolean provjera() {
        try {

            // kreiram iterator klase X na temelju korisnickog unosa
            FoiIterator iterator = (FoiIterator) Class.forName(Params.params.get("-alg").toString())
                    .getConstructor(List.class).newInstance(this.uredjaji);

            while (iterator.hasNext()) {
                Uredjaj u = (Uredjaj) iterator.next();
                if (!u.provjera()) { // ako provjera nije uspjela
                    ToFview.prikazi("Radim zamjenu uredjaja", "warning");
                    this.uredjaji.add(u.zamjena());
                    this.uredjaji.remove(u);
                    if (u instanceof Senzor) {
                        int tmp = this.statistikaMjesta.get("Broj uklonjenih senzora");
                        this.statistikaMjesta.put("Broj uklonjenih senzora", tmp + 1);
                        int tmp2 = this.statistikaMjesta.get("Broj dodanih senzora");
                        this.statistikaMjesta.put("Broj dodanih senzora", tmp2 + 1);
                    } else {
                        int tmp = this.statistikaMjesta.get("Broj uklonjenih aktuatora");
                        this.statistikaMjesta.put("Broj uklonjenih aktuatora", tmp + 1);
                        int tmp2 = this.statistikaMjesta.get("Broj dodanih aktuatora");
                        this.statistikaMjesta.put("Broj dodanih aktuatora", tmp2 + 1);
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
            ToFview.prikazi("Greska prilikom ucitavanja klase: " + e.getMessage(), "warning");
        }

        return true;
    }

    public void addUredjaj(Uredjaj uredjaj) {
        this.uredjaji.add(uredjaj);
        if (uredjaj instanceof Senzor) {
            int tmp = this.statistikaMjesta.get("Broj dodanih senzora");
            this.statistikaMjesta.put("Broj dodanih senzora", tmp + 1);
        } else {
            int tmp = this.statistikaMjesta.get("Broj dodanih aktuatora");
            this.statistikaMjesta.put("Broj dodanih aktuatora", tmp + 1);
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

    @Override
    public boolean inicijalizacija() {
        ArrayList<Uredjaj> neispravniUredjaji = new ArrayList<>();
        for (Uredjaj uredjaj : this.uredjaji) {
            if (!uredjaj.inicijalizacija()) {
                ToFview.prikazi(uredjaj.naziv + " [0]", "warning");
                neispravniUredjaji.add(uredjaj);
                if (uredjaj instanceof Senzor) {
                    int tmp = this.statistikaMjesta.get("Broj senzora koji nisu prošli inicijalizaciju");
                    this.statistikaMjesta.put("Broj senzora koji nisu prošli inicijalizaciju", tmp + 1);
                } else {
                    int tmp = this.statistikaMjesta.get("Broj aktuatora koji nisu prošli inicijalizaciju");
                    this.statistikaMjesta.put("Broj aktuatora koji nisu prošli inicijalizaciju", tmp + 1);
                }
            } else {
                ToFview.prikazi(uredjaj.naziv + " [1]", "info");
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
                        ToFview.prikazi("Greska kod dodjele senzora: " + e.toString(), "warning");
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
