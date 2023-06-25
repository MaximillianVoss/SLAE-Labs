package L1.WithComments;

import java.io.File;  // Импорт класса File из пакета java.io
import java.io.FileNotFoundException;  // Импорт класса FileNotFoundException из пакета java.io
import java.util.Scanner;  // Импорт класса Scanner из пакета java.util
import java.util.regex.Pattern;  // Импорт класса Pattern из пакета java.util.regex

public class Matrix {  // Объявление класса Matrix

    private double[][] matrixArray;  // Приватное поле для хранения элементов матрицы
    private int rowAmount;  // Приватное поле для хранения количества строк в матрице
    private int columnAmount;  // Приватное поле для хранения количества столбцов в матрице
    private double epsilon;  // Приватное поле для хранения погрешности для проверки на равенство нулю
    private static final int NotFounded = -1;  // Приватная константа для обозначения не найденного значения индекса
    private double[] solution;  // Приватное поле для хранения решений системы уравнений

    public Matrix(String fileName) throws FileNotFoundException {  // Конструктор класса, принимающий имя файла
        this.Init(fileName);  // Вызов метода Init для инициализации матрицы из файла
    }

    public void Print() {  // Метод для вывода матрицы на экран
        for (int i = 0; i < rowAmount; i++) {
            for (int j = 0; j < columnAmount; j++) {
                System.out.printf("%18.6E", matrixArray[i][j]);  // Вывод элементов матрицы с двумя знаками после запятой
            }
            System.out.println();  // Переход на новую строку
        }
        System.out.println();  // Вывод пустой строки для отделения матрицы от остального вывода
    }

    public void Init(String s) throws FileNotFoundException {  // Метод для инициализации матрицы из файла
        File file = new File(s);  // Создание объекта File с указанным именем файла
        Scanner scan = new Scanner(file);  // Создание объекта Scanner для чтения файла
        Pattern pat = Pattern.compile("[ \t]+");  // Создание объекта Pattern для разделения строки по пробельным символам
        String str = scan.nextLine();  // Чтение первой строки из файла
        String[] sn = pat.split(str);  // Разделение строки на массив строк по пробельным символам
        rowAmount = Integer.parseInt(sn[0]);  // Преобразование первой строки в целое число и присвоение rowAmount
        columnAmount = Integer.parseInt(sn[1]);  // Преобразование второй строки в целое число и присвоение columnAmount
        epsilon = Math.pow(10, -Double.parseDouble(sn[2]) - 1);  // Преобразование третьей строки в число с плавающей точкой и вычисление epsilon
        this.Create(rowAmount, columnAmount);  // Вызов метода Create для создания матрицы с указанным размером
        int i, j;
        for (i = 0; i < rowAmount; i++) {  // Цикл по строкам матрицы
            str = scan.nextLine();  // Чтение следующей строки из файла
            sn = pat.split(str);  // Разделение строки на массив строк по пробельным символам
            for (j = 0; j < columnAmount; j++) {  // Цикл по столбцам матрицы
                matrixArray[i][j] = Double.parseDouble(sn[j]);  // Преобразование элементов строки в числа с плавающей точкой и запись их в матрицу
            }
        }
        scan.close();  // Закрытие объекта Scanner после чтения файла
    }

    private boolean IsAllZeroRow(int lineNumber) {  // Приватный метод для проверки, является ли строка матрицы нулевой
        for (int i = 0; i < columnAmount; i++) {  // Цикл по столбцам строки
            if (!IsZeroElement(matrixArray[lineNumber][i])) {  // Проверка, является ли элемент ненулевым
                return false;  // Если найден ненулевой элемент, возвращается false
            }
        }
        return true;  // Если все элементы строки нулевые, возвращается true
    }

