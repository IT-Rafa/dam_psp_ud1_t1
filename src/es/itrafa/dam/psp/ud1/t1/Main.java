/*
 * Tarea 1
 * Unidad 1 de PSP(Programación de Servicios y Procesos)
 * Módulo DAM (Desarrollo Aplicaciones Multimedia)
 *
 * Enunciado:
 *
 * Ejercicio 1): Listar las máquinas virtuales que se tiene en Virtual Box
 * Comando necesario a ejecutar: C:\\Program Files\\Oracle\\VirtualBox\\vboxmanage list vms
 * 
 * Ejercicio 2): A partir del nombre de la máquina virtual modificar su ram, debéis pedir al usuario los datos necesarios:
 * Comando necesario a ejecutar: C:\\Program Files\\Oracle\\VirtualBox\\vboxmanage modifyvm "nombremáquina" --memory memoria
 * 
 * Ejercicio 3): Apagar desde Java alguna máquina que está arrancada, debéis pedir al usuario los datos necesarios:
 * Comando necesario a ejecutar: C:\\Program Files\\Oracle\\VirtualBox\\vBoxManage controlvm "nombremáquina" acpipowerbutton
 * 
 * Ejercicio 4): Agregar una descripción a una máquina:
 * Comando necesario a ejecutar: C:\\Program Files\\Oracle\\VirtualBox\\vboxmanage modifyvm "nombremáquina" --description "xxxx"
 */
