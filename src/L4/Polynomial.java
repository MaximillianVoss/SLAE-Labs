package L4;

import java.util.LinkedList;

// Класс для хранения полинома
public class Polynomial {

    // Связный список элементов полинома
    public LinkedList<PolynomialTerm> terms;

    // Конструктор класса Polynomial
    public Polynomial() {
        terms = new LinkedList<>();
    }

    // Внутренний класс для хранения одного элемента полинома
    public class PolynomialTerm {
        public double coefficient; // Коэффициент элемента полинома
        public int degree; // Неотрицательная степень элемента полинома

        // Конструктор класса PolynomialTerm
        public PolynomialTerm(double coefficient, int degree) {
            this.coefficient = coefficient;
            this.degree = degree;
        }
    }

    // Сложение полиномов
    public Polynomial add(Polynomial other) {
        Polynomial result = new Polynomial();

        for (PolynomialTerm term : terms) {
            boolean foundMatchingDegree = false;
            for (PolynomialTerm otherTerm : other.terms) {
                if (term.degree == otherTerm.degree) {
                    double sum = term.coefficient + otherTerm.coefficient;
                    if (sum != 0) {
                        result.terms.add(new PolynomialTerm(sum, term.degree));
                    }
                    foundMatchingDegree = true;
                    break;
                }
            }
            if (!foundMatchingDegree) {
                result.terms.add(new PolynomialTerm(term.coefficient, term.degree));
            }
        }

        for (PolynomialTerm otherTerm : other.terms) {
            boolean foundMatchingDegree = false;
            for (PolynomialTerm term : terms) {
                if (otherTerm.degree == term.degree) {
                    foundMatchingDegree = true;
                    break;
                }
            }
            if (!foundMatchingDegree) {
                result.terms.add(new PolynomialTerm(otherTerm.coefficient, otherTerm.degree));
            }
        }

        return result;
    }

    // Умножение полиномов
    public Polynomial multiply(Polynomial other) {
        Polynomial result = new Polynomial();

        for (PolynomialTerm term1 : terms) {
            for (PolynomialTerm term2 : other.terms) {
                double coefficient = term1.coefficient * term2.coefficient;
                int degree = term1.degree + term2.degree;
                result.terms.add(new PolynomialTerm(coefficient, degree));
            }
        }

        result = result.combineTerms();
        return result;
    }

    // Умножение полинома на вещественное число
    public Polynomial multiplyByScalar(double scalar) {
        Polynomial result = new Polynomial();

        for (PolynomialTerm term : terms) {
            double coefficient = term.coefficient * scalar;
            result.terms.add(new PolynomialTerm(coefficient, term.degree));
        }

        return result;
    }

    // Вычисление значения полинома в точке по правилу Горнера
    public double evaluate(double x) {
        double result = 0;

        for (PolynomialTerm term : terms) {
            result = result * x + term.coefficient;
        }

        return result;
    }

    // Вывод полинома на экран
    public void print() {
        for (int i = 0; i < terms.size(); i++) {
            PolynomialTerm term = terms.get(i);

            if (i > 0 && term.coefficient > 0) {
                System.out.print("+ ");
            }

            System.out.print(term.coefficient + "x^" + term.degree + " ");
        }

        System.out.println();
    }

    // Свертывание полинома, объединение элементов с одинаковыми степенями и удаление элементов с нулевыми коэффициентами
    private Polynomial combineTerms() {
        Polynomial result = new Polynomial();

        for (PolynomialTerm term : terms) {
            boolean foundMatchingDegree = false;
            for (PolynomialTerm resultTerm : result.terms) {
                if (term.degree == resultTerm.degree) {
                    resultTerm.coefficient += term.coefficient;
                    foundMatchingDegree = true;
                    break;
                }
            }
            if (!foundMatchingDegree && term.coefficient != 0) {
                result.terms.add(new PolynomialTerm(term.coefficient, term.degree));
            }
        }

        return result;
    }
}



