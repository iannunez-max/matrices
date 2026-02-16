package matriz;

/**
 * Hilo encargado de calcular un único elemento (fila,columna)
 * del producto de dos matrices. Cada hilo calcula el producto
 * escalar entre la fila de la primera matriz y la columna de la
 * segunda matriz y almacena el resultado localmente.
 */
class HiloMultiplicacion extends Thread {

    // Referencias a las matrices implicadas en la multiplicación
    private Matriz m1;
    private Matriz m2;

    // Índices de la posición que este hilo debe calcular
    private int fila;
    private int columna;

    // Resultado calculado por el hilo (elemento [fila][columna])
    private int resultado;

    /**
     * Construye el hilo indicando las dos matrices y la posición a calcular.
     * @param m1 Primera matriz (provee la fila)
     * @param m2 Segunda matriz (provee la columna)
     * @param fila Índice de fila en m1
     * @param columna Índice de columna en m2
     */
    public HiloMultiplicacion(Matriz m1, Matriz m2, int fila, int columna) {
        this.m1 = m1;
        this.m2 = m2;
        this.fila = fila;
        this.columna = columna;
    }

    /**
     * Ejecutado cuando se llama a `start()`. Implementa el cálculo del
     * producto escalar: suma de m1[fila][k] * m2[k][columna] para k en
     * el rango de columnas de m1 (o filas de m2).
     */
    public void run() {

        int suma = 0;

        for (int i = 0; i < m1.getColumnas(); i++) {
            suma += m1.getValor(fila, i) * m2.getValor(i, columna);
        }

        // Guardamos el resultado calculado para su lectura posterior
        resultado = suma;
    }

    /**
     * Devuelve el resultado calculado por este hilo. Debe llamarse
     * después de que el hilo haya terminado (`join()`).
     */
    public int getResultado() {
        return resultado;
    }
}
