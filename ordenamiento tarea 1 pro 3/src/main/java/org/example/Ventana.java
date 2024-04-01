package org.example;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import java.util.logging.Logger;



import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Ventana extends JFrame {
    /*estoy implementando los botones y paneles*/
    private static final Logger logger = Logger.getLogger(Ventana.class.getName());
    private JMenuBar menuBar;
    private JButton botonOrdenar;
    private JPanel panelA, panelE, panelI, panelO, panelU;
    private String contenidoArchivo; // Variable para almacenar el contenido del archivo

    /*estoy ponieno un constructor*/
    public Ventana() {
        /*configurando la ventana*/
        setSize(1200, 800);
        setResizable(false);
        /**estoy usando un layout nulo para manejar manualmente la
         * ubicaciones (aun nose como ponerlos automaticamente)*/
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        /*iniciando el logger por que sino mi licen me saca 0*/
        inicializarLogger();
        /*creando los otros componenetes*/
        crearMenuBar();
        crearBotonOrdenar();
        crearPanelesVocales();
    }

    /*metodo para iniciar el logger por que sino el licen me saca 0*/

    private void inicializarLogger() {
        try {
            FileHandler fileHandler = new FileHandler("logfile.log");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            e.printStackTrace();


        }
    }

    /*metodo para crear la barra de menú*/
    private void crearMenuBar() {
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        /*menú "archivo"*/
        JMenu menuArchivo = new JMenu("Archivo");
        menuBar.add(menuArchivo);



        /*Elemento de menú "Leer"*/
        JMenuItem menuItemLeer = new JMenuItem("Leer");
        menuArchivo.add(menuItemLeer);
        menuItemLeer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Manejo de la acción al hacer clic en "Leer"*/
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccionar archivo");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt");
                fileChooser.addChoosableFileFilter(filter);

                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        // Leer el contenido del archivo y almacenarlo en la variable
                        contenidoArchivo = leerContenidoArchivo(selectedFile);
                        logger.info("Archivo leído correctamente: " + selectedFile.getName());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        logger.severe("Error al leer el archivo: " + ex.getMessage());

                    }
                }
            }
        });

        /*implmementando la funcion del menu exit*/
        JMenuItem menuItemExit = new JMenuItem("Exit");
        menuArchivo.add(menuItemExit);
        menuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); /*aqui el boton cierra la ventana*/
            }
        });
    }

    private void crearBotonOrdenar() {
        botonOrdenar = new JButton("Ordenar");
        botonOrdenar.setSize(300, 100);
        botonOrdenar.setLocation(450, 150);
        botonOrdenar.setForeground(Color.black);
        botonOrdenar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llama al método para ordenar y poblar los paneles con el contenido del archivo
                ordenarYPoblarPaneles();
            }
        });
        add(botonOrdenar);
    }

    private void crearPanelesVocales() {
        panelA = crearPanelVocal();
        panelE = crearPanelVocal();
        panelI = crearPanelVocal();
        panelO = crearPanelVocal();
        panelU = crearPanelVocal();

        JScrollPane scrollPaneA = new JScrollPane(panelA);
        JScrollPane scrollPaneE = new JScrollPane(panelE);
        JScrollPane scrollPaneI = new JScrollPane(panelI);
        JScrollPane scrollPaneO = new JScrollPane(panelO);
        JScrollPane scrollPaneU = new JScrollPane(panelU);

        // Configuración para desplazamiento vertical
        scrollPaneA.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneE.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneI.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneO.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneU.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Ubicación de los JScrollPane
        scrollPaneA.setBounds(100, 350, 100, 200);
        scrollPaneE.setBounds(300, 350, 100, 200);
        scrollPaneI.setBounds(500, 350, 100, 200);
        scrollPaneO.setBounds(700, 350, 100, 200);
        scrollPaneU.setBounds(900, 350, 100, 200);

        add(scrollPaneA);
        add(scrollPaneE);
        add(scrollPaneI);
        add(scrollPaneO);
        add(scrollPaneU);
    }

    private JPanel crearPanelVocal() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Cambiar disposición a vertical
        panel.setBackground(Color.white);
        return panel;
    }
    private String leerContenidoArchivo(File archivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(archivo));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();
        return content.toString();
    }

    /**
     * Lee el contenido del archivo que se subio ordena las palabras por la vocal que inicia
     * que es la a obviamente. para que muestre en cada uno
     * de su respectivos paneles
     *
     *
     * */

    private void ordenarYPoblarPaneles() {
        if (contenidoArchivo != null && !contenidoArchivo.isEmpty()) {
            // Limpiar los paneles
            panelA.removeAll();
            panelE.removeAll();
            panelI.removeAll();
            panelO.removeAll();
            panelU.removeAll();

            // Dividir las palabras por espacios en blanco
            String[] palabras = contenidoArchivo.split("\\s+");

            // Ordenar las palabras y colocarlas en los paneles correspondientes
            for (String palabra : palabras) {
                char primeraLetra = palabra.charAt(0);
                JPanel panel;
                switch (Character.toUpperCase(primeraLetra)) {
                    case 'A':
                        panel = panelA;
                        break;
                    case 'E':
                        panel = panelE;
                        break;
                    case 'I':
                        panel = panelI;
                        break;
                    case 'O':
                        panel = panelO;
                        break;
                    case 'U':
                        panel = panelU;
                        break;
                    default:
                        continue; // Saltar palabras que no comienzan con vocales
                }
                JLabel etiquetaPalabra = new JLabel(palabra);
                etiquetaPalabra.setSize(100, 20);
                etiquetaPalabra.setHorizontalAlignment(SwingConstants.CENTER);
                panel.add(etiquetaPalabra);
            }

            // Actualizar los paneles
            panelA.revalidate();
            panelE.revalidate();
            panelI.revalidate();
            panelO.revalidate();
            panelU.revalidate();

            panelA.repaint();
            panelE.repaint();
            panelI.repaint();
            panelO.repaint();
            panelU.repaint();
        }
    }
}
