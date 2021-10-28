/*
 * File Name: Exercises.java
 * Project: dam_psp_ud1_t1
 *
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
 * Collections of methods with logic for each exercise
 *
 * @author it-ra
 */
public class Exercises {

    /**
     * Enunciado Ejercicio 1: <\br>
     * Ejercicio 1): Listar las máquinas virtuales que se tiene en Virtual Box
     * 
     * @nota: Capturar las máquinas es usado por todos los ejercicios, ya que
     * se usan para elegir la máquina a modificar, por lo que el proceso real 
     * está en la función getVMNames()
     * 
     * @param vboxManageFile File: Archivo vxmanage para gestión virtualbox por
     * consola
     */
    static void runExercise1(File vboxManageFile) {
        Log.LOGGER.info("Iniciando Ejercicio 1 ****");
        CustomProcess ej1; // control petición a vboxmanage
        List<String> vmNamesList = getVMNames(vboxManageFile, false);

        if (vmNamesList.isEmpty()) {
            UI.showInfoMsg("No existen máquinas virtuales");

        } else {
            UI.showList("Lista máquinas virtuales activas", vmNamesList);

        }
    }

    /**
     * Enunciado Ejercicio 2:: <\br>
     * Ejercicio 2): A partir del nombre de la máquina virtual modificar su ram,
     * debéis pedir al usuario los datos necesarios:
     * 
     * @Warning: Las máquinas encendidas no se pueden modificar; Se muestra
     * salida del error de vboxmanage.
     *
     * @param vboxManageFile File: Archivo vxmanage para gestión virtualbox por
     * consola
     */
    static void runExercise2(File vboxManageFile) {
        Log.LOGGER.info("Iniciando Ejercicio 2 ****");
        UI.showInfoMsg("Cambiar memoria asignada a una máquina virtual");

        String vmName; // nombre mv a cambiar
        String memCant;  // cantidad memoria a asignar en MV

        // Paso 1: Pedir nombre mv a usuario (Usando menú)

        // preparar menu: list + cancelar, por si está vacia
        List<String> vmList = getVMNames(vboxManageFile, false);

        if (vmList.isEmpty()) {
            vmList.add("CANCELAR; No existen máquinas virtuales");

        } else {
            vmList.add("CANCELAR EJERCICIO");
        }

        // mostrar menu y capturar indice seleccionado
        int vmNum = UI.chooseVm("Lista máquinas virtuales", vmList);

        // Lógica menu
        // Convertir indice a nombre mv o cancelar
        if (vmNum == vmList.size()) {
            Log.LOGGER.info("Seleccion cancelada por usuario");
            UI.showInfoMsg("Ejercicio cancelado");
            return;

        } else {
            vmNum--; // ajustar indice con menú
        }

        vmName = vmList.get(vmNum);

        // Paso 2: Pedir memoria al usuario
        memCant = UI.askMemory("vmName", 0, Integer.MAX_VALUE);

        if (memCant.isEmpty()) { // opcion cancelar
            Log.LOGGER.info("Operación cancelada por usuario");
            UI.showInfoMsg("Ejercicio cancelado");
            return;
        }

        CustomProcess ej2;

        List<String> args = new ArrayList<>();
        args.add("modifyvm");
        args.add(vmName);
        args.add("--memory");
        args.add(memCant);

        ej2 = new CustomProcess("ejercicio 2", vboxManageFile, args);
        ej2.runProcess();

        if (ej2.getExitValue() == null) {
            UI.showErrMsg("Hubo un error al hacer la petición a vboxmanage");
            UI.showErrMsg(Log.LOGMSG);

        } else if (ej2.getExitValue() != 0) {
            String msg = String.format("vboxmanage devolvió error %d", ej2.getExitValue());
            UI.showErrMsg(msg);
            UI.showErrorProcessOutput(ej2);

        } else {
            String msg = String.format("Se le asignó %s de RAM a a máquina virtual %s%n", memCant, vmName);
            UI.showInfoMsg(msg);
        }
    }

    /**
     * Enunciado Ejercicio 3: <\br>
     * Ejercicio 3): Apagar desde Java alguna máquina que está arrancada, debéis
     * pedir al usuario los datos necesarios
     *
     * @Nota: No se pide, pero solo se mostrarán las encendidas; Si no, saldría
     * mensaje vboxmanage con error por apagar mv sin encender
     *
     * @param vboxManageFile File: Archivo vxmanage para gestión virtualbox por
     * consola
     */
    static void runExercise3(File vboxManageFile) {
        Log.LOGGER.info("Iniciando Ejercicio 3 ****");
        UI.showInfoMsg("Apagar máquina virtual");

        List<String> vmList = getVMNames(vboxManageFile, false);

        if (vmList.isEmpty()) {
            vmList.add("CANCELAR; No existen máquinas virtuales");

        } else {
            vmList.add("CANCELAR EJERCICIO");
        }

        // mostrar menu y capturar indice seleccionado
        int vmNum = UI.chooseVm("Lista máquinas virtuales encendidas", vmList);


        if (vmNum == vmList.size()) { // opcion cancelar
            Log.LOGGER.info("Seleccion cancelada por usuario");
            UI.showInfoMsg("Ejercicio cancelado");
            return;

        } else {
            vmNum--; // ajustar indice con menú
        }
        String vmName = vmList.get(vmNum);

        CustomProcess ej3;

        List<String> args = new ArrayList<>();
        args.add("controlvm");
        args.add(vmName);
        args.add("acpipowerbutton");

        ej3 = new CustomProcess("ejercicio 2", vboxManageFile, args);
        ej3.runProcess();

        if (ej3.getExitValue() == null) {
            UI.showErrMsg("Hubo un error al hacer la petición a vboxmanage");
            UI.showErrMsg(Log.LOGMSG);

        } else if (ej3.getExitValue() != 0) {
            String msg = String.format("vboxmanage devolvió el error %d", ej3.getExitValue());
            UI.showErrMsg(msg);
            UI.showErrorProcessOutput(ej3);

        } else {
            String msg = String.format("La máquina virtual %s se ha apagado", vmName);
            String aviso = String.format("Aviso: Las máquinas virtuales con W10 a mi no se me apagaron%n");
            UI.showInfoMsg(msg);
            UI.showInfoMsg(aviso);
        }
    }

