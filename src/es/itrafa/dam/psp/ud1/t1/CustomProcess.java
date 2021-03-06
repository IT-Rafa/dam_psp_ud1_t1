/*
 * File Name: CustomProcess.java
 * Project: dam_psp_ud1_t1
 *
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.itrafa.dam.psp.ud1.t1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para ejecutar proceso y capturar datos salida
 *
 * @author it-ra
 */
public class CustomProcess {

    //ATTRIBUTES
    // input
    private final String EXERCISE; // Descripción ejercicio
    private final File EXEFILE;// Archivo ejecutable
    private final List<String> ARGS; // Argumentos enviados
    //output
    private Integer exitValue; // valor de salida del programa (0 == exit)
    private final List<String> stdout; // salida información
    private final List<String> stderr; // salida errores

    // CONSTRUCTOR
    public CustomProcess(String ejercicio, File exeFile, List<String> args) {
        this.EXERCISE = ejercicio;
        this.EXEFILE = exeFile;
        this.ARGS = args;
        this.stdout = new ArrayList<>();
        this.stderr = new ArrayList<>();
    }

    /**
     * @return the exitValue
     */
    public Integer getExitValue() {
        return exitValue;
    }

    /**
     * @return the output
     */
    public List<String> getStdout() {
        return stdout;
    }

    /**
     * @return the output
     */
    public List<String> getStderr() {
        return stderr;
    }

    /**
     * Envia el proceso al sistema y captura la información de salida
     */
    public void runProcess() {
        Runtime rt;
        rt = Runtime.getRuntime();

        Process pr;
        InputStream inpStr;
        InputStream errStr;
        InputStreamReader inpStrReader;
        InputStreamReader errStrReader;
        BufferedReader inpBuffReader;
        BufferedReader errBuffReader;

        String command = EXEFILE.getPath() + " ";
        for (String arg : ARGS) {
            command = command.concat(arg + " ");

        }

        Log.LOGGER.info(String.format("Ejecutando proceso hijo de %s:%n    %s", EXERCISE, command));
        try {
            // Ejecuta proceso hijo
            pr = rt.exec(command);

            // Captura las salidas que se van produciendo (stdout y stderr)
            inpStr = pr.getInputStream();
            inpStrReader = new InputStreamReader(inpStr);
            inpBuffReader = new BufferedReader(inpStrReader);

            errStr = pr.getErrorStream();
            errStrReader = new InputStreamReader(errStr);
            errBuffReader = new BufferedReader(errStrReader);

            String stdLine;
            String errLine;
            while ((stdLine = inpBuffReader.readLine()) != null) {
                stdout.add(stdLine);
            }
            while ((errLine = errBuffReader.readLine()) != null) {
                stdout.add(errLine);
            }

            // Captura valor retorno al final del programa
            this.exitValue = pr.waitFor();
            if (this.exitValue == 0) {
                Log.LOGGER.info(String.format("Proceso hijo de %s realizado con éxito", EXERCISE));
            } else {
                Log.LOGGER.warning(String.format("Proceso hijo de %s realizado pero devolvió error %d", EXERCISE, this.exitValue));
            }

            Log.LOGGER.info(String.format("Proceso hijo de %s eliminado", EXERCISE));
            pr.destroy();

        } catch (IOException ex) { // for exec
            Log.LOGGER.severe(String.format("Error de entrada/salida al intentar ejecutar el proceso hijo de %s", EXERCISE));

        } catch (SecurityException ex) { // for exec
            Log.LOGGER.severe(String.format("Error por no tener permisos para ejecutar el proceso hijo de %s", EXERCISE));

        } catch (NullPointerException ex) { // for exec
            Log.LOGGER.severe(String.format("Error por comando no especificado para el proceso hijo de %s", EXERCISE));

        } catch (IllegalArgumentException ex) { // for exec
            Log.LOGGER.severe(String.format("Error por comando erróneo para el proceso hijo de %s", EXERCISE));

        } catch (InterruptedException ex) {// for waitfor
            Log.LOGGER.severe(String.format("Error al ejecutar proceso hijo de %s", EXERCISE));
        }

    }
}
