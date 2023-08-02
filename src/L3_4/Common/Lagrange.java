package L3_4.Common;

/**
 * Класс Lagrange предназначен для работы с полиномом Лагранжа.
 */
public class Lagrange {

    // Полином Лагранжа
    private Polynom polynom;

    /**
     * Конструктор класса Lagrange, который строит полином Лагранжа по заданной сетке.
     *
     * @param grid объект класса Grid, содержащий сетку точек
     */
    public Lagrange(Grid grid) {
        // Создаем новый полином
        polynom = new Polynom();

        // Проходим по всей сетке и формируем полином Лагранжа
        for (int i = 0; i < grid.getCount(); i++) {
            // Текущий коэффициент и полином на i-ом шаге
            double temp = grid.getY(i);
            Polynom curPolynom = new Polynom(0, 1);

            // Формируем полином, перемножая текущий полином с биномом
            for (int j = 0; j < i; j++) {
                temp /= (grid.getX(i) - grid.getX(j));
                Polynom curBin = new Polynom();
                curBin.changeBin(-grid.getX(j));
                curPolynom = curPolynom.multiple(curBin);
            }

            for (int j = i + 1; j < grid.getCount(); j++) {
                temp /= (grid.getX(i) - grid.getX(j));
                Polynom curBin = new Polynom();
                curBin.changeBin(-grid.getX(j));
                curPolynom = curPolynom.multiple(curBin);
            }

            // Умножаем текущий полином на коэффициент и добавляем к полиному Лагранжа
            curPolynom.multipleK(temp);
            polynom.sum(curPolynom);
        }
    }

    /**
     * Метод для вычисления значения полинома в заданной точке.
     *
     * @param x точка, в которой нужно вычислить значение
     * @return значение полинома в точке x
     */
    public double findSol(double x) {
        return polynom.getPoint(x);
    }

    /**
     * Метод для вывода полинома Лагранжа.
     */
    public void print() {
        polynom.print();
    }
}
