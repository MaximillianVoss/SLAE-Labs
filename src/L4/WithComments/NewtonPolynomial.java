package L4.WithComments;


import L4.Grid;
import L4.Polynomial;

// Класс для интерполяционного полинома в форме Ньютона
class NewtonPolynomial {
    Polynomial polynomial; // Объект класса Polynomial для хранения полинома
    double[] dividedDifferences; // Массив разделенных разностей

    // Конструктор, принимающий grid - объект класса Grid с узлами сетки и значениями сеточной функции
    NewtonPolynomial(Grid grid) {
        polynomial = new Polynomial();
        dividedDifferences = new double[grid.nodes.length];

        calculateDividedDifferences(grid);
        calculatePolynomial(grid);
    }

    // Вычисление разделенных разностей
    private void calculateDividedDifferences(Grid grid) {
        for (int i = 0; i < grid.nodes.length; i++) {
            dividedDifferences[i] = grid.values[i];
        }

        // Вычисление разделенных разностей с использованием алгоритма Ньютона
        for (int j = 1; j < grid.nodes.length; j++) {
            for (int i = grid.nodes.length - 1; i >= j; i--) {
                dividedDifferences[i] = (dividedDifferences[i] - dividedDifferences[i - 1]) / (grid.nodes[i] - grid.nodes[i - j]);
            }
        }
    }

    // Вычисление полинома Ньютона
    private void calculatePolynomial(Grid grid) {
        for (int i = 0; i < grid.nodes.length; i++) {
            Polynomial currentPolynomial = new Polynomial();
            currentPolynomial.terms.add(currentPolynomial.new PolynomialTerm(dividedDifferences[i], 0));

            // Построение многочлена Ньютона с использованием разделенных разностей
            for (int j = 0; j < i; j++) {
                Polynomial termPolynomial = new Polynomial();
                termPolynomial.terms.add(termPolynomial.new PolynomialTerm(1, 1));
                termPolynomial.terms.add(termPolynomial.new PolynomialTerm(-grid.nodes[j], 0));

                currentPolynomial = currentPolynomial.multiply(termPolynomial);
            }

            polynomial = polynomial.add(currentPolynomial);
        }
    }

    // Вывод полинома на экран
    public void print() {
        polynomial.print();
    }
}
