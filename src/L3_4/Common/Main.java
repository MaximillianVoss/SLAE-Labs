package L3_4.Common;

/**
 * Основной класс приложения.
 */
public class Main {

    /**
     * Основной метод приложения.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {

        // Начало интервала.
        double a = 0;
        // Конец интервала.
        double b = 1;
        // Количество точек в сетке.
        int n = 7;
        // Размер шага.
        double step = (b - a) / (n - 1);

        // Создаем сетку из n точек.
        Grid grid = new Grid(n);

        // Заполняем сетку точками.
        for (double i = a; i <= b; i += step) {
            grid.addPoint(i, function(i));
        }

        // Создаем полином Лагранжа.
        Lagrange lagrange = new Lagrange(grid);
        System.out.println("Полином в форме Лагранжа:");
        // Печатаем полином Лагранжа.
        lagrange.print();
        System.out.println();

        // Создаем полином Ньютона.
        Newton newton = new Newton(grid);
        System.out.println("Полином в форме Ньютона:");
        // Печатаем полином Ньютона.
        newton.print();
        System.out.println();

        // Печатаем таблицу с x, y, f(x) и полиномом в точке x.
        printTable(grid, a, b, step / 2, lagrange, newton);
    }

    /**
     * Выводит таблицу x, y, f(x), и полином в точке x.
     *
     * @param grid     сетка точек
     * @param a        начало интервала
     * @param b        конец интервала
     * @param step     размер шага
     * @param lagrange полином Лагранжа
     * @param newton   полином Ньютона
     */
    public static void printTable(Grid grid, double a, double b, double step, Lagrange lagrange, Newton newton) {
        System.out.printf("%20s %20s %20s %20s %20s", "X", "Y", "f(x)", "Ln(x)", "Nn(x)");
        System.out.println();

        // Печатаем каждую точку и соответствующие значения функции и полинома.
        for (double i = a; i <= b; i += step) {
            int index = grid.find(i);
            if (index >= 0) {
                System.out.printf("%20.6E %20.6E %20.6E %20.6E %20.6E", i, grid.getY(index), function(i), lagrange.findSol(i), newton.findSol(i));
            } else {
                System.out.format("%20.6E %20s %20.6E %20.6E %20.6E", i, "", function(i), lagrange.findSol(i), newton.findSol(i));
            }
            System.out.println();
        }
    }

    /**
     * Вычисляет значение функции в точке x.
     *
     * @param x входное значение
     * @return значение функции в точке x
     */
    public static double function(double x) {
        return Math.sin(x * x / 2);
    }
}
