/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.itrafa.dam.psp.ud1.t1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author rafa
 */
public class UI {

    static enum OPTION {
        SALIR, EJ1, EJ2, EJ3, EJ4
    };

    // mensaje para ver detalles del error en carpeta logs
    static final private String LOGMSG = "Revise \"logs/dam_psp_ud1_t1.log\" para mas detalles";

    static void runMenu() {
        int op;
        do {
            op = chooseMenu();
        } while (op == -1);
    }

    protected static int chooseMenu() {
        Log.LOGGER.info("Mostrando menú principal al usuario");

        System.out.printf("Menú PSP_UD1_T1: VirtualBox%n");
        System.out.printf("===========================%n");
        System.out.printf("1 : Ej_1: Mostrar máquinas virtuales%n");
        System.out.printf("2 : Ej_2: Cambiar nombre a máquina virtual%n");
        System.out.printf("3 : Ej_3: Apagar máquina virtual%n");
        System.out.printf("4 : Ej_4: Cambiar descripción a máquina virtual%n");
        System.out.printf("0 : Finalizar programa%n");
        System.out.printf("%nSeleccione una opción: ");

        Scanner inputOp = new Scanner(System.in);
        int op = inputOp.nextInt();

        switch (op) {
            case 0:
                Log.LOGGER.info("Usuario seleccionó opción Salir");
                Log.LOGGER.info("FIN PROGRAMA ****");
                System.exit(0);
            case 1:
            case 4:
                Log.LOGGER.info(String.format(
                        "Usuario seleccionó opción válida: %d", op));
                return op;
            default:
                Log.LOGGER.info(String.format("Usuario seleccionó opción errónea: %d", op));
                System.out.printf("Opción %d no es válida. Seleccione una opción del menú%n", op);
                return -1;
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
        Log.LOGGER.info(
                "Pidiendo al usuario que eliga una máquina virtual");
        while (op < 1 || op > vmList.size()) {
            System.out.printf(
                    "Indique a que máquina virtual desea asignarle otra RAM.%n");
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
                System.out.printf(
                        "%nNúmero no válido. Seleccione una de las opciones%n");
            }
        }
        Log.LOGGER.info(
                String.format(
                        "Seleccion válida; máquina virtual elegida: %s",
                        vmList.get(op - 1)));

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

}
