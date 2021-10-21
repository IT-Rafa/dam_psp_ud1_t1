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
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author rafa
 */
public class Main {
    static final Logger rootLog = Logger.getLogger("");
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        rootLog.setLevel(Level.INFO);
        rootLog.getHandlers()[0].setLevel(Level.ALL);
        
        
        rootLog.finest("INICIO TAREA PSP_UD1_T1");

        // Variables necesarias para todos los ejercicios
        List<String> vmInVBoxList;
        Path exeVboxPath = Paths.get("C:\\Program Files\\Oracle\\VirtualBox\\vboxmanage");

        // Comprobamos ejecutable
        rootLog.info(String.format("Comprobamos ejecutable \"%s\"", exeVboxPath));
        if (checkPath(exeVboxPath)) {
            rootLog.info(String.format("Archivo \"%s\" existe y tiene permisos de ejecución", exeVboxPath));
        } else {
            System.exit(0);
        }

        //Ejercicio 1): Listar las máquinas virtuales que se tiene en Virtual Box 
        vmInVBoxList = ejercicio1(exeVboxPath);
        rootLog.info("Salida Ejercicio 1");
        if (vmInVBoxList == null) {
            System.err.println("Error al buscar máquinas virtuales");
            System.exit(0);
        } else if (vmInVBoxList.isEmpty()) {
            System.err.println("No existen máquinas virtuales creadas");
            System.err.println("El resto de los ejercicios no se podrán ejecutar");
            System.exit(0);
        } else {
            vmInVBoxList.forEach((vm) -> {
                System.out.println(vm);
            });
        }

        /*
        Ejercicio 2): A partir del nombre de la máquina virtual modificar su ram
            , debéis pedir al usuario los datos necesarios:
            Comando necesario a ejecutar:
            <Path_to_VirtualBox_executable>\\vboxmanage modifyvm "nombremáquina" --memory memoria
            Comprobado en máquina:
            'C:\Program Files\Oracle\VirtualBox\VBoxManage.exe' modifyvm Win10_1 --memory 4096
         */
        rootLog.info("Ejercicio 2 Iniciando");
        ejercicio2(exeVboxPath);
        /* Ejercicio 3): Apagar desde Java alguna máquina que está arrancada, 
            debéis pedir al usuario los datos necesarios:
            Comando necesario a ejecutar
            <Path_to_VirtualBox_executable>\\vBoxManage controlvm "nombremáquina" acpipowerbutton
            Comprobado en máquina:
            'C:\Program Files\Oracle\VirtualBox\VBoxManage.exe' modifyvm Win10_1 acpipowerbutton
         */
        rootLog.info("Ejercicio 3 Iniciando");
        ejercicio3(exeVboxPath);

        /* Ejercicio 4): Agregar una descripción a una máquina:
            Comando necesario a ejecutar:
            <Path_to_VirtualBox_executable>\\vboxmanage modifyvm "nombremáquina" --description "xxxx"
            Comprobado en máquina:
            'C:\Program Files\Oracle\VirtualBox\VBoxManage.exe' modifyvm Win10_1 --description "Win10_1"
         */
        rootLog.info("Ejercicio 4 Iniciando");
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
        rootLog.fine("Ejercicio 1 Iniciando");

        List<String> result = new ArrayList<>(); // lista vm
        Runtime rt; //  interface with the environment in which the application is running
        // get system interface of this program 
        rt = Runtime.getRuntime();

        try {
            Process pr;
            rootLog.finest("Ejecutando proceso hijo");
            pr = rt.exec(vboxPath + " list vms");

            InputStream input;
            input = pr.getInputStream();

            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(input));

            rootLog.info("Salida del proceso");
            String linea;
            String regex = "(\")(.+)(\")(.*)";
            Pattern pattern = Pattern.compile(regex);

            while ((linea = br.readLine()) != null) {
                Matcher m = pattern.matcher(linea);
                if (m.find()) {
                    result.add(m.group(2));
                }
            }
            br.close();

            try {
                int exitProcess = pr.waitFor();
                if (exitProcess == 0) {
                    rootLog.info("El proceso finalizo correctamente");
                } else {
                    rootLog.severe("El proceso finalizo con errores");

                }

            } catch (InterruptedException ex) { // needed for exec()
                Logger.getLogger(Main.class.getName()).
                        log(Level.SEVERE, null, ex.getLocalizedMessage());
                System.exit(1);
            }

        } catch (IOException ex) { // needed for InputStreamReader(input)
            rootLog.severe(String.format("\"Error al ejecutar proceso: %s", ex.getLocalizedMessage()));
            System.exit(2);
        }
        return result;
    }

    static private void ejercicio2(Path vboxPath) {
        /* 
         * PASOS:    
         *  1 Preguntar que máquina desas cambiar - Mostrar opciones vistas
         *  2 Preguntar la cantidad de memoria a asignar
         *  3 Hacer llamada desde nuevo proceso
         */
        System.out.printf("Ejercicio 2 está pendiente%n");
    }

    static private void ejercicio3(Path vboxPath) {
        /* 
         * PASOS:    
         *  1 Preguntar que máquina desas apagar - Mostrar opciones vistas
         *  2 Comprobar si ya está apagada con proceso intermedio ?
         *  3 Hacer llamada desde nuevo proceso
         */
        System.out.printf("Ejercicio 3 está pendiente%n");
    }

    static private void ejercicio4(Path vboxPath) {
        /* 
         * PASOS:    
         *  1 Seleccionar que mv desas cambiar - Mostrar opciones vistas
         *  2 Pedir descripción a añadir
         *  3 Hacer llamada desde nuevo proceso
         */
        System.out.printf("Ejercicio 4 está pendiente%n");

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
