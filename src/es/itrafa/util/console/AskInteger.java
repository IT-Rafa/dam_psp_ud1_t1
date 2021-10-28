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
 * Create a menu for console to show formated menu with the options, ask user to
 * select and check that selection is one of options.
 *
 * @author it-ra
 */
public class AskInteger {
    // ATTRIBUTES

    private final String question;
    private final int min;
    private final int max;
    private int answer;

    // CONSTRUCTOR
    public AskInteger(String question, int min, int max) {
        this.question = question;
        this.min = min;
        this.max = max;
    }

    // GETTER & SETTER
    /**
     * @return the selectedOption
     */
    // METHODS
    public String getOpAsString() {
        int result = getOp();
        return String.format("%d", result);
    }

    public int getOp() {
        boolean wrong = true;
        while (wrong) {
            System.out.print(question);

            try {
                Scanner input = new Scanner(System.in);
                answer = input.nextInt();
            } catch (InputMismatchException ex) {
                System.out.printf("Fuera de rango. Debe estar entre %d y %d%n", min, max);
            }
            // check if is a valid option
            if (answer < min || answer > max) {
                System.out.printf("Fuera de rango. Debe estar entre %d y %d%n", min, max);
            } else {
                wrong = false;
            }
        }

    }
    // zero is never a valid option

    System.out.println ();
    return selectedOption ;
}

}
