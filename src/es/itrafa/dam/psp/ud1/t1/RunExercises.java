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
import es.itrafa.dam.psp.ud1.t1.AskUser;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author rafa
 */
public class RunExercises {

    private static final Logger LOGGER = Logger.getLogger(RunExercises.class.getSimpleName());

    static {
        Locale.setDefault(new Locale("en", "EN"));
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%4$6s] [%1$tF %1$tT] %2$s:%n         %5$s%n");

        FileHandler fileHandler;

        try {
            fileHandler = new FileHandler("app.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);

        } catch (IOException | SecurityException ex) {
            Logger.getLogger(RunExercises.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        LOGGER.setUseParentHandlers(false);
        LOGGER.info("Inicio dam_psp_ud1_t1");
        List<String> vmList = null;
        Path exeVboxPath = Paths.get("C:\\Program Files\\Oracle\\VirtualBox\\vboxmanage.exe");

        //EJERCICIO 1):
        //Listar las máquinas virtuales que se tiene en Virtual Box
        System.out.println("Ej 1: Mostrar máquinas virtuales existentes en virtualBox.");

        // Comprobamos estado archivo
        if (checkPath(exeVboxPath)) {

            vmList = ejercicio1(exeVboxPath);
            if (vmList.isEmpty()) {
                System.err.println("virtualBox no tiene máquinas virtuales creadas");
                System.err.println("El resto de los ejercicios no podrán realizarse");
                System.exit(0);

            } else {
                for (String vm : vmList) {
                    System.out.println(vm);
                }

            }
        } else {
            System.err.printf("El archivo \"%s\" no existe o no es ejecutable%n",
                    exeVboxPath);
            System.err.printf("Los ejercicios no pueden realizarse%n");
            System.err.printf("Compruebe que virtualBox está instalado"
                    + " y que la ruta de instación coincide con la indicada%n");
            System.exit(0);
        }
        // Ejecutar programa con los argumentos


        /*
        Ejercicio 2): A partir del nombre de la máquina virtual modificar su ram
            , debéis pedir al usuario los datos necesarios:
            Comando necesario a ejecutar:
            <Path_to_VirtualBox_executable>\\vboxmanage modifyvm "nombremáquina" --memory memoria
            Comprobado en máquina:
            'C:\Program Files\Oracle\VirtualBox\VBoxManage.exe' modifyvm Win10_1 --memory 4096
         */
        ejercicio2(exeVboxPath, vmList);
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
        Runtime rt;
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
                Logger.getLogger(RunExercises.class.getName()).
                        log(Level.SEVERE, null, ex.getLocalizedMessage());
                System.exit(1);
            }

        } catch (IOException ex) { // needed for InputStreamReader(input)
            LOGGER.severe(String.format("\"Error al leer salida proceso: %s", ex.getLocalizedMessage()));
            System.exit(2);
        }
        return result;
    }

    static private void ejercicio2(Path vboxPath, List<String> vmList) {
        LOGGER.info("Ejercicio 2 Iniciando");
        LOGGER.severe("Ejercicio 2 En progreso");

        //  1 Preguntar que máquina desas cambiar - Mostrar opciones vistas
        String vm = AskUser.chooseVm(vmList);

        //  2 Preguntar la cantidad de memoria a asignar
        int mem = AskUser.askMemory();

        //  3 Hacer llamada desde nuevo proceso
        Runtime rt = Runtime.getRuntime();
        Process pr;
        LOGGER.info("Ejecutando proceso hijo");

        try {
            pr = rt.exec(vboxPath + " modifyvm " + vm + " --memory " + mem);

            int exitProcess;

            try {
                exitProcess = pr.waitFor();

                if (exitProcess == 0) {
                    LOGGER.info("El proceso finalizo correctamente");
                } else {
                    LOGGER.severe("El proceso finalizo con errores");

                }
            } catch (InterruptedException ex) {
                Logger.getLogger(RunExercises.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(RunExercises.class.getName()).log(Level.SEVERE, null, ex);
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
        LOGGER.info(String.format("Comprobamos estado archivo \"%s\"", exeVboxPath));

        if (!Files.exists(exeVboxPath)) {
            if (!Files.exists(exeVboxPath.getParent())) {
                LOGGER.warning(String.format("La ruta \"%s\" no existe%n", exeVboxPath.getParent()));

            } else {
                LOGGER.warning(String.format("La ruta e\"%s\" no existe, pero no contiene el archivo %s%n",
                        exeVboxPath.getParent(), exeVboxPath.getFileName()));
            }
            return false;

        } else if (!Files.isExecutable(exeVboxPath)) {
            LOGGER.warning(String.format("El archivo \"%s\" existe pero no tiene permisos de ejecución%n", exeVboxPath.getFileName()));
            return false;
        }
        LOGGER.info(String.format("El archivo \"%s\" existe y tiene permisos de ejecución", exeVboxPath));
        return true;
    }

}
