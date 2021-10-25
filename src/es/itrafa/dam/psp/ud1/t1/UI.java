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
public class UI {

    static final private String LOGMSG = "Revise \"logs/dam_psp_ud1_t1.log\" para mas detalles";

    protected static int mainMenu() {
        Log.LOGGER.info("Mostramos menú de ejercicios");
        int op;
        System.out.printf("Menú PSP_UD1_T1: VirtualBox%n");
        System.out.printf("===========================%n");
        System.out.printf("1 : Ej_1: Mostrar máquinas virtuales%n");
        System.out.printf("2 : Ej_2: Cambiar nombre a máquina virtual%n");
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
                System.out.println();
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
     * @return
     */
    protected static String chooseVm(List<String> vmList) {
        int op;
        String line;
        Scanner inputOp = new Scanner(System.in);

        op = -1;
        while (op < 1 || op > vmList.size()) {
            Log.LOGGER.info("Mostrando lista de nombres de máquinas virtuales");
            System.out.printf(
                    "Seleccione máquina virtual a la que desea modificarle la RAM.%n");
            System.out.printf("Lista Máquinas virtuales actuales:%n");
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
        /*
            --description <desc>:
            Changes the VM's description, which is a way to record details about
            the VM in a way which is meaningful for the user. The GUI interprets 
            HTML formatting, the command line allows arbitrary strings potentially
            containing multiple lines.
         */
 /* El programa admite varias lineas y formato html */
        String line;
        Scanner inputOp = new Scanner(System.in);

        Log.LOGGER.info("Pidiendo la descripción que se quiere asignar a máquina virtual");
        System.out.printf("Escriba descripción para %s: ", vmName);

        line = inputOp.nextLine();

        Log.LOGGER.info("Descripción capturada");

        return line;
    }

    static void showNofoundExeMsg() {
        System.err.print("Error al comprobar archivo VBoxManage; ");
        System.err.println(LOGMSG);
        System.err.println("Compruebe que virtualBox está instalado y que la ruta de instalación coincide");
        System.exit(0);
    }

    static void outputExercise1(List<String> vmNames) {
        if (vmNames == null) {
            System.out.println("Hubo problemas para capturar los nombres de las máquinas virtuales actuales");
            System.out.println(LOGMSG);

        } else if (vmNames.isEmpty()) {
            System.out.println("No existe ninguna máquina virtual actualmente");

        } else {
            System.out.println("Lista de nombres de máquinas virtuales");
            for (String vmName : vmNames) {
                System.out.println(vmName);
            }
            System.out.println();
        }

    }

    static void showEmptyList(String listName) {
        System.out.printf("no existen %s actualmente%n", listName);
    }

    static void showFullOutput(CustomProcess ej1) {
        for (String line : ej1.getStdout()) {
            System.out.println(line);
        }

        for (String line : ej1.getStderr()) {
            System.out.println(line);
        }
    }

    static void showNoNamesError() {
        System.out.println("Error al filtrar los nombres de las máquinas actuales");
    }

    static void showList(String title, List<String> list) {
        System.out.println(title);
        for(int i=0; i<title.length(); i++){
            System.out.printf("%c", '=');
        }
        System.out.println();
        
        for (String element : list) {
            System.out.println(element);
        }
        System.out.println();
    }
}
