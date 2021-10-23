/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.itrafa.dam.psp.ud1.t1;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author rafa
 */
public class AskUser {

    /**
     *
     * @param vmList
     * @return
     */
    protected static String chooseVm(List<String> vmList) {
        String vm;
        int op;
        boolean validOp;

        Scanner inputOp = new Scanner(System.in);
        op = 0;
        validOp = false;
        while (!validOp) {
            System.out.printf("Indique a que máquina virtual desea asignarle otra RAM.%n");
            System.out.printf("Máquina virtuales actuales:%n");
            int i = 0;
            for (String eachVm : vmList) {
                System.out.printf("%d: %s%n", i, eachVm);
            }
            String opSt = inputOp.nextLine();
            if (op > 0) {
                validOp = true;
            }
            op = Integer.parseUnsignedInt(opSt, 10);

        }

        return vmList.get(op);
    }

    /**
     *
     * @return
     */
    protected static int askMemory() {
        
        /* OUTPUT IF MEM TOO LONG        
PS D:\source> & 'C:\Program Files\Oracle\VirtualBox\VBoxManage.exe' modifyvm Win10_1 --memory 10000000
VBoxManage.exe: error: Invalid RAM size: 10000000 MB (must be in range [4, 2097152] MB)
VBoxManage.exe: error: Details: code E_INVALIDARG (0x80070057), component SessionMachine, interface IMachine, callee IUnknown
VBoxManage.exe: error: Context: "COMSETTER(MemorySize)(ValueUnion.u32)" at line 638 of file VBoxManageModifyVM.cpp
PS D:\source>
         */
        int mem;
        boolean validOp;

        Scanner inputOp = new Scanner(System.in);
        mem = 0;
        validOp = false;
        while (!validOp) {
            System.out.printf("Indique la RAM que desea asignarle.%n");
            System.out.printf("Máquina virtuales actuales:%n");

            String opSt = inputOp.nextLine();
            if (mem > 0) {
                validOp = true;
            }
            mem = Integer.parseUnsignedInt(opSt, 10);

        }

        return mem;
    }
}
