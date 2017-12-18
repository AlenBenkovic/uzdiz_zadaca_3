/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3;

import uzdiz_zadaca_3.builder.ToF;
import uzdiz_zadaca_3.mvc.ToFview;
import uzdiz_zadaca_3.utils.Params;

/**
 *
 * @author abenkovic
 */
public class Uzdiz_zadaca_3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (Params.checkArgs(args)) {
            ToF tof = new ToF.Builder()
                    .inicijalizirajSucelje()
                    .kreirajMjesta()
                    .ucitajModeleUredjaja()
                    //.postaviUredjaje()
                    /*.inicijalizacija()
                    .opremanjeMjesta()*/
                    .build();
            
            //tof.radiProvjere();
            
        } else {
            ToFview.prikazi("Parametri nisu ispravni!", "warning");
            System.exit(0);
        }
    }

}
