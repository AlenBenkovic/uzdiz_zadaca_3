/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.iterator;

import java.util.Collections;
import java.util.List;
import uzdiz_zadaca_3.composite.Uredjaj;

/**
 *
 * @author abenkovic
 */
public class AlgoritamSlijedno implements FoiIterator{

    List<Uredjaj> uredjaji;
    int position = 0;

    public AlgoritamSlijedno(List<Uredjaj> uredjaji) {
        this.uredjaji = uredjaji;
        Collections.sort(this.uredjaji, (u1, u2) -> u1.id - u2.id);
    }

    @Override
    public boolean hasNext() {
        if (position >= this.uredjaji.size()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void reset() {
        this.position = 0;
    }

    @Override
    public Object next() {
        Uredjaj uredjaj = this.uredjaji.get(position);
        position++;
        return uredjaj;
    }

}
