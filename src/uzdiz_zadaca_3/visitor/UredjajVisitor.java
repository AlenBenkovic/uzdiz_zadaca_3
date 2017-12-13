/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.visitor;

import uzdiz_zadaca_3.composite.Aktuator;
import uzdiz_zadaca_3.composite.Senzor;
import uzdiz_zadaca_3.composite.Uredjaj;

/**
 *
 * @author abenkovic
 */
public class UredjajVisitor implements Visitor {

    public UredjajVisitor() {
    }
    
    
    @Override
    public float visit(Senzor senzor) {
        return (senzor.vrijednost/senzor.max)*100;
    }

    @Override
    public float visit(Aktuator aktuator) {
        return (aktuator.vrijednost/aktuator.max)*100;
    }

    @Override
    public float visit(Uredjaj uredjaj) {
        return (uredjaj.vrijednost/uredjaj.max)*100;
    }
    
    
    
    
}
