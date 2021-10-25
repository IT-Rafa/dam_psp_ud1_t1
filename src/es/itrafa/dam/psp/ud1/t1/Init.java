/*
 * File Name
 * Project 
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Resumen enunciado: El programa mostrará un menú que permitirá al usuario
 * ejecutar las siguientes operaciones: Ejercicio 1): Listar las máquinas
 * virtuales que se tiene en Virtual Box Ejercicio 2): A partir del nombre de la
 * máquina virtual modificar su ram, debéis pedir al usuario los datos
 * necesarios: Ejercicio 3): Apagar desde Java alguna máquina que está
 * arrancada, debéis pedir al usuario los datos necesarios: Ejercicio 4):
 * Agregar una descripción a una máquina:
 *
 *
 * @author rafa
 */
public class Init {

    // Esta ruta dependerá del equipo y la instalación de virtualBox
    static final private File EXEFILE = new File("C:\\Program Files\\Oracle\\VirtualBox\\VBoxManage.exe");

    public static void main(String[] args) {
        Log.LOGGER.info("INICIO PROGRAMA ****");

        // Comprobación instalación previa programa virtualBox
        if (!Util.checkPath(EXEFILE)) {
            UI.showNofoundExeMsg();
            System.exit(1);
        }
        // Mostrar menú
        while (true) { // out of program using menú
            initTarea();
        }

    }

    private static void initTarea() {
        int op;
        op = UI.mainMenu();
        runExercise(op);
    }

    /**
     * @param args the command line arguments
     */
    private static void runExercise(int op) {

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
        }

    }
}
