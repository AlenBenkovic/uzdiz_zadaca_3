/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uzdiz_zadaca_3.logs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 *
 * @author abenkovic
 */
public class FoiLogger {

    private static volatile FoiLogger INSTANCE;
    private Logger logger = Logger.getLogger(FoiLogger.class.getName());
    private FileHandler fileHandler;
    private ConsoleHandler consoleHandler;
    private LogsFormatter formatter;
    private String datoteka;
    private boolean isFileHandlerOn = false;
    private ArrayList<Zapis> queue = new ArrayList();
    private int brojLinija;

    static {
        INSTANCE = new FoiLogger();
    }

    private FoiLogger() {

    }

    public static FoiLogger getInstance() {
        return INSTANCE;
    }

    public void init(String datoteka, int brojLinija) {
        this.datoteka = datoteka;
        this.brojLinija = brojLinija;
        
        formatter = new LogsFormatter();
        logger.setUseParentHandlers(false);
        
        this.consoleHandler = new ConsoleHandler();
        this.consoleHandler.setFormatter(formatter);
        
        logger.addHandler(this.consoleHandler);

    }

    public void log(String poruka, String tip) {
        this.queue.add(new Zapis(poruka, tip));
        this.logIt(poruka, tip);
        if (this.queue.size() == this.brojLinija && !this.isFileHandlerOn) {
            this.emptyQueue();
        }

    }

    public void logIt(String poruka, String tip) {
        switch (tip) {
            case "info":
                logger.info(poruka);
                break;
            case "warning":
                logger.warning(poruka);
                break;
            case "fine":
                logger.fine(poruka);
                break;
        }

    }

    public void emptyQueue() {
        try {

            fileHandler = new FileHandler(this.datoteka, true);
            fileHandler.setFormatter(formatter);

        } catch (SecurityException | IOException e) {
            System.err.println("Greska prilikom I/O logova");
        }
        
        this.logger.addHandler(fileHandler);
        this.isFileHandlerOn = true;
        this.logger.removeHandler(consoleHandler);
        
        for (Zapis z:queue) {
            this.logIt(z.poruka, z.tip);
        }
        this.queue.clear();
        
        this.logger.removeHandler(fileHandler);
        this.fileHandler.close();
        this.isFileHandlerOn = false;
        this.logger.addHandler(consoleHandler);
        
        
    }

    public class Zapis {

        String poruka;
        String tip;

        public Zapis(String poruka, String tip) {
            this.poruka = poruka;
            this.tip = tip;
        }

    }

}
