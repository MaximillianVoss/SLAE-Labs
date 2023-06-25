package L3;

import static L3.Main.f;

// Класс для хранения узлов сетки и значений сеточной функции
class Grid {
    double[] nodes; // Массив узлов сетки
    double[] values; // Массив значений сеточной функции

    // Конструктор класса Grid для выделения памяти
    Grid(double a, double b, int points) {
        nodes = new double[points];
        values = new double[points];

        double step = (b - a) / (points - 1);

        for (int i = 0; i < points; i++) {
            nodes[i] = a + i * step;
            values[i] = f(nodes[i]);
        }
    }
}
