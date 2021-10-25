/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.itrafa.dam.psp.ud1.t1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author it-ra
 */
public class ExercisesFake {

    static void runExercise1(File vboxManageFile) {
        // Proceso OK sin ninguna salida
        Log.LOGGER.info("Iniciando Ejercicio 1FAKE ****");
        CustomProcess ej;
        List<String> names = new ArrayList<>();

        List<String> args = new ArrayList<>();
        args.add("D:\\source\\dam\\psp\\dam_psp_ud1_t1\\docs\\emptyTest.txt");

        ej = new CustomProcess("ejercicio 1F", vboxManageFile, args);
        ej.runProcess();

        if (ej.getExitValue() == null) {

        } else if (ej.getExitValue() != 0) {
            UI.showFullOutput(ej);

        } else {
            if (ej.getStdout().isEmpty()) {
                UI.showEmptyList("máquinas virtuales");

            } else {
                // Create a Pattern object
                Pattern r = Pattern.compile("\"(.*)\".*$");

                // Now create matcher object.
                for (String line : ej.getStdout()) {
                    Matcher m = r.matcher(line);
                    if (m.find()) {
                        names.add(m.group(1));
                    }
                }

                if (names.isEmpty()) {
                    UI.showNoNamesError();

                } else {
                    UI.showList("Lista máquinas virtuales activas", names);

                }
            }
        }
    }

    static void runExercise2(File vboxManageFile) {
        // Proceso OK con salida OK
        Log.LOGGER.info("Iniciando Ejercicio 2FAKE ****");
        CustomProcess ej;
        List<String> names = new ArrayList<>();

        List<String> args = new ArrayList<>();
        args.add("D:\\source\\dam\\psp\\dam_psp_ud1_t1\\docs\\test.txt");

        ej = new CustomProcess("ejercicio 1F", vboxManageFile, args);
        ej.runProcess();

        if (ej.getExitValue() == null) {

        } else if (ej.getExitValue() != 0) {
            UI.showFullOutput(ej);

        } else {
            if (ej.getStdout().isEmpty()) {
                UI.showEmptyList("máquinas virtuales");

            } else {
                // Create a Pattern object
                Pattern r = Pattern.compile("\"(.*)\".*$");

                // Now create matcher object.
                for (String line : ej.getStdout()) {
                    Matcher m = r.matcher(line);
                    if (m.find()) {
                        names.add(m.group(1));
                    }
                }

                if (names.isEmpty()) {
                    UI.showNoNamesError();

                } else {
                    UI.showList("Lista máquinas virtuales activas", names);

                }
            }
        }
    }

    static void runExercise3(File vboxManageFile) {
        // Proceso KO 
        Log.LOGGER.info("Iniciando EjercicioFAKE 3 ****");
        CustomProcess ej;
        List<String> names = new ArrayList<>();

        List<String> args = new ArrayList<>();
        args.add("D:\\source\\dam\\psp\\dam_psp_ud1_t1\\docs\\test2.txt");

        ej = new CustomProcess("ejercicio 1F", vboxManageFile, args);
        ej.runProcess();

        if (ej.getExitValue() == null) {

        } else if (ej.getExitValue() != 0) {
            UI.showFullOutput(ej);

        } else {
            if (ej.getStdout().isEmpty()) {
                UI.showEmptyList("máquinas virtuales");

            } else {
                // Create a Pattern object
                Pattern r = Pattern.compile("\"(.*)\".*$");

                // Now create matcher object.
                for (String line : ej.getStdout()) {
                    Matcher m = r.matcher(line);
                    if (m.find()) {
                        names.add(m.group(1));
                    }
                }

                if (names.isEmpty()) {
                    UI.showNoNamesError();

                } else {
                    UI.showList("Lista máquinas virtuales activas", names);

                }
            }
        }
    }

    static void runExercise4(File vboxManageFile) {
        // Proceso OK con salida OK
        Log.LOGGER.info("Iniciando Ejercicio 4FAKE ****");
        CustomProcess ej;
        List<String> names = new ArrayList<>();

        List<String> args = new ArrayList<>();
        args.add("D:\\source\\dam\\psp\\dam_psp_ud1_t1\\docs\\test2.txt");

        ej = new CustomProcess("ejercicio 1F", vboxManageFile, args);
        ej.runProcess();

        if (ej.getExitValue() == null) {

        } else if (ej.getExitValue() != 0) {
            UI.showFullOutput(ej);

        } else {
            if (ej.getStdout().isEmpty()) {
                UI.showList("Lista máquinas virtuales activas", names);

            } else {
                // Create a Pattern object
                Pattern r = Pattern.compile("\"(.*)\".*$");

                // Now create matcher object.
                for (String line : ej.getStdout()) {
                    Matcher m = r.matcher(line);
                    if (m.find()) {
                        names.add(m.group(1));
                    }
                }

                if (names.isEmpty()) {
                    UI.showNoNamesError();

                } else {
                    UI.showList("Lista máquinas virtuales activas", names);

                }
            }
        }
    }

    private static List<String> getVMNames(File vboxManageFile) {
        List<String> vmNames = new ArrayList<>();
        CustomProcess ej1;

        List<String> args = new ArrayList<>();
        args.add("list");
        args.add("vms");

        ej1 = new CustomProcess("ejercicio 1", vboxManageFile, args);
        ej1.runProcess();

        if (ej1.getExitValue() == 0) {
            Log.LOGGER.info("Proceso para recuperar nombres de máquinas virtuales tuvo éxito");

            // Create a Pattern object
            Pattern r = Pattern.compile("\"(.*)\".*$");

            // Now create matcher object.
            for (String line : ej1.getStdout()) {
                Matcher m = r.matcher(line);
                if (m.find()) {
                    vmNames.add(m.group(1));
                }
            }
            return vmNames;
        }
        return null;
    }

    /**
     *
     * @param exeFile
     * @return
     */
    protected static boolean checkPath(File exeFile) {
        if (exeFile.isFile()) {
            if (exeFile.canExecute()) {
                Log.LOGGER.info(
                        String.format(
                                "Localizado archivo "
                                + "con permiso ejecución válido:%n     \"%s\"", exeFile));
                return true; // SUCCESS

            } else {
                Log.LOGGER.warning(
                        String.format("Archivo ejecutable \"%s\" "
                                + " existe pero no se puede ejecutar", exeFile));
            }
        } else {
            Log.LOGGER.warning(
                    String.format("Archivo ejecutable \"%s\" no existe", exeFile));
        }
        return false;
    }
}
