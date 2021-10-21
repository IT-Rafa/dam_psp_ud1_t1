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
package dam.psp.ud1.t1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author rafa
 */
public class Main {

    static final Logger LOGGER
            = Logger.getLogger(Main.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.info("INICIO TAREA PSP_UD1_T1");
        List<String> vmInVBoxList;
        String vboxPath, executable;

        vboxPath = "C:\\Program Files\\Oracle\\VirtualBox\\";
        executable = "vboxmanage";

        /* 
        Ejercicio 1): Listar las máquinas virtuales que se tiene en Virtual Box 
            Comando necesario a ejecutar: 
            <Path_to_VirtualBox_executable>\\vboxmanage list vms
            Comprobado en máquina:
            'C:\Program Files\Oracle\VirtualBox\VBoxManage.exe' list vms
         */
        LOGGER.info("Ejercicio 1 Iniciando");
        vmInVBoxList = ejercicio1(vboxPath, executable);
        LOGGER.info("Salida Ejercicio 1");
        if (vmInVBoxList != null) {
            vmInVBoxList.forEach((vm) -> {
                System.out.println(vm);
            });
        } else {
            System.out.println("No existen máquinas virtuales creadas");
        }

        /*
        Ejercicio 2): A partir del nombre de la máquina virtual modificar su ram
            , debéis pedir al usuario los datos necesarios:
            Comando necesario a ejecutar:
            <Path_to_VirtualBox_executable>\\vboxmanage modifyvm "nombremáquina" --memory memoria
            Comprobado en máquina:
            'C:\Program Files\Oracle\VirtualBox\VBoxManage.exe' modifyvm Win10_1 --memory 4096
         */
        LOGGER.info("Ejercicio 2 Iniciando");
        ejercicio2(vboxPath, executable);
        /* Ejercicio 3): Apagar desde Java alguna máquina que está arrancada, 
            debéis pedir al usuario los datos necesarios:
            Comando necesario a ejecutar
            <Path_to_VirtualBox_executable>\\vBoxManage controlvm "nombremáquina" acpipowerbutton
            Comprobado en máquina:
            'C:\Program Files\Oracle\VirtualBox\VBoxManage.exe' modifyvm Win10_1 acpipowerbutton
         */
        LOGGER.info("Ejercicio 3 Iniciando");
        ejercicio3(vboxPath, executable);

        /* Ejercicio 4): Agregar una descripción a una máquina:
            Comando necesario a ejecutar:
            <Path_to_VirtualBox_executable>\\vboxmanage modifyvm "nombremáquina" --description "xxxx"
            Comprobado en máquina:
            'C:\Program Files\Oracle\VirtualBox\VBoxManage.exe' modifyvm Win10_1 --description "Win10_1"
         */
        LOGGER.info("Ejercicio 4 Iniciando");
        ejercicio4(vboxPath, executable);
    }

    /**
     * Get the a list of names of actual virtual machines in virtualbox
     *
     * @param vboxPath String with path where virtuaBox is installed
     * @param executable name of virtualBox executable
     * @return The list with the virtual machine names or null if empty or error
     */
    static private List<String> ejercicio1(String vboxPath, String executable) {
        List<String> result = new ArrayList<>();
        Runtime rt;
        rt = Runtime.getRuntime();
        try {
            Process pr;
            LOGGER.info("Ejecutando proceso");
            pr = rt.exec(vboxPath + executable + " list vms");

            InputStream input;
            input = pr.getInputStream();

            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(input));

            LOGGER.info("Salida del proceso");
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
                    LOGGER.info("El proceso finalizo correctamente");
                    return result;
                } else {
                    LOGGER.severe(String.format("El proceso finalizo con error%d%n", exitProcess));

                }

            } catch (InterruptedException e) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
            }

        } catch (IOException ex) { // only for exec()
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    static private void ejercicio2(String vboxPath, String executable) {
        /* 
         * PASOS:    
         *  1 Preguntar que máquina desas cambiar - Mostrar opciones vistas
         *  2 Preguntar la cantidad de memoria a asignar
         *  3 Hacer llamada desde nuevo proceso
         */
        System.out.printf("Ejercicio 2 está pendiente%n");
    }

    static private void ejercicio3(String vboxPath, String executable) {
        /* 
         * PASOS:    
         *  1 Preguntar que máquina desas apagar - Mostrar opciones vistas
         *  2 Comprobar si ya está apagada con proceso intermedio ?
         *  3 Hacer llamada desde nuevo proceso
         */
        System.out.printf("Ejercicio 3 está pendiente%n");
    }

    static private void ejercicio4(String vboxPath, String executable) {
        /* 
         * PASOS:    
         *  1 Seleccionar que mv desas cambiar - Mostrar opciones vistas
         *  2 Pedir descripción a añadir
         *  3 Hacer llamada desde nuevo proceso
         */
        System.out.printf("Ejercicio 4 está pendiente%n");

    }
}
