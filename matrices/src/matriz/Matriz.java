package matriz;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/*
 * Clase que representa una matriz de enteros.
 *
 * Proporciona operaciones básicas: creación, acceso a valores,
 * lectura desde consola o archivo, y guardado/visualización.
 */
class Matriz {

    // Número de filas de la matriz
    private int filas;

    // Número de columnas de la matriz
    private int columnas;

    // Almacenamiento interno de los datos: datos[fila][columna]
    private int[][] datos;

    /*
     * Constructor: crea una matriz de tamaño filas x columnas
     * inicializando el array interno.
     */
    public Matriz(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        datos = new int[filas][columnas];
    }

    /* Se reserva el espacio en la memoria*/
    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

   /* Donde se lee el número */
    public int getValor(int i, int j) {
        return datos[i][j];
    }

  /* Donde se guarda */
    public void setValor(int i, int j, int valor) {
        datos[i][j] = valor;
    }

    /*
     * Rellena la matriz leyendo valores desde la entrada estándar (consola).
     * Se recorre la matriz por filas solicitando cada valor al usuario.
     */
    public void rellenarPorConsola() {
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print("Valor [" + i + "][" + j + "]: ");
                datos[i][j] = sc.nextInt();
            }
        }
    }

    public void rellenarDesdeArchivo(String nombre) throws Exception {
        // Lee valores whitespace-separados desde un archivo de texto.
        // Se asume que el archivo contiene al menos filas*columnas enteros.
        Scanner sc = new Scanner(new File(nombre));

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                datos[i][j] = sc.nextInt();
            }
        }

        sc.close();
    }

    public void guardarEnArchivo(String nombre) throws Exception {
        // Guarda la matriz en un archivo de texto, una fila por línea,
        // separando valores con espacios.
        PrintWriter pw = new PrintWriter(nombre);

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                pw.print(datos[i][j] + " ");
            }
            pw.println();
        }

        pw.close();
    }

    public void mostrar() {
        // Muestra la matriz en consola con formato sencillo.
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(datos[i][j] + " ");
            }
            System.out.println();
        }
    }
}
