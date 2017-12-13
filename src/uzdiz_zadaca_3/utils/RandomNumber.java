/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.utils;

import java.util.Random;

/**
 *
 * @author abenkovic
 */
public class RandomNumber {
    public static Random rand = new Random();
    
    
    public static void setSeed(long seed){
        RandomNumber.rand.setSeed(seed);
    }
    

    public static int dajSlucajniBroj(int odBroja, int doBroja) {
        return RandomNumber.rand.nextInt((doBroja - odBroja) + 1) + odBroja;
    }

    public static float dajSlucajniBroj(float odBroja, float doBroja) {
        return odBroja + RandomNumber.rand.nextFloat() * (doBroja - odBroja);   
    }

}