    // Определение метода приведения системы уравнений к треугольному виду.
    public Results MakeTriangle() {
        // Запуск основного цикла по строкам матрицы, начиная с нулевой строки.
        for (int iteration = 0; iteration < rowAmount; iteration++) {
            // Проверка, если итерация больше или равна количеству строк, то остановка выполнения цикла.
            if (iteration >= rowAmount) {
                break;
            }

            // Вызов функции SwapFirstNotZeroLine, которая пытается переставить текущую строку так,
            // чтобы на главной диагонали был ненулевой элемент.
            boolean swapResult = SwapFirstNotZeroLine(iteration);
            // Если перестановка не удалась, то проводятся дополнительные проверки.
            if (!swapResult) {
                // Если текущая строка полностью состоит из нулей,
                if (IsAllZeroRow(iteration)) {
                    // и если последний элемент в этой строке также равен нулю,
                    if (IsZeroElement(matrixArray[iteration][columnAmount - 1])) {
                        // то система имеет бесконечное количество решений.
                        return Results.INFINITE_SOLUTIONS;
                    } else {
                        // Если же последний элемент в строке не равен нулю, то решений нет.
                        return Results.NO_SOLUTIONS;
                    }
                } else {
                    // Если же перестановка не удалась, но строка не состоит полностью из нулей,
                    // то это указывает на то, что система вырожденная.
                    return Results.DEGENERATE_SYSTEM;
                }
            }

            // Проверка абсолютного значения элемента на диагонали с заданной точностью.
            if (Math.abs(matrixArray[iteration][iteration]) < this.epsilon) {
                // Если оно меньше заданной точности, то считается, что решение найдено.
                return Results.SINGLE_SOLUTION;
            }

            // Пересчет коэффициентов после возможной перестановки строк.
            RecalculateCoefficients(iteration, iteration);

            // Если мы на последней итерации и последний диагональный элемент равен нулю,
            if (iteration == rowAmount - 1 && IsZeroElement(matrixArray[iteration][iteration])) {
                // то дополнительно проверяем последний элемент в строке.
                if (IsZeroElement(matrixArray[iteration][columnAmount - 1])) {
                    // Если он равен нулю, то система имеет бесконечное количество решений.
                    return Results.INFINITE_SOLUTIONS;
                } else {
                    // Если он не равен нулю, то решений нет.
                    return Results.NO_SOLUTIONS;
                }
            }
        }
        // Если процесс выполнен для всех строк, и ни одно из условий преждевременного выхода не выполнено,
        // то система имеет единственное решение.
        return Results.SINGLE_SOLUTION;
    }

    private void RecalculateCoefficients(int currentRow, int nextRow) {  // Приватный метод для пересчета коэффициентов после перестановки строк
        for (int i = currentRow + 1; i < rowAmount; i++) {  // Цикл по строкам, начиная со следующей строки после текущей
            double multiplier = matrixArray[i][nextRow] / matrixArray[nextRow][nextRow];  // Вычисление множителя для приведения к нулю элементов под диагональю
            for (int j = nextRow; j < columnAmount; j++) {  // Цикл по столбцам, начиная с текущего столбца
                matrixArray[i][j] -= multiplier * matrixArray[nextRow][j];  // Вычитание произведения множителя и элемента текущей строки из соответствующего элемента следующей строки
            }
            // Проверка, является ли строка теперь нулевой, но правая часть уравнения не нулевая
            if (i != rowAmount - 1 && IsZeroElement(matrixArray[i][i]) && !IsZeroElement(matrixArray[i][columnAmount - 1])) {
                throw new IllegalStateException("Система не имеет решений");  // Генерация исключения, если система не имеет решений
            }
        }
    }

    private boolean SwapFirstNotZeroLine(int lineNumber) {  // Приватный метод для перестановки строки, чтобы первый ненулевой элемент оказался на диагонали
        if (IsZeroElement(matrixArray[lineNumber][lineNumber])) {  // Проверка, является ли элемент на диагонали нулевым
            int notZeroLine = FindLineWithNotZeroElement(lineNumber);  // Поиск строки с первым ненулевым элементом в столбце
            if (notZeroLine == NotFounded) {  // Если строка не найдена
                return false;  // Возвращается false
            }
            SwapLines(lineNumber, notZeroLine);  // Перестановка строк
        }
        return true;  // Возвращается true
    }

