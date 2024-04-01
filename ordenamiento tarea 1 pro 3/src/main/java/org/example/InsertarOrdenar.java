package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class InsertarOrdenar {

    /**en esta clase estoy haciendo el metodo que lea lor archivo que le dare para que
     * despues pueda ordenarlos.
     */

    public static String leerContenidoArchivo(File archivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(archivo));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();
        return content.toString();
    }
}