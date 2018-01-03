/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.chain;

/**
 *
 * @author abenkovic
 */
public abstract class MjestoHandler {

    public MjestoHandler successor;

    public void setSuccessor(MjestoHandler successor) {
        this.successor = successor;
    }

    public abstract void handleRequest(int tipMjesta);

}
