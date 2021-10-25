/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.itrafa.dam.psp.ud1.t1;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author it-ra
 */
public class Log {

    protected static final Logger LOGGER = Logger.getLogger(Log.class.getSimpleName());
    private static final File FILELOG = new File("logs//dam_psp_ud1_t1.log");

    static {

        try {
        File path = new File(FILELOG.getPath());
        path.mkdirs();
        FILELOG.delete();
        FILELOG.createNewFile();
            
        } catch (IOException ex) {
            LOGGER.severe(String.format("Error LOGGER: %s%n", ex));
        }
        Locale.setDefault(new Locale("en", "EN"));
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%4$6s] [%1$tF %1$tT] %2$s:%n--> %5$s%n");

        FileHandler fileHandler;

        try {
            fileHandler = new FileHandler(FILELOG.getPath(), true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

        } catch (IOException | SecurityException ex) {
            LOGGER.severe(String.format("Error LOGGER: %s%n", ex));
        }

        Log.LOGGER.setUseParentHandlers(false);
    }

    protected static void endLogger() {
        for (Handler h : LOGGER.getHandlers()) {
            // FAIL IF USER KILL (.lck file)
            h.close();   //must call h.close or a .LCK file will remain.
        }

    }

}
