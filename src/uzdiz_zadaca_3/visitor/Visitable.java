/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.visitor;

/**
 *
 * @author abenkovic
 */
public interface Visitable {

    public float accept(Visitor visitor);

}
