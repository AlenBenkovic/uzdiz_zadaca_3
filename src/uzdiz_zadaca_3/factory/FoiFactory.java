/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.factory;

import java.util.List;
import uzdiz_zadaca_3.composite.Mjesto;
import uzdiz_zadaca_3.composite.Uredjaj;

/**
 *
 * @author abenkovic
 */
public abstract class FoiFactory {
    
    public void FoiFactory() {

    }
    
    public List<Mjesto> kreirajMjesta(String datoteka) { return null;};

    
    public Uredjaj kreirajUredjaj(boolean isSenzor, int tip){ return null;};
    
    
}
