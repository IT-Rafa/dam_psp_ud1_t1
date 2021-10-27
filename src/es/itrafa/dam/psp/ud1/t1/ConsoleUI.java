/*
 * File Name: Init.java
 * Project: dam_psp_ud1_t1
 * Enunciado completo en docs
 * Causa: Tarea para 
 *
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.itrafa.dam.psp.ud1.t1;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Gestiona entradas y salidas a consola para usuario
 *
 * @author rafa
 */
public class ConsoleUI {

    protected static int mainMenu() {
        int op;
        String menuTitle = "Menú PSP_UD1_T1: vBox ejercicios";
        List<String> optionsMenu = new ArrayList<>();
        optionsMenu.add("Ej_1: Mostrar máquinas virtuales");
        optionsMenu.add("Ej_2: Cambiar RAM a máquina virtual");
        optionsMenu.add("Ej_3: Apagar máquina virtual");
        optionsMenu.add("Ej_4: Cambiar descripción a máquina virtual");
        optionsMenu.add("Finalizar programa");

        return getOpFromMenu(menuTitle, optionsMenu);
    }

    /**
     * Show a console menu with the title, and the options from arguments. This
     * menu will be displayed again until a right option be selected by user.
     * The options are numbered correlatively from one.
     *
     * @param menuTitle String title of menu
     * @param optionsMenu List<String> List of options to show
     * @return int - The option selected by user
     */
    private static int getOpFromMenu(String menuTitle, List<String> optionsMenu) {
        int op;

        int maxLenght; // cant of chars for menu border == The longest line
        maxLenght = menuTitle.length();

        do {
            // Calcule max lenght of options
            for (String opMenu : optionsMenu) {
                if (opMenu.length() > maxLenght) {
                    maxLenght = opMenu.length();
                }
            }
            // Menu Header
            maxLenght += 6;
            for (int i = 0; i < maxLenght; i++) {
                System.out.printf("%c", '*');
            }
            System.out.println();
            System.out.println("*  " + maxLenght + "  *");
            for (int i = 0; i < maxLenght; i++) {
                System.out.printf("%c", '*');
            }
            System.out.println();

            // Menu options 
            int opNum = 1; // Options start with one
            for (String opMenu : optionsMenu) {
                System.out.printf("*  %d: %-11s", opNum++, opMenu);
                System.out.printf("  *%n");
            }
            for (int i = 0; i < maxLenght; i++) {
                System.out.printf("%c", '*');
            }
            System.out.println();
            System.out.print("Seleccione una opción: ");

            // ask user to select a option 
            try {
                Scanner input = new Scanner(System.in);
                op = input.nextInt();
            } catch (InputMismatchException ex) {
                op = 0;
            }
            // check if is a valid option
            if (op < 1 || op > optionsMenu.size()) {
                System.out.printf("Opción no válida. Seleccione opción del menú%n%n");
                op = 0;
            }
        } while (op == 0); // zero is never a valid option

        return op;
    }

    /**
     * Show message in console and ask user for int
     *
     * @param menuTitle String title of menu
     * @param optionsMenu List<String> List of options to show
     * @return int - The option selected by user
     */
    private static int askForInt(String question, int min, int max) {
        int userInt;
        do {

            System.out.print(question);

            try {
                Scanner input = new Scanner(System.in);
                userInt = input.nextInt();
            } catch (InputMismatchException ex) {
                userInt = min - 1;
            }

            if (userInt < min || userInt > max) {
                System.out.print("Valor fuera de rango o no válido.");
                System.out.printf("%nReintente o introduzca -1 para cancelar%n%n");
                userInt = min - 1;
            }
        } while (userInt == min - 1);

        return userInt;
    }

