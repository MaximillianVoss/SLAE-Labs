package L3.WithComments;

import L3.Grid;
import L3.Polynomial;

// Класс для интерполяционного полинома в форме Лагранжа
class LagrangePolynomial {
    Polynomial polynomial; // Объект класса Polynomial для хранения полинома

    // Конструктор, принимающий grid - объект класса Grid с узлами сетки и значениями сеточной функции
    LagrangePolynomial(Grid grid) {
        polynomial = new Polynomial();

        for (int i = 0; i < grid.nodes.length; i++) {
            double currentNode = grid.nodes[i];
            double currentValue = grid.values[i];

            Polynomial currentPolynomial = new Polynomial();
            currentPolynomial.terms.add(new Polynomial.PolynomialTerm(currentValue, 0));

            for (int j = 0; j < grid.nodes.length; j++) {
                if (j == i) {
                    continue;
                }
                double node = grid.nodes[j];

                Polynomial termPolynomial = new Polynomial();
                termPolynomial.terms.add(new Polynomial.PolynomialTerm(1, 1));
                termPolynomial.terms.add(new Polynomial.PolynomialTerm(-node, 0));

                currentPolynomial = currentPolynomial.multiply(termPolynomial);
            }

            currentPolynomial = currentPolynomial.multiplyByScalar(1 / denominator(grid.nodes, i));
            polynomial = polynomial.add(currentPolynomial);
        }
    }

    // Вспомогательная функция для вычисления знаменателя в формуле Лагранжа
    private double denominator(double[] nodes, int index) {
        double result = 1;
        double currentNode = nodes[index];

        for (int i = 0; i < nodes.length; i++) {
            if (i == index) {
                continue;
            }
            double node = nodes[i];
            result *= currentNode - node;
        }

        return result;
    }

    // Вывод полинома на экран
    public void print() {
        polynomial.print();
    }
}
