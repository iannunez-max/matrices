package matriz;

import java.util.Scanner;

/**
 * Clase principal con el método `main` que coordina la lectura de datos,
 * la creación de hilos para multiplicar matrices y el guardado/visualización
 * del resultado.
 */
public class Principal {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int f1, c1, f2, c2;

        // Si se proporcionan 4 argumentos en la línea de comandos se usan
        // como dimensiones: f1 c1 f2 c2, en caso contrario se piden por consola.
        if (args.length == 4) {

            f1 = Integer.parseInt(args[0]);
            c1 = Integer.parseInt(args[1]);
            f2 = Integer.parseInt(args[2]);
            c2 = Integer.parseInt(args[3]);

        } else {

            System.out.println("Introduce filas y columnas de la matriz 1:");
            f1 = sc.nextInt();
            c1 = sc.nextInt();

            System.out.println("Introduce filas y columnas de la matriz 2:");
            f2 = sc.nextInt();
            c2 = sc.nextInt();
        }

        // Validaciones de tamaño (limita a 1..20 para esta práctica)
        if (f1 < 1 || f1 > 20 || c1 < 1 || c1 > 20 ||
            f2 < 1 || f2 > 20 || c2 < 1 || c2 > 20) {

            System.out.println("Los tamaños deben estar entre 1 y 20.");
            return;
        }

        // Comprobación de compatibilidad de multiplicación: columnas de A == filas de B
        if (c1 != f2) {
            System.out.println("No se pueden multiplicar las matrices.");
            return;
        }

        boolean continuar = true;

        while (continuar) {

            // Crear las matrices con las dimensiones indicadas
            Matriz m1 = new Matriz(f1, c1);
            Matriz m2 = new Matriz(f2, c2);
            Matriz resultado = new Matriz(f1, c2);

            System.out.println("1 - Introducir datos por consola");
            System.out.println("2 - Leer datos desde archivo");
            int opcion = sc.nextInt();

            try {

                if (opcion == 1) {

                    // Relleno interactivo por consola
                    System.out.println("Rellenando matriz 1:");
                    m1.rellenarPorConsola();

                    System.out.println("Rellenando matriz 2:");
                    m2.rellenarPorConsola();

                } else {

                    // Lectura desde archivos especificados por el usuario
                    System.out.print("Nombre archivo matriz 1: ");
                    String archivo1 = sc.next();

                    System.out.print("Nombre archivo matriz 2: ");
                    String archivo2 = sc.next();

                    m1.rellenarDesdeArchivo(archivo1);
                    m2.rellenarDesdeArchivo(archivo2);
                }

                // Matriz de hilos: cada hilo calcula un único elemento del resultado
                HiloMultiplicacion[][] hilos = new HiloMultiplicacion[f1][c2];

                // Crear y lanzar hilos: se crea un hilo por cada posición (i,j)
                for (int i = 0; i < f1; i++) {
                    for (int j = 0; j < c2; j++) {
                        hilos[i][j] = new HiloMultiplicacion(m1, m2, i, j);
                        hilos[i][j].start();
                    }
                }

                // Esperar a que terminen y recoger resultados: join() garantiza
                // que el cálculo de cada elemento haya finalizado antes de leerlo.
                for (int i = 0; i < f1; i++) {
                    for (int j = 0; j < c2; j++) {
                        hilos[i][j].join();
                        resultado.setValor(i, j, hilos[i][j].getResultado());
                    }
                }

                System.out.println("Matriz resultado:");
                resultado.mostrar();

                System.out.println("¿Guardar resultado en archivo? (1=Si / 0=No)");
                int guardar = sc.nextInt();

                if (guardar == 1) {
                    System.out.print("Nombre del archivo: ");
                    String nombre = sc.next();
                    resultado.guardarEnArchivo(nombre);
                }

            } catch (Exception e) {
                // Captura genérica de errores: en producción convendría manejar
                // excepciones específicas y mostrar mensajes más detallados.
                System.out.println("Error durante la ejecución.");
            }

            System.out.println("¿Nueva multiplicación? (1=Si / 0=No)");
            int seguir = sc.nextInt();

            if (seguir == 0) {
                continuar = false;
            }
        }

        System.out.println("Programa finalizado.");
        sc.close();
    }
}