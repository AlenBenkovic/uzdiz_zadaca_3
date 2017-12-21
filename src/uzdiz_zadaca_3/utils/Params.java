/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.utils;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author abenkovic
 */
public class Params {

    public static HashMap params = new HashMap();

    public static boolean checkArgs(String[] args) {
        boolean status = false;

        if (args[0].equals("--help")) {

            System.out.println("Opcije:\n");
            System.out.println("-g sjeme za generator slučajnog broja (u intervalu 100 - 65535).\n"
                    + " Ako nije upisana opcija, uzima se broj milisekundi u trenutnom vremenu na bazi"
                    + " njegovog broja sekundi i broja milisekundi.\n\n"
                    + "-m naziv datoteke mjesta\n\n"
                    + "-s naziv datoteke senzora\n\n"
                    + "-a naziv datoteke aktuatora\n\n"
                    + "-r naziv datoteke rasporeda\n\n"
                    + "-tcd trajanje ciklusa dretve u sek.\n Ako nije upisana opcija, uzima se slučajni broj u intervalu 1 - 17.\n\n"
                    + "-br broj redaka na ekranu (24-40). Ako nije upisana opcija, uzima se 24\n\n"
                    + "-bs broj stupaca na ekranu (80-160). Ako nije upisana opcija, uzima se 80.\n\n"
                    + "-brk broj redaka na ekranu za unos komandi (2-5). Ako nije upisana opcija, uzima se 2.\n\n"
                    + "-pi prosječni % ispravnosti uređaja (0-100). Ako nije upisana opcija, uzima se 50.\n\n"
                    + "");
            System.exit(0);
            
        } else {

            for (int i = 0; i < args.length - 1; i = i + 2) {
                checkParams(args[i], args[i + 1]);
            }

            if (!params.containsKey("-br")) {
                params.put("-br", 24);

            }
            
            if (!params.containsKey("-bs")) {
                params.put("-bs", 80);

            }
            
            if (!params.containsKey("-brk")) {
                params.put("-brk", 2);

            }
            
            if (!params.containsKey("-pi")) {
                params.put("-pi", 50);

            }
            
            if (!params.containsKey("-g")) {
                // broj milisekundi od trenutnog vremena, a ne od 1.1.1970
                params.put("-g", Calendar.getInstance().get(Calendar.MILLISECOND));

            }

            if (!params.containsKey("-tcd")) {
                params.put("-tcd", RandomNumber.dajSlucajniBroj(1, 17));
            }

            status = (params.containsKey("-m")
                    && params.containsKey("-s")
                    && params.containsKey("-a")
                    && params.containsKey("-r"));

            System.out.println(Collections.singletonList(params));

        }

        return status;
    }

    private static boolean checkParams(String flag, String value) {
        boolean status = false;
        Pattern pattern;
        Matcher matcher;
        switch (flag) {
            case "-g":
                status = Integer.parseInt(value) >= 100 && Integer.parseInt(value) <= 65535;
                if (status) {
                    RandomNumber.setSeed(Long.getLong(value));
                }
                break;
            case "-m":
            case "-s":
            case "-a":
            case "-r":
                if(value.contains(" ")){
                    status = false;
                } else {
                    status = true;
                }
                break;
            case "-br":
                status = Integer.parseInt(value) >= 24 && Integer.parseInt(value) <= 40;
                break;
            case "-bs":
                status = Integer.parseInt(value) >= 80 && Integer.parseInt(value) <= 160;
                break;
            case "-brk":
                status = Integer.parseInt(value) >= 2 && Integer.parseInt(value) <= 5;
                break;
            case "-pi":
                status = Integer.parseInt(value) >= 0 && Integer.parseInt(value) <= 100;
                break;
            case "-tcd":
                pattern = Pattern.compile("\\d*");
                matcher = pattern.matcher(value);
                status = matcher.matches();
                break;
                
        }
        if (status) {
            params.put(flag, value);
        }

        return status;
    }
    
    public static void setPi(int i){
        params.put("-pi", i);
    }

}
