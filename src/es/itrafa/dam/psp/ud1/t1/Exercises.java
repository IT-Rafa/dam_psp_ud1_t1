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
public class Exercises {

    static void runExercise1(File vboxManageFile) {
        Log.LOGGER.info("Iniciando Ejercicio 1 ****");
        List<String> vmNames;

        vmNames = getVMNames(vboxManageFile);
        UI.outputExercise1(vmNames);

    }

    static void runExercise2(File vboxManageFile) {
        Log.LOGGER.info("Iniciando Ejercicio 2 ****");

        String vmName = UI.chooseVm(getVMNames(vboxManageFile));
        String memCant = UI.askMemory(vmName);

        CustomProcess ej1;

        List<String> args = new ArrayList<>();
        args.add("modifyvm");
        args.add("vmName");
        args.add("--memory");
        args.add(memCant);

        ej1 = new CustomProcess("ejercicio 2", vboxManageFile, args);
        ej1.runProcess();
    }

    static void runExercise3(File vboxManageFile) {
        Log.LOGGER.info("Iniciando Ejercicio 3 ****");
        String vmName = UI.chooseVm(getVMNames(vboxManageFile));

    }

    static void runExercise4(File vboxManageFile) {
        Log.LOGGER.info("Iniciando Ejercicio 4 ****");
        String vmName = UI.chooseVm(getVMNames(vboxManageFile));

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

}
