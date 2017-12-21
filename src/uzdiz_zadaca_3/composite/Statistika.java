/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.composite;

/**
 *
 * @author abenkovic
 */
public class Statistika {

    public String nazivMjesta;
    public int idMjesta;
    public int ukupanBrojSenzora = 0;
    public int ukupanBrojAktuatora = 0;
    public int senzoriInitNeuspjeh = 0;
    public int aktuatoriInitNeuspjeh = 0;
    public int brojDodanihSenzora = 0;
    public int brojDodanihAktuatora = 0;
    public int brojUklonjenihSenzora = 0;
    public int brojUklonjenihAktuatora = 0;

    public Statistika(String nazivMjesta, int idMjesta, int totalSenzori, int totalAktuatori) {
        this.nazivMjesta = nazivMjesta;
        this.idMjesta = idMjesta;
        this.ukupanBrojSenzora = totalSenzori;
        this.ukupanBrojAktuatora = totalAktuatori;
    }

}
