package L1.WithComments;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Matrix {

    private double[][] matrixArray; // Массив для хранения матрицы
    private int rowAmount; // Количество строк матрицы
    private int columnAmount; // Количество столбцов матрицы
    private double epsilon; // Погрешность
    private static final int NotFounded = -1; // Константа для обозначения отсутствия значения
    private double[] solution; // Массив для хранения решений системы

    public Matrix(String fileName) throws FileNotFoundException {
        this.Init(fileName); // Инициализация объекта матрицы
    }

    public void Print() {
        for (int i = 0; i < rowAmount; i++) {
            for (int j = 0; j < columnAmount; j++) {
                System.out.printf("%18.6E", matrixArray[i][j]); // Вывод элементов матрицы
            }
            System.out.println();
        }
        System.out.println();
    }

    public void Init(String s) throws FileNotFoundException {
        File file = new File(s); // Создание объекта файла
        Scanner scan = new Scanner(file); // Создание объекта сканера для чтения файла
        Pattern pat = Pattern.compile("[ \t]"); // Паттерн для разделения строк на элементы
        String str = scan.nextLine(); // Чтение строки из файла
        String[] sn = pat.split(str); // Разделение строки на элементы
        rowAmount = Integer.parseInt(sn[0]); // Чтение количества строк из первого элемента
        columnAmount = Integer.parseInt(sn[1]); // Чтение количества столбцов из второго элемента
        epsilon = Math.pow(10, -Double.parseDouble(sn[2]) - 1); // Чтение значения погрешности из третьего элемента
        this.Create(rowAmount, columnAmount); // Создание матрицы с заданным количеством строк и столбцов
        int i, j;
        for (i = 0; i < rowAmount; i++) {
            str = scan.nextLine(); // Чтение строки из файла
            sn = pat.split(str); // Разделение строки на элементы
            for (j = 0; j < columnAmount; j++) {
                matrixArray[i][j] = Double.parseDouble(sn[j]); // Запись элементов матрицы из строки
            }
        }
        scan.close(); // Закрытие сканера
    }

    public boolean IsAllZeroRow(int row) {
        for (int i = 0; i < columnAmount; i++) {
            if (Math.abs(matrixArray[row][i]) > this.epsilon) {
                return false; // Проверка, является ли строка нулевой
            }
        }
        return true;
    }


    public Results MakeTriangle() {
        for (int iteration = 0; iteration < rowAmount; iteration++) {
            boolean swapResult = SwapFirstNotZeroLine(iteration); // Обмен текущей строки с первой строкой, в которой не нулевой элемент

            if (!swapResult) {
                return Results.DEGENERATE_SYSTEM; // Система вырожденная
            }

            if (Math.abs(matrixArray[iteration][iteration]) < this.epsilon) {
                if (iteration == rowAmount - 1 && IsZeroElement(matrixArray[iteration][columnAmount - 1])) {
                    return Results.INFINITE_SOLUTIONS; // Система имеет бесконечное количество решений
                } else {
                    return Results.DEGENERATE_SYSTEM; // Система вырожденная
                }
            }

            RecalculateCoefficients(iteration, iteration); // Пересчет коэффициентов для приведения матрицы к треугольному виду
        }

        // Проверка на наличие 0 на диагонали после приведения к треугольному виду
        for (int i = 0; i < rowAmount; i++) {
            if (IsZeroElement(matrixArray[i][i])) {
                if (IsZeroElement(matrixArray[i][columnAmount - 1])) {
                    return Results.INFINITE_SOLUTIONS; // Система имеет бесконечное количество решений
                } else {
                    return Results.NO_SOLUTIONS; // Система не имеет решений
                }
            }
        }

        return Results.SINGLE_SOLUTION; // Система имеет единственное решение
    }


    private void RecalculateCoefficients(int row, int iteration) {
        double diagElement = matrixArray[iteration][iteration]; // Диагональный элемент
        for (int j = iteration; j < columnAmount; j++) {
            matrixArray[iteration][j] /= diagElement; // Деление элементов строки на диагональный элемент
        }
        for (int i = iteration + 1; i < rowAmount; i++) {
            double upperRowCoeff = matrixArray[i][iteration]; // Коэффициент строки выше текущей строки
            for (int j = iteration; j < columnAmount; j++) {
                matrixArray[i][j] -= matrixArray[iteration][j] * upperRowCoeff; // Вычитание из элементов строки текущей строки, умноженных на коэффициент
            }
        }
    }

    private boolean SwapFirstNotZeroLine(int lineNumber) {
        if (IsZeroElement(matrixArray[lineNumber][lineNumber])) {
            int notZeroLine = FindLineWithNotZeroElement(lineNumber); // Поиск строки, в которой не нулевой элемент
            if (notZeroLine == NotFounded) {
                return false;
            }
            SwapLines(lineNumber, notZeroLine); // Обмен текущей строки с найденной строкой
        }
        return true;
    }

    public double[] CheckSolutions() {
        Results result = MakeTriangle(); // Приведение матрицы к треугольному виду и определение результата
        switch (result) {
            case SINGLE_SOLUTION:
                solution = FindSolutions(); // Нахождение решений системы
                return solution;
            case DEGENERATE_SYSTEM:
                System.out.println("Система вырожденная"); // Вывод сообщения о вырожденной системе
                return new double[0];
            case INFINITE_SOLUTIONS:
                System.out.println("Система имеет бесконечное количество решений"); // Вывод сообщения о бесконечном количестве решений
                return new double[0];
            case NO_SOLUTIONS:
                System.out.println("Система не имеет решений"); // Вывод сообщения о системе без решений
                return new double[0];
            default:
                return new double[0];
        }

    }

    public double[] FindSolutions() {
        solution = new double[rowAmount]; // Массив для хранения решений
        for (int i = rowAmount - 1; i >= 0; i--) {
            double sol = GetSolution(i); // Нахождение решения для каждой строки
            solution[i] = sol;
        }
        return solution;
    }

    private void Create(int rowAmount, int columnAmount) {
        matrixArray = new double[rowAmount][columnAmount]; // Создание матрицы с заданным количеством строк и столбцов
        for (int i = 0; i < rowAmount; i++) {
            matrixArray[i] = new double[columnAmount]; // Создание массива для каждой строки матрицы
        }
    }

    private int FindLineWithNotZeroElement(int lineNumber) {
        for (int i = lineNumber; i < rowAmount; i++) {
            if (!IsZeroElement(matrixArray[i][lineNumber])) {
                return i; // Поиск строки, в которой не нулевой элемент
            }
        }
        return NotFounded;
    }

    private void SwapLines(int firstLine, int secondLine) {
        double[] temp = matrixArray[secondLine]; // Временный массив для обмена строк
        matrixArray[secondLine] = matrixArray[firstLine]; // Обмен строк
        matrixArray[firstLine] = temp;
    }

    private boolean IsZeroElement(double element) {
        return Math.abs(element) < epsilon; // Проверка, является ли элемент нулевым
    }

    private double GetSolution(int i) {
        double sum = 0;
        for (int j = i + 1; j < columnAmount - 1; j++) {
            sum += solution[j] * matrixArray[i][j]; // Суммирование произведений элементов строки на соответствующие решения
        }
        return (matrixArray[i][columnAmount - 1] - sum) / matrixArray[i][i]; // Нахождение значения решения
    }

    public int getRowAmount() {
        return this.rowAmount; // Получение количества строк
    }

    public int getColumnAmount() {
        return this.columnAmount; // Получение количества столбцов
    }

    public double[][] getMatrixArray() {
        return this.matrixArray; // Получение массива матрицы
    }
}
