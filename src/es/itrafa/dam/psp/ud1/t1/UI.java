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

import es.itrafa.util.console.AskOpFromMenu;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Gestiona UI programa
 *
 * @author rafa
 */
public class UI {

    /**
     * Pide una descripción al usuario para una máquina virtual
     *
     * @param vmName Máquina a la que se le asignará la descripción
     *
     * @return String que contiene un valor válido o cadena vacia
     */
    protected static String askDesc(String vmName) {
        String line = null;
        try {

            Scanner inputOp = new Scanner(System.in);

            Log.LOGGER.info("Pidiendo la descripción que se quiere asignar a máquina virtual");
            System.out.printf("Escriba descripción para %s (Deje vacio para cancelar): ", vmName);

            line = inputOp.nextLine();

            Log.LOGGER.info("Descripción capturada");

        } catch (Exception ex) {
            Log.LOGGER.severe("Error desconocido al capturar descripción");
            System.out.printf("Error desconocido al capturar descripción): ");
        }
        return line;
    }

    /**
     * Muestra salida completa que el proceso envio a consola (stdout y stderr)
     *
     * @warning salida puede no coincidir con original por sincronización entre
     * stdout y stderr
     *
     * @param ej CustomProcess con los datos del proceso
     */
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
            Log.LOGGER.warning("Fallo truco para mostrar salida sincronizada");
        }

    }

    /**
     * Muestra mensaje en consola por stdout
     *
     * @param message String con el mensaje
     */
    static void showInfoMsg(String message) {
        System.out.println(message);
    }

    /**
     * Muestra mensaje en consola por stderr
     *
     * @param message String con el mensaje
     */
    static void showErrMsg(String message) {
        System.err.println(message);
    }

    /**
     * Muestra por consola lista de Strings formateada
     *
     * @param title String con descripción de la lista
     * @param list Lista de String a mostrar
     */
    static void showList(String title, List<String> list) {
        // header
        System.out.println(title);
        for (int i = 0; i < title.length(); i++) {
            System.out.printf("%c", '=');
        }
        System.out.println();
        // list
        for (String element : list) {
            System.out.println(element);
        }
        System.out.println();
    }

    /**
     * Salida en consola del mensaje de error por fallo al no localizar
     * vboxmanage
     */
    static void showPathNoFound() {
        System.err.println("Error al comprobar archivo VBoxManage; ");
        System.err.println(Log.LOGMSG);
        System.err.println("Compruebe que virtualBox está instalado y que la ruta de instalación coincide");
    }

    static void MainMenu() {
        String menuTitle = "Menú PSP_UD1_T1: vBox ejercicios";
        List<String> optionsMenu = new ArrayList<>();

        optionsMenu.add("Ej_1: Mostrar máquinas virtuales");
        optionsMenu.add("Ej_2: Cambiar RAM a máquina virtual");
        optionsMenu.add("Ej_3: Apagar máquina virtual");
        optionsMenu.add("Ej_4: Cambiar descripción a máquina virtual");
        optionsMenu.add("FIN PROGRAMA");

        AskOpFromMenu mainMenu = new AskOpFromMenu(menuTitle, optionsMenu);
    }

    static int getOpFromMainMenu() {
        String menuTitle = "Menú PSP_UD1_T1: vBox ejercicios";
        List<String> optionsMenu = new ArrayList<>();

        optionsMenu.add("Ej_1: Mostrar máquinas virtuales");
        optionsMenu.add("Ej_2: Cambiar RAM a máquina virtual");
        optionsMenu.add("Ej_3: Apagar máquina virtual");
        optionsMenu.add("Ej_4: Cambiar descripción a máquina virtual");
        optionsMenu.add("FIN PROGRAMA");

        AskOpFromMenu mainMenu = new AskOpFromMenu(menuTitle, optionsMenu);

        return mainMenu.showAndAsk();
    }

    static int chooseVm(String menuTitle, List<String> vmList) {
        AskOpFromMenu vmMenu = new AskOpFromMenu(menuTitle, vmList);
        return vmMenu.showAndAsk();
    }
    
        static int askMemory(String question, int min, int max) {
        AskOpFromMenu vmMem = new AskOpFromMenu(question, min, max);
        return vmMem.showAndAsk();
    }

}
