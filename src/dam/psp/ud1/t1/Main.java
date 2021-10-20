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

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafa
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String vboxPath, executable;
        vboxPath = "";
        executable = "vboxmanage";

        /* 
        Ejercicio 1): Listar las máquinas virtuales que se tiene en Virtual Box 
            Comando necesario a ejecutar: 
            <Path_to_VirtualBox_executable>\\vboxmanage list vms 
         */
        System.out.printf("Ejercicio 1:%n");
        ejercicio1(vboxPath, executable);
        /*
        Ejercicio 2): A partir del nombre de la máquina virtual modificar su ram
            , debéis pedir al usuario los datos necesarios:
            Comando necesario a ejecutar:
            <Path_to_VirtualBox_executable>\\vboxmanage modifyvm "nombremáquina" --memory memoria
         */
        System.out.printf("Ejercicio 2:%n");
        ejercicio2(vboxPath, executable);
        /* Ejercicio 3): Apagar desde Java alguna máquina que está arrancada, 
            debéis pedir al usuario los datos necesarios:
            Comando necesario a ejecutar
            <Path_to_VirtualBox_executable>\\vBoxManage controlvm "nombremáquina" acpipowerbutton
         */
        System.out.printf("Ejercicio 3:%n");
        ejercicio3(vboxPath, executable);

        /* Ejercicio 4): Agregar una descripción a una máquina:
            Comando necesario a ejecutar:
            <Path_to_VirtualBox_executable>\\vboxmanage modifyvm "nombremáquina" --description "xxxx"
         */
        System.out.printf("Ejercicio 4:%n");
        ejercicio4(vboxPath, executable);
    }

    /**
     * Show actual virtualBox machines using virtualBox console command
     */
    static private void ejercicio1(String vboxPath, String executable) {
        Runtime rt;
        Process pr;
        rt = Runtime.getRuntime();

        try {
            pr = rt.exec(vboxPath + executable);
            pr.waitFor();

        } catch (IOException ex) { // only for exec()
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (InterruptedException ex) { // only for waitFor()
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.printf("Ejercicio 1 está en proceso%n");
    }

    static private void ejercicio2(String vboxPath, String executable) {
        System.out.printf("Ejercicio 2 está pendiente%n");
    }

    static private void ejercicio3(String vboxPath, String executable) {
        System.out.printf("Ejercicio 3 está pendiente%n");
    }

    static private void ejercicio4(String vboxPath, String executable) {
        System.out.printf("Ejercicio 4 está pendiente%n");

    }
}
