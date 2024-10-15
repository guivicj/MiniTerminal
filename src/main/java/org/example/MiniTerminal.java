package org.example;

import org.example.error.DirectoryDoesNotExist;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MiniTerminal {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        MiniFileManager mf = new MiniFileManager();
        boolean testOK = false;

        do {
            try {
                System.out.print("Operación ...: ");
                String opr = sc.nextLine();
                testOK = MiniFileManager.analizarOperacion(mf, opr);
            } catch (DirectoryDoesNotExist e) {
                e.getMessage();
            }
        } while (!testOK);

    }
}