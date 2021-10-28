/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.itrafa.util.console;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Create a menu for console to show formated menu with the options, ask user
 * to select and check that selection is one of options.
 *
 * @author it-ra
 */
public class AskOpFromMenu {

    private final String title;
    private List<String> options;
    private int selectedOption;

    // CONSTRUCTOR
    public AskOpFromMenu(String menuTitle, List<String> optionsMenu) {
        this.title = menuTitle;
        this.options = optionsMenu;
    }

    // GETTER & SETTER
    /**
     * @return the selectedOption
     */
    public int getSelectedOption() {
        return selectedOption;
    }

    // METHODS
    public int showAndAsk() {

        int maxLenght; // cant of chars for menu border == The longest line
        maxLenght = title.length();

        do {
            // Calcule max lenght of options
            for (String opMenu : options) {
                if (opMenu.length() > maxLenght) {
                    maxLenght = opMenu.length();
                }
            }
            // AskInteger Header
            maxLenght += 10; // space for option number
            for (int i = 0; i < maxLenght; i++) {
                System.out.printf("%c", '=');
            }
            System.out.println();

            String bordertitle = String.format("| %s", title);
            System.out.printf(bordertitle);

            for (int i = 0; i < (maxLenght - bordertitle.length()) - 1; i++) {
                System.out.print(" ");
            }
            System.out.printf("|%n");

            for (int i = 0; i < maxLenght; i++) {
                System.out.printf("%c", '=');
            }
            System.out.println();

            // AskInteger options 
            int opNum = 1; // Options start with one
            for (String op : options) {
                String option = String.format("|  %d: %s", opNum++, op);
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
                selectedOption = input.nextInt();
            } catch (InputMismatchException ex) {
                selectedOption = 0;
            }
            // check if is a valid option
            if (selectedOption < 1 || selectedOption > options.size()) {
                System.out.printf("Opción no válida. Seleccione opción del menú%n%n");
                selectedOption = 0;
            }
        } while (selectedOption == 0); // zero is never a valid option

        System.out.println();
        return selectedOption;
    }

}
