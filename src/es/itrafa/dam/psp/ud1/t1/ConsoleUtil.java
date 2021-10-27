/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.itrafa.dam.psp.ud1.t1;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Collection of utilities for User interactions in console
 *
 * @author it-ra
 */
public class ConsoleUtil {

    /**
     * Show a console menu with the title, and the options from arguments. This
     * menu will be displayed again until a right option be selected by user.
     * The options are numbered correlatively from one.
     *
     * @param menuTitle String title of menu
     * @param optionsMenu List<String> List of options to show
     * @return int - The option selected by user
     */
    public static int getOpFromMenu(String menuTitle, List<String> optionsMenu) {
        int op;

        int maxLenght; // cant of chars for menu border == The longest line
        maxLenght = menuTitle.length();

        do {
            // Calcule max lenght of options
            for (String opMenu : optionsMenu) {
                if (opMenu.length() > maxLenght) {
                    maxLenght = opMenu.length();
                }
            }
            // Menu Header
            maxLenght += 10; // space for option number
            for (int i = 0; i < maxLenght; i++) {
                System.out.printf("%c", '=');
            }
            System.out.println();

            String bordertitle = String.format("| %s", menuTitle);
            System.out.printf(bordertitle);

            for (int i = 0; i < (maxLenght - bordertitle.length())-1; i++) {
                System.out.print(" ");
            }
            System.out.printf("|%n");

            for (int i = 0; i < maxLenght; i++) {
                System.out.printf("%c", '=');
            }
            System.out.println();

            // Menu options 
            int opNum = 1; // Options start with one
            for (String opMenu : optionsMenu) {
                String option = String.format("|  %d: %s", opNum++, opMenu);
                System.out.printf(option);

                for (int i = 0; i < (maxLenght - option.length() - 1); i++) {
                    System.out.print(" ");
                }
                System.out.printf("|%n");
            }
            for (int i = 0; i < maxLenght; i++) {
                System.out.printf("%c", '-');
            }
            System.out.println();
            System.out.print("Seleccione una opción: ");

            // ask user to select a option 
            try {
                Scanner input = new Scanner(System.in);
                op = input.nextInt();
            } catch (InputMismatchException ex) {
                op = 0;
            }
            // check if is a valid option
            if (op < 1 || op > optionsMenu.size()) {
                System.out.printf("Opción no válida. Seleccione opción del menú%n%n");
                op = 0;
            }
        } while (op == 0); // zero is never a valid option

        System.out.println();
        return op;
    }

    public static void pressReturnKey(String msg) {
        try {
            System.out.println(msg);
            Scanner input = new Scanner(System.in);
            input.nextLine();
        } catch (InputMismatchException ex) {

        }

    }
}
