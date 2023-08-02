package L3_4.Common;

/**
 * Класс Newton предназначен для работы с полиномом Ньютона.
 */
public class Newton {

    // Массив разностей для формирования полинома Ньютона
    private double[] differences;
    // Полином Ньютона
    private Polynom newton;

    /**
     * Конструктор класса Newton, который строит полином Ньютона по заданной сетке.
     *
     * @param grid объект класса Grid, содержащий сетку точек
     */
    public Newton(Grid grid) {
        int length = grid.getCount();
        differences = new double[length];
        double firstArrOfDif[] = new double[length];
        double secondArrOfDif[] = new double[length];

        // Инициализация массива разностей 0-ого порядка
        for (int i = 0; i < length; i++) {
            firstArrOfDif[i] = grid.getY(i);
        }
        differences[0] = firstArrOfDif[0];

        // Расчет массива разностей n-ого порядка
        for (int i = 1; i < length; i++) {
            for (int j = 0; j < length - i; j++) {
                secondArrOfDif[j] = (firstArrOfDif[j] - firstArrOfDif[j + 1]) / (grid.getX(j) - grid.getX(j + i));
            }
            firstArrOfDif = secondArrOfDif;
            differences[i] = firstArrOfDif[0];
        }

        // Формирование полинома Ньютона
        newton = new Polynom(0, differences[0]);
        Polynom curBin = new Polynom();
        for (int i = 1; i < length; i++) {
            Polynom curPolynom = new Polynom(0, 1);
            for (int j = 0; j < i; j++) {
                curBin.changeBin(-grid.getX(j));
                curPolynom = curPolynom.multiple(curBin);
            }
            curPolynom.multipleK(differences[i]);
            newton.sum(curPolynom);
        }
    }

    /**
     * Метод для вычисления значения полинома в заданной точке.
     *
     * @param x точка, в которой нужно вычислить значение
     * @return значение полинома в точке x
     */
    public double findSol(double x) {
        return newton.getPoint(x);
    }

    /**
     * Метод для вывода полинома Ньютона.
     */
    public void print() {
        newton.print();
    }
}
