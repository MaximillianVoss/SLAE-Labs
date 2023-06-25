package L2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Matrix {

    private double[][] array;
    private double epsilon = 0.000001;

    private double[][] lastPermutation;


    public Matrix(String filename) throws FileNotFoundException {
        Init(filename);
    }

    public void Init(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        this.array = new double[rows][columns];
        this.lastPermutation = new double[rows][columns]; // Исправление здесь
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.array[i][j] = scanner.nextDouble();
                this.lastPermutation[i][j] = this.array[i][j]; // Копирование исходной матрицы
            }
        }
        scanner.close();
    }


    public boolean CheckForZeros() {
        for (int i = 0; i < array.length; i++) {
            if (Math.abs(array[i][i]) < epsilon) {
                return true;
            }
        }
        return false;
    }

    public boolean CheckSCC() {
        for (int i = 0; i < array.length; i++) {
            double sum = 0;
            for (int j = 0; j < array[0].length; j++) {
                if (j != i) {
                    sum += Math.abs(array[i][j]);
                }
            }
            if (sum > Math.abs(array[i][i])) {
                return false;
            }
        }
        return true;
    }

    private double[] PerformIteration(double[] previousApproximation) {
        double[] newApproximation = new double[previousApproximation.length];
        for (int i = 0; i < array.length; i++) {
            double sum = 0;
            for (int j = 0; j < array[0].length - 1; j++) {
                if (i != j) {
                    sum += array[i][j] * previousApproximation[j];
                }
            }
            newApproximation[i] = (array[i][array[0].length - 1] - sum) / array[i][i];
        }
        return newApproximation;
    }


    private void swapRows(int row1, int row2) {
        double[] temp = array[row1];
        array[row1] = array[row2];
        array[row2] = temp;

        // Сохранение копии текущей перестановки
        lastPermutation = new double[array.length][];
        for (int i = 0; i < array.length; i++) {
            lastPermutation[i] = array[i].clone();
        }
    }

    public void PrintLastPermutation() {
        if (lastPermutation == null) {
            System.out.println("Перестановки еще не было.");
            return;
        }

        int rowAmount = lastPermutation.length;
        int columnAmount = lastPermutation[0].length;

        for (int i = 0; i < rowAmount; i++) {
            for (int j = 0; j < columnAmount; j++) {
                System.out.printf("%18.6E", lastPermutation[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void SortRows() {
        for (int i = 0; i < array.length; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (Math.abs(array[j][i]) > Math.abs(array[maxIndex][i])) {
                    maxIndex = j;
                }
            }
            swapRows(i, maxIndex);
        }
    }


    public double[] SolveByIterations() {
        SortRows();
        double[] previousApproximation = new double[array.length];
        double[] currentApproximation;
        double norm;
        int maxIteration = 1000;
        int iteration = 0;
        do {
            for (int i = 0; i < array.length; i++) {
                if (Math.abs(array[i][i]) < epsilon) {
                    if (i < array.length - 1) {
                        swapRows(i, i + 1);
                    } else {
                        System.out.println("Метод не сходится");
                        return previousApproximation;
                    }
                }
            }
            currentApproximation = PerformIteration(previousApproximation);
            norm = Norm(currentApproximation, previousApproximation);
            previousApproximation = currentApproximation.clone();
            iteration++;
            if (iteration > maxIteration) {
                System.out.println("Метод не сходится");
                return previousApproximation;
            }
        } while (norm > epsilon);
        System.out.println("Метод сходится после " + iteration + " итераций");
        return currentApproximation;
    }

    public double Norm(double[] approximation1, double[] approximation2) {
        double maxDiff = Math.abs(approximation1[0] - approximation2[0]);
        for (int i = 1; i < approximation1.length; i++) {
            double diff = Math.abs(approximation1[i] - approximation2[i]);
            if (diff > maxDiff) {
                maxDiff = diff;
            }
        }
        return maxDiff;
    }

    public double[] SolveWithoutControl() {
        return SolveByIterations();
    }

    public double[] SolveWithControl() {
        if (!CheckSCC()) {
            System.out.println("Достаточное условие сходимости не выполняется.");

            return new double[array.length];
        }
        System.out.println("Достаточное условие сходимости выполняется.");
        return SolveByIterations();
    }

    public void Print() {
        int rowAmount = array.length;
        int columnAmount = array[0].length;

        for (int i = 0; i < rowAmount; i++) {
            for (int j = 0; j < columnAmount; j++) {
                System.out.printf("%18.6E", array[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
