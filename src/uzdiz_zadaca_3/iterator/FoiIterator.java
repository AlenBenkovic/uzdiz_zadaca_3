/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.iterator;

/**
 *
 * @author abenkovic
 */
public interface FoiIterator {
    
    boolean hasNext();
    
    void reset();
    
    Object next();
    
}