    /**
     * Enunciado Ejercicio 4:: <\br>
     * Ejercicio 4): Agregar una descripción a una máquina:
     *
     * @Warning: Las máquinas encendidas no se pueden modificar; Se muestra
     * salida del error de vboxmanage.
     *
     * @param vboxManageFile File: Archivo vxmanage para gestión virtualbox por
     * consola
     */
    static void runExercise4(File vboxManageFile) {
        Log.LOGGER.info("Iniciando Ejercicio 4 ****");
        UI.showInfoMsg("Cambiar descripción asignada a una máquina virtual");
        List<String> vmList = getVMNames(vboxManageFile, false);

        if (vmList.isEmpty()) {
            vmList.add("CANCELAR; No existen máquinas virtuales");

        } else {
            vmList.add("CANCELAR EJERCICIO");
        }

        // mostrar menu y capturar indice seleccionado
        int vmNum = UI.chooseVm("Lista máquinas virtuales", vmList);

        if (vmNum == vmList.size()) { // opcion cancelar
            Log.LOGGER.info("Seleccion cancelada por usuario");
            UI.showInfoMsg("Ejercicio cancelado");
            return;

        } else {
            vmNum--; // ajustar indice con menú
        }
        String vmName = vmList.get(vmNum);

        String desc = UI.askDesc(vmName);
        System.out.print(desc);
        if (desc.isEmpty()) { // opcion cancelar
            Log.LOGGER.info("Operación cancelada por usuario");
            UI.showInfoMsg("Ejercicio cancelado");
            return;
        }
        String descQuotes = "\"" + desc + "\"";

        CustomProcess ej4;

        List<String> args = new ArrayList<>();
        args.add("modifyvm");
        args.add(vmName);
        args.add("--description");
        args.add(descQuotes);

        ej4 = new CustomProcess("ejercicio 4", vboxManageFile, args);
        ej4.runProcess();

        if (ej4.getExitValue() == null) {
            UI.showErrMsg("Hubo un error al hacer la petición a vboxmanage");
            UI.showErrMsg(Log.LOGMSG);

        } else if (ej4.getExitValue() != 0) {
            String msg = String.format("vboxmanage devolvió error %d", ej4.getExitValue());
            UI.showErrMsg(msg);
            UI.showErrorProcessOutput(ej4);

        } else {
            String msg = String.format("%nSe le asignó la nueva descripción a la máquina virtual %s%n", vmName);
            UI.showInfoMsg(msg);
        }
    }

    /**
     * Captura y filtra solo el nombre de las máquinas virtuales que existen en
     * Virtual Box; Todas, o solo las encendidas, según onlyRunning
     *
     * @param vboxManageFile File: Archivo vxmanage para gestión virtualbox por
     * consola
     * @param onlyRunning Si true solo se muestran mv encendidas, si false se
     * muestran todas
     *
     * @return Lista con los solo los nombres de las máquinas virtuales
     * indicadas
     */
    private static List<String> getVMNames(File vboxManageFile, boolean onlyRunning) {
        List<String> vmNames = new ArrayList<>();
        CustomProcess ej1;
        String descProceso;
        List<String> args = new ArrayList<>();
        args.add("list");
        if (onlyRunning) {
            args.add("runningvms ");
            descProceso = "captura vm actuales";
        } else {
            args.add("vms");
            descProceso = "captura vm iniciadas";
        }

        ej1 = new CustomProcess(descProceso, vboxManageFile, args);
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
     * Comprueba que el archivo existe y tiene permisos correctos
     *
     * @param vboxManageFile File: Archivo vxmanage para gestión virtualbox por
     *
     * @return true si el archivo es válido para ejecutar, false sino
     */
    protected static boolean checkPath(File vboxManageFile) {
        if (vboxManageFile.isFile()) {
            if (vboxManageFile.canExecute()) {
                Log.LOGGER.info(
                        String.format(
                                "Localizado archivo "
                                + "con permiso ejecución válido:%n     \"%s\"", vboxManageFile));
                return true; // SUCCESS

            } else {
                Log.LOGGER.warning(
                        String.format("Archivo ejecutable \"%s\" "
                                + " existe pero no se puede ejecutar", vboxManageFile));
            }
        } else {
            Log.LOGGER.warning(
                    String.format("Archivo ejecutable \"%s\" no existe", vboxManageFile));
        }
        return false;
    }
}
