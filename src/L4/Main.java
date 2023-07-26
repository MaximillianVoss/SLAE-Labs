package L4;

import L3.Polynomial;

// Главный класс с методом main() и форматированным выводом
class Main {
    public static void main(String[] args) {
        double a = 0; // Начало интервала
        double b = 1; // Конец интервала
        int points = 5; // Количество точек

        // Создать объект класса Grid с узлами сетки и значениями сеточной функции размером points
        Grid grid = new Grid(a, b, points

        );
        // Создать объект класса NewtonPolynomial, используя объект Grid для построения полинома
        NewtonPolynomial newtonPolynomial = new NewtonPolynomial(grid);

        // Вывести полином на экран в экспоненциальном виде с 6 знаками после десятичной точки
        newtonPolynomial.print();

        // Вывести результаты интерполяции в табличной форме
        printResults(newtonPolynomial, grid, a, b, points);
    }

    // Вывод результатов интерполяции в табличной форме
    static void printResults(NewtonPolynomial newtonPolynomial, Grid grid, double a, double b, int points) {
        double step = (b - a) / ((points - 1) * 2);

        //System.out.println("x\t\t y\t\t f(x)\t\t Ln(x)");
        System.out.printf("\t%18s \t%18s \t%18s \t%18s\n","x","y","f(x)","Ln(x)");
        for (double x = a; x <= b; x += step) {
            double y = newtonPolynomial.polynomial.evaluate(x);
            double f = grid.values[closestNodeIndex(grid.nodes, x)];

            //System.out.printf("%.6f\t %.6f\t %.6f\t %.6f\n", x, y, f, Math.log(x));
            System.out.printf("\t%18.6E \t%18.6E \t%18.6E \t%18.6E \n", x, y, f, Math.log(x));
        }
    }

    // Вспомогательная функция для нахождения индекса ближайшего узла сетки к заданной точке
    private static int closestNodeIndex(double[] nodes, double x) {
        int index = 0;
        double minDiff = Math.abs(x - nodes[0]);

        for (int i = 1; i < nodes.length; i++) {
            double diff = Math.abs(x - nodes[i]);
            if (diff < minDiff) {
                minDiff = diff;
                index = i;
            }
        }

        return index;
    }

    // Метод, возвращающий значение для узла сетки
    static double f(double x) {
        // Реализация функции для вычисления значения узла сетки в точке x
        // Здесь наша функция для вычисления
        return Math.sin(x);
    }
}