    public double[] CheckSolutions() {  // Метод для проверки решений системы уравнений
        Results result = MakeTriangle();  // Вызов метода MakeTriangle для приведения матрицы к треугольному виду
        switch (result) {  // Оператор выбора в зависимости от результата приведения матрицы к треугольному виду
            case SINGLE_SOLUTION:
                solution = FindSolutions();  // Вызов метода FindSolutions для нахождения решений
                return solution;  // Возвращается массив решений
            case DEGENERATE_SYSTEM:
                System.out.println("Система вырожденная");  // Вывод сообщения о вырожденной системе
                return new double[0];  // Возвращается пустой массив
            case INFINITE_SOLUTIONS:
                System.out.println("Система имеет бесконечное количество решений");  // Вывод сообщения о бесконечном количестве решений
                return new double[0];  // Возвращается пустой массив
            case NO_SOLUTIONS:
                System.out.println("Система не имеет решений");  // Вывод сообщения о отсутствии решений
                return new double[0];  // Возвращается пустой массив
            default:
                return new double[0];  // Возвращается пустой массив
        }
    }

    public double[] FindSolutions() {  // Метод для нахождения решений системы уравнений
        solution = new double[rowAmount];  // Создание массива для хранения решений
        for (int i = rowAmount - 1; i >= 0; i--) {  // Цикл по строкам матрицы в обратном порядке
            double sol = GetSolution(i);  // Вычисление решения для текущей строки
            solution[i] = sol;  // Присвоение решения соответствующему элементу массива
        }
        return solution;  // Возвращается массив решений
    }

    private void Create(int rowAmount, int columnAmount) {  // Приватный метод для создания матрицы с указанным размером
        matrixArray = new double[rowAmount][columnAmount];  // Создание двумерного массива для хранения элементов матрицы
        for (int i = 0; i < rowAmount; i++) {  // Цикл по строкам матрицы
            matrixArray[i] = new double[columnAmount];  // Создание массива для хранения элементов строки
        }
    }

    private int FindLineWithNotZeroElement(int lineNumber) {  // Приватный метод для поиска строки с первым ненулевым элементом в столбце
        for (int i = lineNumber; i < rowAmount; i++) {  // Цикл по строкам, начиная с текущей строки
            if (!IsZeroElement(matrixArray[i][lineNumber])) {  // Проверка, является ли элемент ненулевым
                return i;  // Возвращается номер строки
            }
        }
        return NotFounded;  // Если строка не найдена, возвращается значение NotFounded
    }

    private void SwapLines(int firstLine, int secondLine) {  // Приватный метод для перестановки двух строк матрицы
        double[] temp = matrixArray[secondLine];  // Временное хранение второй строки
        matrixArray[secondLine] = matrixArray[firstLine];  // Копирование первой строки во вторую
        matrixArray[firstLine] = temp;  // Копирование временно сохраненной строки в первую
    }

    private boolean IsZeroElement(double element) {  // Приватный метод для проверки, является ли элемент нулевым
        return Math.abs(element) < epsilon;  // Возвращается true, если элемент по модулю меньше погрешности epsilon
    }

    private double GetSolution(int i) {  // Приватный метод для вычисления решения уравнения для заданной строки
        double sum = 0;  // Инициализация суммы
        for (int j = i + 1; j < columnAmount - 1; j++) {  // Цикл по столбцам, начиная со следующего столбца
            sum += solution[j] * matrixArray[i][j];  // Прибавление произведения решения и элемента матрицы к сумме
        }
        return (matrixArray[i][columnAmount - 1] - sum) / matrixArray[i][i];  // Возвращается вычисленное решение
    }

    public int getRowAmount() {  // Геттер для получения количества строк в матрице
        return this.rowAmount;  // Возвращается значение поля rowAmount
    }

    public int getColumnAmount() {  // Геттер для получения количества столбцов в матрице
        return this.columnAmount;  // Возвращается значение поля columnAmount
    }

    public double[][] getMatrixArray() {  // Геттер для получения двумерного массива элементов матрицы
        return this.matrixArray;  // Возвращается ссылка на двумерный массив matrixArray
    }
}
