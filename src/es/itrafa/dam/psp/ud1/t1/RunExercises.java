/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.itrafa.dam.psp.ud1.t1;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author rafa
 */
public class RunExercises {

    // Esta ruta dependerá del equipo y la instalación de virtualBox
    static final private File EXEFILE = new File("C:\\Program Files\\Oracle\\VirtualBox\\VBoxManage.exe");

    // mensaje para ver detalles del error en carpeta logs
    static final private String LOGMSG = "Revise \"logs/dam_psp_ud1_t1.log\" para mas detalles";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Prepara logs
        Log.LOGGER.info("INICIO PROGRAMA ****");

        System.out.println("Inicio dam_psp_ud1_t1");

        // Variable rellenada en ejercicio 1 y usada los ejercicios 2, 3 y 4
        List<String> vmNamesList; // Contendrá la lista de nombres de la máquinas virtuales

        // Preparar y comprobar ejecutable
        System.out.println("COMPROBACIÓN PREVIA EJERCICIOS");

        if (!Util.checkPath(EXEFILE)) {
            System.err.print("Error al comprobar archivo VBoxManage; ");
            System.err.println(LOGMSG);
            System.err.println("Compruebe que virtualBox está instalado y que la ruta de instalación coincide");
            System.exit(0);
        }
        System.out.println();

        // EJERCICIO 1
        System.out.println("EJERCICIO 1: Mostrar máquinas virtuales actuales en virtualBox");
        if ((vmNamesList = ejercicio_1(EXEFILE)) == null) {
            System.err.print("Error al ejecutar proceso; ");
            System.err.println(LOGMSG);
            System.exit(0);

        } else if (vmNamesList.isEmpty()) {
            System.err.println("Virtual Box no contiene ninguna máquina virtual actualmente");
            System.err.println("El resto de los ejercicios no podrán hacerse");
            System.exit(0);

        } else { // SUCCESS
            System.out.println("Lista máquinas virtuales:");
            vmNamesList.forEach(x -> System.out.printf("- %s%n", x));
            System.out.println();
        }

        // EJERCICIO 2
        System.out.println("EJERCICIO 2: Elegir máquina virtual y cambiarle la memoria");
        if (ejercicio_2(EXEFILE, vmNamesList)) {
            System.out.println("Memoria asignada con exito");
        } else {
            System.out.println("Memoria no se pudo asignar");
        }
        System.out.println();

        // EJERCICIO 3
        System.out.println("EJERCICIO 3: Elegir máquina virtual y apagarla");
        if (ejercicio_3(EXEFILE, vmNamesList)) {
            System.out.println("la máquina virtual se está/se ha apagado??");
        } else {
            System.out.println("la máquina virtual no se pudo apagar");
        }
        System.out.println();

