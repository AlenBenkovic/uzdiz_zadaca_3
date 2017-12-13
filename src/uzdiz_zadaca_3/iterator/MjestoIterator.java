/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.iterator;

import java.util.Collections;
import java.util.List;
import uzdiz_zadaca_3.composite.Mjesto;

/**
 *
 * @author abenkovic
 */
public class MjestoIterator implements FoiIterator {

    List<Mjesto> mjesta;
    int position = 0;

    public MjestoIterator(List<Mjesto> mjesta) {

        this.mjesta = mjesta;
        Collections.sort(this.mjesta, (m1, m2)-> m1.id - m2.id);

    }

    
    @Override
    public boolean hasNext() {
        if (position >= this.mjesta.size()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Mjesto next() {
        Mjesto mjesto = this.mjesta.get(position);
        position++;
        return mjesto;
    }

    @Override
    public void reset() {
        this.position = 0;
    }
       

}
