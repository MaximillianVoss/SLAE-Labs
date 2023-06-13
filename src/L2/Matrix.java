package L2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

// Класс Matrix
public class Matrix {

    // Инициализация переменных
    private double[][] array; // Матрица (массив)
    private double epsilon = 0.000001; // Погрешность для проверки на равенство нулю
    private int[] notNullCombination; //Массив для решения

    // Конструктор класса Matrix
    public Matrix(String filename) throws FileNotFoundException {
        Init(filename);
    }

    // Метод инициализации матрицы из файла
    public void Init(String filename) throws FileNotFoundException {
        // Используем Scanner для чтения файла
        Scanner scanner = new Scanner(new File(filename));
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        this.array = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.array[i][j] = scanner.nextDouble();
            }
        }
        scanner.close();
    }

    // Метод проверки наличия нулей на главной диагонали
    public boolean CheckForZeros() {
        for (int i = 0; i < array.length; i++) {
            if (Math.abs(array[i][i]) < epsilon) {
                return true;
            }
        }
        return false;
    }

    // Метод проверки достаточного условия сходимости
    public boolean CheckSCC() {
        //Проверка достаточного условия сходимости матрицы:
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

    //Метод итерации
    private double[] PerformIteration(double[] previousApproximation) {
        // Инициализация нового приближения
        double[] newApproximation = new double[previousApproximation.length];
        // Перебор строк матрицы
        for (int i = 0; i < array.length; i++) {
            newApproximation[i] = array[i][array[0].length - 1]; // свободный член
            for (int j = 0; j < array[0].length - 1; j++) {
                if (i != j) {
                    newApproximation[i] -= array[i][j] * previousApproximation[j];
                }
            }
            newApproximation[i] /= array[i][i];
        }
        return newApproximation;
    }

    // Метод решения системы итерационным методом
    public double[] SolveByIterations() {
        double[] previousApproximation = new double[array.length];
        double[] currentApproximation;
        do {
            currentApproximation = PerformIteration(previousApproximation);
            previousApproximation = currentApproximation.clone();
        } while (!CheckSCC());
        return currentApproximation;
    }

    // Метод вывода матрицы на экран
    public void Print() {
        for (double[] row : array) {
            System.out.println(Arrays.toString(row));
        }
    }
}
