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
        CustomProcess ej1;
        List<String> vmNamesList = new ArrayList<>();

        List<String> args = new ArrayList<>();
        args.add("list");
        args.add("vms");
        ej1 = new CustomProcess("ejercicio 1", vboxManageFile, args);
        ej1.runProcess();

        if (ej1.getExitValue() == null) {
            ConsoleUI.showErrMsg("Hubo un error al hacer la petición a vboxmanage");
            ConsoleUI.showErrMsg(Log.LOGMSG);

        } else if (ej1.getExitValue() != 0) {
            String msg = String.format("vboxmanage devolvió error %d", ej1.getExitValue());
            ConsoleUI.showErrMsg(msg);
            ConsoleUI.showErrorProcessOutput(ej1);

        } else {
            if (ej1.getStdout().isEmpty()) {
                ConsoleUI.showInfoMsg("No existen máquinas virtuales actualmente");

            } else {
                // Create a Pattern object
                Pattern r = Pattern.compile("\"(.*)\".*$");

                // Now create matcher object.
                for (String line : ej1.getStdout()) {
                    Matcher m = r.matcher(line);
                    if (m.find()) {
                        vmNamesList.add(m.group(1));
                    }
                }

                if (vmNamesList.isEmpty()) {
                    ConsoleUI.showInfoMsg("Error al filtrar los nombres "
                            + "de las máquinas virtuales de la lista enviada por vboxmanage");

                } else {
                    ConsoleUI.showList("Lista máquinas virtuales activas", vmNamesList);

                }
            }
        }
    }

    static void runExercise2(File vboxManageFile) {
        Log.LOGGER.info("Iniciando Ejercicio 2 ****");
        ConsoleUI.showInfoMsg("Cambiar memoria asignada a una máquina virtual");

        String menuTitle = "Lista máquinas virtuales";

        List<String> vmList = getVMNames(vboxManageFile, false);

        if (vmList.isEmpty()) {
            vmList.add("CANCELAR; No existen máquinas virtuales");
        } else {
            vmList.add("CANCELAR EJERCICIO");
        }

        int vmNum = ConsoleUtil.getOpFromMenu(menuTitle, vmList);

        if (vmNum == vmList.size()) { // opcion cancelar
            Log.LOGGER.info("Seleccion cancelada por usuario");
            ConsoleUI.showInfoMsg("Ejercicio cancelado");
            return;

        } else {
            vmNum--; // ajustar indice con menú
        }
        String vmName = vmList.get(vmNum);

        String memCant = ConsoleUI.askMemory(vmName);

        if (memCant.isEmpty()) { // opcion cancelar
            Log.LOGGER.info("Operación cancelada por usuario");
            ConsoleUI.showInfoMsg("Ejercicio cancelado");
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
            ConsoleUI.showErrMsg("Hubo un error al hacer la petición a vboxmanage");
            ConsoleUI.showErrMsg(Log.LOGMSG);

        } else if (ej2.getExitValue() != 0) {
            String msg = String.format("vboxmanage devolvió error %d", ej2.getExitValue());
            ConsoleUI.showErrMsg(msg);
            ConsoleUI.showErrorProcessOutput(ej2);

        } else {
            String msg = String.format("Se le asignó %s de RAM a a máquina virtual %s%n", memCant, vmName);
            ConsoleUI.showInfoMsg(msg);
        }
    }

    static void runExercise3(File vboxManageFile) {
        Log.LOGGER.info("Iniciando Ejercicio 3 ****");
        ConsoleUI.showInfoMsg("Apagar máquina virtual");

        String menuTitle = "Lista máquinas virtuales encendidas";
        List<String> vmList = getVMNames(vboxManageFile, true);

        if (vmList.isEmpty()) {
            vmList.add("CANCELAR EJERCICIO; Ninguna máquina virtual está encendida");
        } else {
            vmList.add("CANCELAR EJERCICIO");
        }

        int vmNum = ConsoleUtil.getOpFromMenu(menuTitle, vmList);

        if (vmNum == vmList.size()) { // opcion cancelar
            Log.LOGGER.info("Seleccion cancelada por usuario");
            ConsoleUI.showInfoMsg("Ejercicio cancelado");
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
            ConsoleUI.showErrMsg("Hubo un error al hacer la petición a vboxmanage");
            ConsoleUI.showErrMsg(Log.LOGMSG);

        } else if (ej3.getExitValue() != 0) {
            String msg = String.format("vboxmanage devolvió el error %d", ej3.getExitValue());
            ConsoleUI.showErrMsg(msg);
            ConsoleUI.showErrorProcessOutput(ej3);

        } else {
            String msg = String.format("La máquina virtual %s se ha apagado", vmName);
            String aviso = String.format("Aviso: Las máquinas virtuales con W10 a mi no se me apagaron%n");
            ConsoleUI.showInfoMsg(msg);
            ConsoleUI.showInfoMsg(aviso);
        }
    }

    static void runExercise4(File vboxManageFile) {
       Log.LOGGER.info("Iniciando Ejercicio 4 ****");
        ConsoleUI.showInfoMsg("Cambiar descripción asignada a una máquina virtual");

        String menuTitle = "Lista máquinas virtuales";

        List<String> vmList = getVMNames(vboxManageFile, false);

        if (vmList.isEmpty()) {
            vmList.add("CANCELAR; No existen máquinas virtuales");
        } else {
            vmList.add("CANCELAR EJERCICIO");
        }

        int vmNum = ConsoleUtil.getOpFromMenu(menuTitle, vmList);

        if (vmNum == vmList.size()) { // opcion cancelar
            Log.LOGGER.info("Seleccion cancelada por usuario");
            ConsoleUI.showInfoMsg("Ejercicio cancelado");
            return;

        } else {
            vmNum--; // ajustar indice con menú
        }
        String vmName = vmList.get(vmNum);


        String desc = ConsoleUI.askDesc(vmName);
        System.out.print(desc);
        if (desc.isEmpty()) { // opcion cancelar
            Log.LOGGER.info("Operación cancelada por usuario");
            ConsoleUI.showInfoMsg("Ejercicio cancelado");
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
            ConsoleUI.showErrMsg("Hubo un error al hacer la petición a vboxmanage");
            ConsoleUI.showErrMsg(Log.LOGMSG);

        } else if (ej4.getExitValue() != 0) {
            String msg = String.format("vboxmanage devolvió error %d", ej4.getExitValue());
            ConsoleUI.showErrMsg(msg);
            ConsoleUI.showErrorProcessOutput(ej4);

        } else {
            String msg = String.format("%nSe le asignó la nueva descripción a la máquina virtual %s%n", vmName);
            ConsoleUI.showInfoMsg(msg);
        }
    }

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