        // EJERCICIO 4
        System.out.println("EJERCICIO 4: Elegir máquina virtual y ponerle una descripción");
        if (ejercicio_4(EXEFILE, vmNamesList)) {
            System.out.println("descripción asignada con éxito a la máquina virtual");
        } else {
            System.out.println("descripción no se pudo asignar a la máquina virtual");
        }
        System.out.println();
    }

    /**
     *
     * @param exeFile
     * @return
     */
    private static List<String> ejercicio_1(File exeFile) {
        Log.LOGGER.info("----------------------------------------------------");
        Log.LOGGER.info("INICIO EJERCICIO 1 ****");
        // lista de solo los nombres de las 
        List<String> result = new ArrayList<>();

        // Preparar argumentos necesarios para capturar lista máquinas virtuales
        List<String> args = new ArrayList<>();
        args.add("list");
        args.add("vms");

        // Generar proceso, ejecutarlo y capturar salida
        CustomProcess ej1 = new CustomProcess("Ejercicio 1", exeFile, args);
        ej1.runProcess();

        // Modificar lista para solo mostrar nombres
        Pattern pattern = Pattern.compile("\"([^\"]*)\"*+");
        int i = 0;
        for (String vm : ej1.getStdout()) {
            Matcher m = pattern.matcher(vm);
            if (m.find()) {
                String mvName = m.group(1);
                Log.LOGGER.info(String.format("Capturado nombre máquina virtual %d: %s", i++, mvName));
                result.add(mvName);
            }
        }
        Log.LOGGER.info("Lista nombres vm completada");
        return result;

    }

    private static boolean ejercicio_2(File exeFile, List<String> vmNamesList) {
        Log.LOGGER.info("----------------------------------------------------");
        Log.LOGGER.info("INICIO EJERCICIO 2 ****");
        // Usuario elige mv a modificar, según lista
        String vmName = AskUser.chooseVm(vmNamesList);

        // Usuario indica memoria a asignar
        int mem = AskUser.askMemory(vmName);

        // Preparar argumentos necesarios para asignar memoria a máquina
        List<String> args = new ArrayList<>();
        args.add("modifyvm");
        args.add(vmName);
        args.add("--memory");
        args.add(String.format("%d", mem));

        // Generar proceso, ejecutarlo y capturar salida
        CustomProcess ej2 = new CustomProcess("Ejercicio 2", exeFile, args);
        ej2.runProcess();
        if (ej2.getExitValue() != 0) {
            for (String line : ej2.getStdout()) {
                System.out.print(line);
            }
        }

        return ej2.getExitValue() == 0;
        /* OUTPUT IF MEM TOO LONG        
PS D:\source> & 'C:\Program Files\Oracle\VirtualBox\VBoxManage.exe' modifyvm Win10_1 --memory 10000000
VBoxManage.exe: error: Invalid RAM size: 10000000 MB (must be in range [4, 2097152] MB)
[  INFO] [2021-10-23 09:21:09] es.itrafa.dam.psp.ud1.t1.AskUser chooseVm:
--> Usuario seleccionó 1
[  INFO] [2021-10-23 09:21:09] es.itrafa.dam.psp.ud1.t1.AskUser chooseVm:
--> Seleccion válida; máquina virtual elegida: Win10_1
[  INFO] [2021-10-23 09:21:09] es.itrafa.dam.psp.ud1.t1.AskUser askMemory:
VBoxManage.exe: error: Details: code E_INVALIDARG (0x80070057), component SessionMachine, interface IMachine, callee IUnknown
VBoxManage.exe: error: Context: "COMSETTER(MemorySize)(ValueUnion.u32)" at line 638 of file VBoxManageModifyVM.cpp
PS D:\source>
         */
    }

    private static boolean ejercicio_3(File exeFile, List<String> vmNamesList) {
        Log.LOGGER.info("----------------------------------------------------");
        Log.LOGGER.info("INICIO EJERCICIO 3 ****");
        // Usuario elige mv a apagar, según lista (solo encendidas?)
        String vmName = AskUser.chooseVm(vmNamesList);

        // Preparar argumentos necesarios para asignar memoria a máquina
        List<String> args = new ArrayList<>();
        args.add("controlvm");
        args.add(vmName);
        args.add("acpipowerbutton");

        // Generar proceso, ejecutarlo y capturar salida
        CustomProcess ej3 = new CustomProcess("Ejercicio 3", exeFile, args);
        ej3.runProcess();
        if (ej3.getExitValue() != 0) {
            for (String line : ej3.getStdout()) {
                System.out.print(line);
            }
        }

        return ej3.getExitValue() == 0;
    }

    private static boolean ejercicio_4(File exeFile, List<String> vmNamesList) {
        Log.LOGGER.info("----------------------------------------------------");
        Log.LOGGER.info("INICIO EJERCICIO 4 ****");

        // Usuario elige mv para modificar descripción
        String vmName = AskUser.chooseVm(vmNamesList);

        // Usuario indica escribe descripción (varias lineas?)
        String desc = AskUser.askDesc(vmNamesList);
        
        // Preparar argumentos necesarios para modificar descripción
        List<String> args = new ArrayList<>();
        args.add("modifyvm");
        args.add(vmName);
        args.add("--description");
        args.add(desc);
        
        // Generar proceso, ejecutarlo y capturar salida
        CustomProcess ej4 = new CustomProcess("Ejercicio 4", exeFile, args);
        ej4.runProcess();
        if (ej4.getExitValue() != 0) {
            for (String line : ej4.getStdout()) {
                System.out.print(line);
            }
        }

        return ej4.getExitValue() == 0;
    }
}
