/*
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
 *
 * @author it-ra
 */
public class CustomProcess {

    //ATTRIBUTES
    // input
    private final String EXERCISE;
    private final File EXEFILE;
    private final List<String> ARGS;
    //output
    private int exitValue;
    private List<String> stdout;
    private List<String> stderr;

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
    public int getExitValue() {
        return exitValue;
    }

    /**
     * @param exitValue the exitValue to set
     */
    public void setExitValue(int exitValue) {
        this.exitValue = exitValue;
    }

    /**
     * @return the output
     */
    public List<String> getStdout() {
        return stdout;
    }

    /**
     * @param output the output to set
     */
    public void setStdout(List<String> stdout) {
        this.stdout = stdout;
    }

    /**
     * @return the output
     */
    public List<String> getStderr() {
        return stderr;
    }

    /**
     * @param stderr
     */
    public void setStderr(List<String> stderr) {
        this.stderr = stderr;
    }

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

        Log.LOGGER.info(String.format("Ejecutando proceso hijo de %s%n    %s", EXERCISE, command));
        try {
            pr = rt.exec(command);

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
            this.exitValue = pr.waitFor();
            if (this.exitValue == 0) {
                Log.LOGGER.info(String.format("Proceso hijo de %s realizado con éxito", EXERCISE));
            } else {
                Log.LOGGER.warning(String.format("Proceso hijo de %s realizado con errores", EXERCISE));
            }

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
