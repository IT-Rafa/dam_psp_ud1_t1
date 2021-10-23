/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.itrafa.dam.psp.ud1.t1;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author rafa
 */
public class AskUser {

    /**
     *
     * @param vmList
     * @return
     */
    protected static String chooseVm(List<String> vmList) {
        int op;
        String line;
        Scanner inputOp = new Scanner(System.in);
        op = -1;
        Log.LOGGER.info("Pidiendo al usuario que eliga una máquina virtual");
        while (op < 1 || op > vmList.size()) {
            System.out.printf("Indique a que máquina virtual desea asignarle otra RAM.%n");
            System.out.printf("Máquina virtuales actuales:%n");
            int i = 0;
            for (String eachVm : vmList) {
                System.out.printf("%d: %s%n", 1 + i++, eachVm);
            }
            System.out.printf("Escriba el número correspondiente: ");
            line = inputOp.nextLine();
            op = Integer.parseUnsignedInt(line);
            Log.LOGGER.info(String.format("Usuario seleccionó %d", op));
            if (op < 1 || op > vmList.size()) {
                Log.LOGGER.info("Seleccion no válida. Repetimos petición");
                System.out.printf("%nNúmero no válido. Seleccione una de las opciones%n");
            }
        }
        Log.LOGGER.info(String.format("Seleccion válida; máquina virtual elegida: %s", vmList.get(op - 1)));

        return vmList.get(op - 1);
    }

    /**
     *
     * @param vmName
     * @return
     */
    protected static int askMemory(String vmName) {


        int mem;
        String line;
        Scanner inputOp = new Scanner(System.in);
        mem = -1;
        Log.LOGGER.info("Pidiendo al usuario que indique cantidad memoría");
        while (mem < 1 || mem > Integer.MAX_VALUE) {
            System.out.printf("Escriba RAM (en MB) para %s: ", vmName);

            line = inputOp.nextLine();
            mem = Integer.parseUnsignedInt(line);
            Log.LOGGER.info(String.format("Usuario escribio %s", line));
            if (mem < 1 || mem > Integer.MAX_VALUE) {
                Log.LOGGER.info("cantidad no válida. Repetimos petición");
                System.out.printf("%nCantidad no válida. El rango es 1-%d%n", Integer.MAX_VALUE);
            }
        }
        Log.LOGGER.info(String.format("Cantidad válida; RAM a asignar: %d", mem));

        return mem;
    }

    static String askDesc(List<String> vmNamesList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