    /**
     * Muestra menú por consola y recibe opción y la comprueba Repite si se
     * introduce opción errónea.
     *
     * @return int: opción elegida válida
     */
    protected static int mainMenu2() {
        Log.LOGGER.info("Mostramos menú de ejercicios");
        int op;
        System.out.printf("Menú PSP_UD1_T1: vBox ejercicios%n");
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
     * Muestra menú para elegir entre las máquinas virtuales de la lista. Repite
     * hasta opción válida o al seleccionar cancelar
     *
     * @param vmList Lista de nombres de máquinas virtuales
     * @param onlyRunning Indica si la lista se refiere a todas o solo las
     * encendidas
     *
     * @return String - Nombre de máquina virtual seleccionado. Vacio si
     * cancelar
     */
    protected static String chooseVm(List<String> vmList, boolean onlyRunning) {
        int op;
        String line;
        Scanner inputOp = new Scanner(System.in);

        op = -1;
        while (op < 1 || op > vmList.size()) {
            // Muestra título lista
            System.out.printf("Seleccione máquina virtual%n");
            if (onlyRunning) {
                Log.LOGGER.info("Mostrando lista de nombres de máquinas virtuales encendidas");
                System.out.printf("Lista Máquinas virtuales actuales encendidas:%n");

            } else {
                Log.LOGGER.info("Mostrando lista de nombres de todas máquinas virtuales");
                System.out.printf("Lista Máquinas virtuales actuales:%n");
            }

            // Muestra lista
            int i = 0;
            for (String eachVm : vmList) {
                System.out.printf("%d: %s%n", 1 + i++, eachVm);
            }
            System.out.printf("0: Cancelar Selección%n");

            // Captura opción
            System.out.printf("Escriba el número correspondiente: ");
            Log.LOGGER.info("Esperando selección del usuario");

            line = inputOp.nextLine();

            // Comprueba valor válido (rango int)
            op = Integer.parseUnsignedInt(line);
            if (op < 1 || op > vmList.size()) {
                Log.LOGGER.info("Seleccion usuario no válida. Repetimos petición");
                System.out.printf(
                        "%nNúmero no válido. Seleccione una de las opciones%n");
            }
        }

        // Opción válida
        Log.LOGGER.info(
                String.format(
                        "Seleccion usuario válida; Máquina virtual elegida: %s",
                        vmList.get(op - 1)));
        if (op == 0) {
            return "";
        } else {
            return vmList.get(op - 1);
        }

    }

    /**
     * Pide al usuario una cantidad de memoria y comprueba rango válido. 0 Se
     * interpreterá como cancelar
     *
     * @param vmName Máquina a la que se le asignará la memoria
     * @return String Cantidad memoria a asignar. Vacio si cancelar
     */
    protected static String askMemory(String vmName) {
        final int minMem = 4;
        final int maxMem = Integer.MAX_VALUE;

        int mem;
        String line;
        Scanner inputOp = new Scanner(System.in);

        mem = -1;
        Log.LOGGER.info("Pidiendo al usuario que indique cantidad memoría");
        while (mem < 0 || mem > maxMem) {
            System.out.printf("Escriba RAM (en MB) para %s(0 para anular): ", vmName);
            line = inputOp.nextLine();

            // pasamos a int para comprobar rango
            mem = Integer.parseUnsignedInt(line);
            Log.LOGGER.info(String.format("Usuario escribio %s", line));
            if (mem < minMem || mem > Integer.MAX_VALUE) {
                Log.LOGGER.info(String.format("valor introducido: %d no es válido", mem));
                System.out.printf("%nCantidad no válida. El rango es %d-%d%n", minMem, maxMem);
            }
        }
        Log.LOGGER.info(String.format("valor introducido: %d es válido", mem));
        return String.format("%d", mem);
    }

    /**
     * Pide una descripción al usuario para una máquina virtual
     *
     * @param vmName Máquina a la que se le asignará la descripción
     * @return La descripción a asignar añadiendo comillas
     */
    protected static String askDesc(String vmName) {
        String line;
        Scanner inputOp = new Scanner(System.in);

        Log.LOGGER.info("Pidiendo la descripción que se quiere asignar a máquina virtual");
        System.out.printf("Escriba descripción para %s: ", vmName);

        line = inputOp.nextLine();

        Log.LOGGER.info("Descripción capturada");

        return "\"" + line + "\"";
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

    static void showPathNoFound() {
        System.err.println("Error al comprobar archivo VBoxManage; ");
        System.err.println(Log.LOGMSG);
        System.err.println("Compruebe que virtualBox está instalado y que la ruta de instalación coincide");
    }

}
