/*
 * File Name: Init.java
 * Project: dam_psp_ud1_t1
 * Enunciado completo en docs
 * Causa: Tarea para 
 *
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.itrafa.dam.psp.ud1.t1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Define la puesta en marcha de la tarea
 *
 * @author rafa
 */
public class Init {

    // Supuesta ruta instalación de virtualBox
    private static final File EXEFILE = new File("C:\\Program Files\\Oracle\\VirtualBox\\VBoxManage.exe");

    /**
     * Evita ejecución si instalación virtualBox es errónea e inicia la tarea
     *
     * @param args
     */
    public static void main(String[] args) {
        Log.LOGGER.info("INICIO PROGRAMA ****");

        // COMPROBACIÓN REQUISITOS
        // Comprobación instalación previa programa virtualBox
        if (!Exercises.checkPath(EXEFILE)) {
            ConsoleUI.showPathNoFound();
            System.exit(1);
        }

        // INICIO TAREA
        while (true) {
            int op;
            String menuTitle = "Menú PSP_UD1_T1: vBox ejercicios";
            List<String> optionsMenu = new ArrayList<>();

            optionsMenu.add("Ej_1: Mostrar máquinas virtuales");
            optionsMenu.add("Ej_2: Cambiar RAM a máquina virtual");
            optionsMenu.add("Ej_3: Apagar máquina virtual");
            optionsMenu.add("Ej_4: Cambiar descripción a máquina virtual");
            optionsMenu.add("FIN PROGRAMA");
            op = ConsoleUtil.getOpFromMenu(menuTitle, optionsMenu);

            switch (op) {

                case 1:
                    Exercises.runExercise1(EXEFILE);
                    break;
                case 2:
                    Exercises.runExercise2(EXEFILE);
                    break;
                case 3:
                    Exercises.runExercise3(EXEFILE);
                    break;
                case 4:
                    Exercises.runExercise4(EXEFILE);
                    break;
                case 5:
                    Log.LOGGER.info("FIN PROGRAMA ****");
                    System.exit(0);

            }
            ConsoleUtil.pressReturnKey("Presione Enter para volver a mostrar menú");
        }

    }

}
