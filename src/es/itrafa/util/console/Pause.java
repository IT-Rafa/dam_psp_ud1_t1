/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.itrafa.util.console;

import es.itrafa.dam.psp.ud1.t1.Log;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author rafa
 */
public class Pause {

    public static void pressReturnToCont(String msg) {
        try {
            System.out.println(msg);
            Scanner input = new Scanner(System.in);
            input.nextLine();
        } catch (InputMismatchException ex) {
            Log.LOGGER.severe(String.format("Error with console pause: %s%n", ex));
        }

    }
}
