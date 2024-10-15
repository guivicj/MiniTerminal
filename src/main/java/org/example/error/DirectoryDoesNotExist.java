package org.example.error;

public class DirectoryDoesNotExist extends Exception {
    public DirectoryDoesNotExist() {
        System.out.println("El directorio no existe");
    }
}