package es.itrafa.dam.psp.ud1.t1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author rafa
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getSimpleName());

    ;

    static {
        Locale.setDefault(new Locale("en", "EN"));
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%4$6s] [%1$tF %1$tT] %3$s: %5$s%n");

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.info("Inicio dam_psp_ud1_t1");

        // Variables necesarias para todos los ejercicios
        List<String> vmInVBoxList;
        Path exeVboxPath = Paths.get("C:\\Program Files\\Oracle\\VirtualBox\\vboxmanage.exe");

        // Comprobamos ejecutable
        LOGGER.info(String.format("Comprobamos ejecutable \"%s\"", exeVboxPath));
        if (checkPath(exeVboxPath)) {
            LOGGER.info(String.format("El archivo \"%s\" existe y tiene permisos de ejecución", exeVboxPath));
        } else {
            System.exit(0);
        }

        //Ejercicio 1): Listar las máquinas virtuales que se tiene en Virtual Box 
        vmInVBoxList = ejercicio1(exeVboxPath);
        LOGGER.info("Salida Ejercicio 1");
        if (vmInVBoxList == null) {
            System.err.println("Error al buscar máquinas virtuales");
            System.exit(0);
        } else if (vmInVBoxList.isEmpty()) {
            System.err.println("No existen máquinas virtuales creadas");
            System.err.println("El resto de los ejercicios no se podrán ejecutar");
            System.exit(0);
        } else {
            for (String vm : vmInVBoxList) {
                System.out.println(vm);
            }

        }

        /*
        Ejercicio 2): A partir del nombre de la máquina virtual modificar su ram
            , debéis pedir al usuario los datos necesarios:
            Comando necesario a ejecutar:
            <Path_to_VirtualBox_executable>\\vboxmanage modifyvm "nombremáquina" --memory memoria
            Comprobado en máquina:
            'C:\Program Files\Oracle\VirtualBox\VBoxManage.exe' modifyvm Win10_1 --memory 4096
         */
        ejercicio2(exeVboxPath, vmInVBoxList);
        /* Ejercicio 3): Apagar desde Java alguna máquina que está arrancada, 
            debéis pedir al usuario los datos necesarios:
            Comando necesario a ejecutar
            <Path_to_VirtualBox_executable>\\vBoxManage controlvm "nombremáquina" acpipowerbutton
            Comprobado en máquina:
            'C:\Program Files\Oracle\VirtualBox\VBoxManage.exe' modifyvm Win10_1 acpipowerbutton
         */
        ejercicio3(exeVboxPath);

        /* Ejercicio 4): Agregar una descripción a una máquina:
            Comando necesario a ejecutar:
            <Path_to_VirtualBox_executable>\\vboxmanage modifyvm "nombremáquina" --description "xxxx"
            Comprobado en máquina:
            'C:\Program Files\Oracle\VirtualBox\VBoxManage.exe' modifyvm Win10_1 --description "Win10_1"
         */
        ejercicio4(exeVboxPath);
    }

    /**
     * Get the a list of names of actual virtual machines in virtualbox
     *
     * @param vboxPath String with path where virtuaBox is installed
     * @param executable name of virtualBox executable
     * @return The list with the virtual machine names or null if empty or error
     */
    static private List<String> ejercicio1(Path vboxPath) {
        LOGGER.fine("Ejercicio 1 Iniciando");

        List<String> result = new ArrayList<>(); // lista vm
        Runtime rt; //  interface with the environment in which the application is running
        // get system interface of this program 

        System.out.printf("Mostramos máquinas virtuales activas en virtualBox:%n");
        rt = Runtime.getRuntime();

        try {
            Process pr;
            LOGGER.info("Ejecutando proceso hijo");
            pr = rt.exec(vboxPath + " list vms");

            //Capturando salida consola del proceso
            InputStream input;
            input = pr.getInputStream();

            //Preparando lectura salida consola del proceso
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(input));

            //Preparando filtro para string de salida
            String linea;
            String regex = "(\")(.+)(\")(.*)";
            Pattern pattern = Pattern.compile(regex);

            // Leyendo cada línea salida del proceso
            while ((linea = br.readLine()) != null) {
                // Filtrando dato que nos interesa (nombre mv)
                Matcher m = pattern.matcher(linea);
                if (m.find()) {
                    result.add(m.group(2));
                }
            }
            // Fin lectura
            br.close();

            // Control fin proceso
            try {
                int exitProcess = pr.waitFor();
                if (exitProcess == 0) {
                    LOGGER.info("El proceso finalizo correctamente");
                } else {
                    LOGGER.severe("El proceso finalizo con errores");

                }

            } catch (SecurityException | InterruptedException ex) { // for exec()
                Logger.getLogger(Main.class.getName()).
                        log(Level.SEVERE, null, ex.getLocalizedMessage());
                System.exit(1);
            }

        } catch (IOException ex) { // needed for InputStreamReader(input)
            LOGGER.severe(String.format("\"Error al leer salida proceso: %s", ex.getLocalizedMessage()));
            System.exit(2);
        }
        return result;
    }

    static private void ejercicio2(Path vboxPath, List<String> vmInVBoxList) {
        LOGGER.info("Ejercicio 2 Iniciando");
        LOGGER.severe("Ejercicio 2 En progreso");
        /* 
         * PASOS:    
         *  1 Preguntar que máquina desas cambiar - Mostrar opciones vistas
         *  2 Preguntar la cantidad de memoria a asignar
         *  3 Hacer llamada desde nuevo proceso
         */
        Scanner inputOp = new Scanner(System.in);
        final int MAXMEM = 999;
        final int MINMEM = 0;
        int maxvm = -1;
        int op = 0;

        while (op < 1 || op >= maxvm) {
            System.out.printf("Indique a que máquina virtual desea modificarle1"
                    + "1 la RAM asignada:%n");
            maxvm = 1;
            for (String vm : vmInVBoxList) {
                System.out.printf("%d: %s%n", maxvm++, vm);
            }
            System.out.printf("Seleccionar opción: ");
            op = inputOp.nextInt();
            if (op < 1 || op >= maxvm) {
                System.out.printf("%nOpción no válida. Seleccione una del menú%n");
            }
        }
        LOGGER.info(String.format("Usuario selecciono %s", vmInVBoxList.get(--op)));

        int mem = 0;
        while (mem < 999 || mem >= 999999999) {
            System.out.printf("Indique la cantidad de memoria RAM que desea asignar (en Megas):");
            String memSt = inputOp.nextLine();

            System.out.printf("%nIntroducido %s%n", memSt);
            if (memSt.matches("\\d{3,10}")) {
                mem = Integer.valueOf(memSt);
                System.out.printf("%nmem = %d%n", mem);
            } else {
                mem = 0;
            }
            if (mem < 999 || mem >= 999999999) {
                System.out.printf("%nOpción no válida. Seleccione una del menú%n");
            }
        }
        /* OUTPUT IF MEM TOO LONG        
PS D:\source> & 'C:\Program Files\Oracle\VirtualBox\VBoxManage.exe' modifyvm Win10_1 --memory 10000000
VBoxManage.exe: error: Invalid RAM size: 10000000 MB (must be in range [4, 2097152] MB)
VBoxManage.exe: error: Details: code E_INVALIDARG (0x80070057), component SessionMachine, interface IMachine, callee IUnknown
VBoxManage.exe: error: Context: "COMSETTER(MemorySize)(ValueUnion.u32)" at line 638 of file VBoxManageModifyVM.cpp
PS D:\source>
         */

        Runtime rt = Runtime.getRuntime();
        Process pr;
        LOGGER.info("Ejecutando proceso hijo");

        try {
            pr = rt.exec(vboxPath + " modifyvm " + vmInVBoxList.get(op) + " --memory " + mem);

            int exitProcess;

            try {
                exitProcess = pr.waitFor();

                if (exitProcess == 0) {
                    LOGGER.info("El proceso finalizo correctamente");
                } else {
                    LOGGER.severe("El proceso finalizo con errores");

                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static private void ejercicio3(Path vboxPath) {
        LOGGER.info("Ejercicio 3 Iniciando");
        LOGGER.severe("Ejercicio 3 No implantado");
        /* 
         * PASOS:    
         *  1 Preguntar que máquina desas apagar - Mostrar opciones vistas
         *  2 Comprobar si ya está apagada con proceso intermedio ?
         *  3 Hacer llamada desde nuevo proceso
         */
    }

    static private void ejercicio4(Path vboxPath) {
        LOGGER.info("Ejercicio 4 Iniciando");
        LOGGER.severe("Ejercicio 4 No implantado");
        /* 
         * PASOS:    
         *  1 Seleccionar que mv desas cambiar - Mostrar opciones vistas
         *  2 Pedir descripción a añadir
         *  3 Hacer llamada desde nuevo proceso
         */

    }

    private static boolean checkPath(Path exeVboxPath) {
        if (!Files.exists(exeVboxPath)) {
            System.err.printf("El programa \"%s\" no existe%n", exeVboxPath);
            System.err.printf("Compruebe que virtualBox está instalado y que la ruta coincide%n", exeVboxPath);
            return false;
        } else if (!Files.isExecutable(exeVboxPath)) {
            System.err.printf("El archivo \"%s\" no tiene permisos de ejecución%n", exeVboxPath.getFileName());
            return false;
        }
        return true;
    }

}
