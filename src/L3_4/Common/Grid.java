package L3_4.Common;

/**
 * Класс представляющий сетку точек на двухмерной плоскости.
 */
public class Grid {

    // Количество точек в сетке
    private int count;

    // Массив координат X и Y точек
    private double[] x;
    private double[] y;

    // Индекс последнего добавленного элемента в сетку
    private int last = -1;

    /**
     * Создает новую сетку заданного размера.
     *
     * @param count количество точек в сетке.
     */
    public Grid(int count) {
        this.count = count;
        x = new double[count];
        y = new double[count];
    }

    /**
     * Возвращает координату X точки по индексу.
     *
     * @param index индекс точки в сетке.
     * @return координата X точки.
     */
    public double getX(int index) {
        return x[index];
    }

    /**
     * Возвращает координату Y точки по индексу.
     *
     * @param index индекс точки в сетке.
     * @return координата Y точки.
     */
    public double getY(int index) {
        return y[index];
    }

    /**
     * Возвращает количество точек в сетке.
     *
     * @return количество точек в сетке.
     */
    public int getCount() {
        return count;
    }

    /**
     * Добавляет новую точку в сетку.
     *
     * @param x координата X новой точки.
     * @param y координата Y новой точки.
     */
    public void addPoint(double x, double y) {
        if (last + 1 < count) {
            last++;
            this.x[last] = x;
            this.y[last] = y;
        }
    }

    /**
     * Ищет индекс точки в сетке по ее координате X.
     *
     * @param x координата X искомой точки.
     * @return индекс искомой точки или -1, если точка не найдена.
     */
    public int find(double x) {
        for (int i = 0; i <= last; i++) {
            if (Math.abs(this.x[i] - x) <= 1e-6) {
                return i;
            }
        }
        return -1;
    }
}
