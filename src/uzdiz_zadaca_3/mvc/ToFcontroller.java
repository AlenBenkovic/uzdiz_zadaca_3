/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.mvc;

import uzdiz_zadaca_3.composite.FoiZgrada;

/**
 *
 * @author abenkovic
 */
public class ToFcontroller {
    private FoiZgrada zgrada;
    private MainView view;
    
    public ToFcontroller(FoiZgrada zgrada, MainView view){
        this.zgrada = zgrada;
        this.view = view;
    }
    
}
