/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.itrafa.dam.psp.ud1.t1;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafa
 */
public class UI {

    protected static int mainMenu() {
        Log.LOGGER.info("Mostramos menú de ejercicios");
        int op;
        System.out.printf("Menú PSP_UD1_T1: VirtualBox%n");
        System.out.printf("===========================%n");
        System.out.printf("1 : Ej_1: Mostrar máquinas virtuales%n");
        System.out.printf("2 : Ej_2: Cambiar RAM a máquina virtual%n");
        System.out.printf("3 : Ej_3: Apagar máquina virtual%n");
        System.out.printf("4 : Ej_4: Cambiar descripción a máquina virtual%n");
        System.out.printf("0 : Finalizar programa%n");
        System.out.printf("%nSeleccione una opción: ");

        Log.LOGGER.info("Esperando selección del usuario");
        Scanner inputOp = new Scanner(System.in);
        try {
            op = inputOp.nextInt();
        } catch (java.util.InputMismatchException ex) {
            op = -1; // 
        }

        switch (op) {
            case 0:
                Log.LOGGER.info("Usuario seleccionó opción Salir");
            case 1:
            case 2:
            case 3:
            case 4:
                Log.LOGGER.info(String.format(
                        "Usuario seleccionó Ejercicio %d", op));
                return op;
            default:
                Log.LOGGER.warning("Usuario seleccionó opción errónea");
                System.out.printf("Opción introducida no válida. Seleccione una opción del menú%n%n", op);
                return mainMenu();
        }

    }

    /**
     *
     * @param vmList
     * @param isRunning
     * @return
     */
    protected static String chooseVm(List<String> vmList, boolean onlyRunning) {
        int op;
        String line;
        Scanner inputOp = new Scanner(System.in);

        op = -1;
        while (op < 1 || op > vmList.size()) {
            if (onlyRunning) {
                Log.LOGGER.info("Mostrando lista de nombres de máquinas virtuales encendidas");
                System.out.printf(
                        "Seleccione máquina virtual%n");
                System.out.printf("Lista Máquinas virtuales actuales encendidas:%n");
                
            } else {
                Log.LOGGER.info("Mostrando lista de nombres de máquinas virtuales");
                System.out.printf(
                        "Seleccione máquina virtual%n");
                System.out.printf("Lista Máquinas virtuales actuales:%n");
            }
            int i = 0;
            for (String eachVm : vmList) {
                System.out.printf("%d: %s%n", 1 + i++, eachVm);
            }

            System.out.printf("Escriba el número correspondiente: ");
            Log.LOGGER.info("Esperando selección del usuario");

            line = inputOp.nextLine();
            op = Integer.parseUnsignedInt(line);
            if (op < 1 || op > vmList.size()) {
                Log.LOGGER.info("Seleccion usuario no válida. Repetimos petición");
                System.out.printf(
                        "%nNúmero no válido. Seleccione una de las opciones%n");
            }
        }

        Log.LOGGER.info(
                String.format(
                        "Seleccion usuario válida; Máquina virtual elegida: %s",
                        vmList.get(op - 1)));

        return vmList.get(op - 1);
    }

    /**
     *
     * @param vmName
     * @return
     */
    protected static String askMemory(String vmName) {

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
        return String.format("%d", mem);
    }

    /**
     *
     * @param vmName
     * @return
     */
    protected static String askDesc(String vmName) {
        String line;
        Scanner inputOp = new Scanner(System.in);

        Log.LOGGER.info("Pidiendo la descripción que se quiere asignar a máquina virtual");
        System.out.printf("Escriba descripción para %s: ", vmName);

        line = inputOp.nextLine();

        Log.LOGGER.info("Descripción capturada");

        return line;
    }

    static void showErrorProcessOutput(CustomProcess ej) {
        // flush() y thread.sleep "sincronizan" las salidas de err y out
        try {
            System.err.println("A continuación se muestra la salida de vboxmanage:");
            System.err.flush();
            Thread.sleep(1);
            for (String line : ej.getStdout()) {
                System.out.println(line);
                System.out.flush();
                Thread.sleep(1);
            }

            for (String line : ej.getStderr()) {
                System.err.println(line);
                System.err.flush();
                Thread.sleep(1);
            }
            System.out.println("Fin salida");
            System.out.println();
        } catch (InterruptedException ex) {
            Log.LOGGER.warning("Problema con truco para mostrar salida");
        }

    }

    static void showInfoMsg(String message) {
        System.out.println(message);
    }

    static void showErrMsg(String message) {
        System.err.println(message);
    }

    static void showList(String title, List<String> list) {

        System.out.println(title);
        for (int i = 0; i < title.length(); i++) {
            System.out.printf("%c", '=');
        }
        System.out.println();

        for (String element : list) {
            System.out.println(element);
        }
        System.out.println();
    }

}
