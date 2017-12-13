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
public interface Visitor {
    
    public float visit(Senzor senzor);
    
    public float visit(Aktuator aktuator);
    
    public float visit(Uredjaj uredjaj);
    
}
