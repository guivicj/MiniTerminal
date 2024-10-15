package org.example;

import org.example.error.DirectoryDoesNotExist;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MiniFileManager {

    private static File directorio;


    public MiniFileManager() {
        directorio = getPwd();
    }


    public static boolean analizarOperacion(MiniFileManager mf, String opr) throws DirectoryDoesNotExist {
        String[] comando = opr.split(" ", 3);
        String operacion = comando[0];

        boolean testOK = false;

        switch (operacion) {
            case "pwd":
                ejecutarPwd(mf);
                break;
            case "cd":
                ejecutarCd(comando[1]);
                break;
            case "ls":
                ejecutarLs();
                break;
            case "ll":
                ejecutarLl();
                break;
            case "mkdir":
                ejecutarMkDir(comando[1]);
                break;
            case "rm":
                ejecutarRm(comando[1]);
                break;
            case "mv":
                ejecutarMv(comando[1], comando[2]);
                break;
            case "help":
                ejecutarHelp();
                break;
            case "exit":
                ejecutarExit();
                testOK = true;
                break;
            default:
                System.out.println("Comando Incorrecto");
        }
        System.out.println("---");
        return testOK;
    }


    private static void ejecutarPwd(MiniFileManager mf) {
        System.out.printf("Ruta ...: %s%n", directorio.getAbsolutePath());
    }

    private static void ejecutarCd(String s) throws DirectoryDoesNotExist {
        if (s.equals("..")) {
            directorio = directorio.getParentFile();
            System.out.printf("Ruta ...: %s%n", directorio.getAbsolutePath());
        } else {
            File f = new File(s);
            if (f.exists() && f.isDirectory()) {
                directorio = f;
                System.out.printf("Ruta ...: %s%n", directorio.getAbsolutePath());
            } else {
                throw new DirectoryDoesNotExist();
            }

        }
    }

    private static void ejecutarLs() {
        File f = new File(directorio.getAbsolutePath() + "/");
        System.out.println(
                """
                        ARCHIVOS Y DIRECTORIOS
                        ======================""");
        boolean info = false;
        printList(f, info);
    }

    private static void ejecutarLl() {
        File f = new File(directorio.getAbsolutePath() + "/");
        System.out.println(
                """
                        NOMBRE          TAMAÑO    FECHA MODIFICACIÓN
                        =============== ======  ======================""");

        boolean info = true;
        printList(f, info);
    }


    private static void ejecutarMkDir(String s) {
        File f = new File(directorio.getAbsolutePath() + "/" + s);
        boolean archivo = f.mkdir();
        if (archivo) {
            System.out.println("Directorio Creado Correctamente");
        } else {
            System.out.println("ERROR: Directorio no Creado");
        }

    }

    private static void ejecutarRm(String s) {
        File f = new File(directorio.getAbsolutePath() + "/" + s);
        boolean archivo = f.delete();
        if (archivo) {
            System.out.println("Directorio Eliminado Correctamente");
        } else {
            System.out.println("ERROR: Directorio no Eliminiado");
        }
    }


    private static void ejecutarMv(String f1, String f2) {
        File f = new File(f1);
        if (f.exists() && f.isDirectory()) {
            boolean mvOK = f.renameTo(new File(f2));
            System.out.printf("%s%n", mvOK ? "Cambio Ejecutado Correctamente" : "Cambio NO Ejecutado");
        } else {
            System.out.println("ERROR: Archivo no Encontrado");
        }
    }

    private static void ejecutarHelp() {
        System.out.println(
                """
                        pwd: Muestra cuál es la carpeta actual
                        cd <DIR>: Cambia la carpeta actual a ‘DIR’. Con .. cambia a la carpeta superior
                        ls: Muestra la lista de directorios y archivos de la carpeta actual (primero directorios, luego archivos, ambos ordenados alfabéticamente)
                        ll: Igual que ls, pero muestra también el tamaño y la fecha de última modificación
                        mkdir <DIR>: Crea el directorio ‘DIR’ en la carpeta actual
                        rm <FILE>: Borra ‘FILE’. Si es una carpeta, eliminará primero sus archivos y luego la carpeta. Si tiene subcarpetas, las dejará intactas y mostrará una advertencia al usuario
                        mv <FILE1> <FILE2>: Mueve o cambia el nombre de ‘FILE1’ a ‘FILE2’
                        exit: Termina el programa""");
    }

    private static void ejecutarExit() {
        System.out.println("Programa Finalizado");
    }

    private static String formatearFecha(File f) {
        long lastModified = f.lastModified();
        Date d = new Date(lastModified);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formato.format(d);
    }

    private static File getPwd() {
        return new File(System.getProperty("user.dir"));
    }

    private static void printList(File f, boolean info) {
        if (info) {
            for (String s : f.list()) {
                String fecha = formatearFecha(f);
                System.out.printf("%-15s - %4d - %s%n", s, f.length(), fecha);
            }
        } else {
            for (String s : f.list()) {
                System.out.println(s);
            }
        }
    }
}
