package L1;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

// Перечислимый тип для результатов решения системы уравнений
enum Results {
    SINGLE_SOLUTION, // Единственное решение
    DEGENERATE_SYSTEM, // Вырожденная система
    INFINITE_SOLUTIONS, // Бесконечное количество решений
    NO_SOLUTIONS // Нет решений
}


// Класс Matrix
class Matrix {

    // Инициализация переменных
    private double[][] matrixArray; // Матрица (массив)
    private int rowAmount; // Количество строк
    private int columnAmount; // Количество столбцов
    private double epsilon; // Погрешность для проверки на равенство нулю
    private static final int NotFounded = -1; // Индекс, если строка с ненулевым не найдена
    private double[] solution; //Массив для получения решения

    // Метод печати матрицы
    public void Print() {
        // Цикл по строкам матрицы
        for (int i = 0; i < rowAmount; i++) {
            // Цикл по столбцам матрицы
            for (int j = 0; j < columnAmount; j++) {
                // Вывод элемента матрицы в консоль с форматированием
                System.out.printf("%.2f\t", matrixArray[i][j]);
            }
            // Вывод новой строки
            System.out.println();
        }
        // Вывод пустой строки
        System.out.println();
    }

    // Инициализация матрицы из файла
    public void Init(String s) throws FileNotFoundException {
        File file = new File(s);
        Scanner scan = new Scanner(file);
        Pattern pat = Pattern.compile("[ \t]+");
        String str = scan.nextLine();
        String[] sn = pat.split(str);
        rowAmount = Integer.parseInt(sn[0]);
        columnAmount = Integer.parseInt(sn[1]);
        epsilon = Math.pow(10, -Double.parseDouble(sn[2]) - 1);
        this.Create(rowAmount, columnAmount);
        int i, j;
        for (i = 0; i < rowAmount; i++) {
            str = scan.nextLine();
            sn = pat.split(str);
            for (j = 0; j < columnAmount; j++)
                matrixArray[i][j] = Double.parseDouble(sn[j]);
        }
        scan.close();
    }

    // Приведение матрицы к треугольному виду
    public Results MakeTriangle() {
        // Цикл по итерациям (номер текущей итерации)
        for (int iteration = 0; iteration < rowAmount; iteration++) {
            // Если текущая итерация превышает количество строк матрицы, прерываем цикл
            if (iteration >= rowAmount) {
                break;
            }

            // Вызов метода SwapFirstNotZeroLine() для обмена строк с нулевым элементом на диагонали
            boolean swapResult = SwapFirstNotZeroLine(iteration);
            // Если результат обмена строк равен false (не с кем поменять строку, в которой обнаружили 0 на диагонали),
            // возвращаем "DEGENERATE_SYSTEM" - система вырожденная
            if (!swapResult) {
                return Results.DEGENERATE_SYSTEM;
            }

            // Вызов метода RecalculateCoefficients() для пересчета коэффициентов после обнаружения ненулевого элемента
            RecalculateCoefficients(iteration, iteration);

            // Если матрица вырожденная (Дошли до конца, и последняя строка имеет нулевой элемент на диагонали),
            // возвращаем либо "INFINITE_SOLUTIONS" = бесконечно много решений, либо "NO_SOLUTIONS" = решений нет
            if (iteration == rowAmount - 1 && IsZeroElement(matrixArray[iteration][iteration])) {
                if (IsZeroElement(matrixArray[iteration][columnAmount - 1])) {
                    return Results.INFINITE_SOLUTIONS;
                } else {
                    return Results.NO_SOLUTIONS;
                }
            }
        }
        // Если все хорошо, возвращаем "SINGLE_SOLUTION" - решение единственное
        return Results.SINGLE_SOLUTION;
    }

    // Пересчет коэффициентов после обнаружения ненулевого элемента на диагонали
    private void RecalculateCoefficients(int currentRow, int nextRow) {
        // Цикл по столбцам матрицы
        for (int j = nextRow + 1; j < columnAmount; j++) {
            // Вычисление множителя для исключения переменной
            double multiplier = matrixArray[nextRow][j] / matrixArray[nextRow][nextRow];

            // Обновление элементов строки с учетом множителя
            for (int k = nextRow; k < columnAmount; k++) {
                matrixArray[currentRow][k] -= multiplier * matrixArray[nextRow][k];
            }
        }
    }

    // Обмен строк с ненулевым элементом
    private boolean SwapFirstNotZeroLine(int lineNumber) {
        // Если элемент на диагонали равен нулю
        if (IsZeroElement(matrixArray[lineNumber][lineNumber])) {
            // Найти строку с ненулевым элементом на данной позиции
            int notZeroLine = FindLineWithNotZeroElement(lineNumber);

            // Если такой строки нет, вернуть false
            if (notZeroLine == NotFounded) {
                return false;
            }
            // Иначе, обменять строки местами
            SwapLines(lineNumber, notZeroLine);
        }
        // Вернуть true
        return true;
    }


    // Проверка на наличие решений
    public double[] CheckSolutions() {
        // Передаем результат MakeTriangle в переменную result
        Results result = MakeTriangle();
        // Вывод сообщения в зависимости от значения result
        switch (result) {
            case SINGLE_SOLUTION:
                double[] solutions = FindSolutions();
                //PrintResult(solutions);
                return solutions;
            case DEGENERATE_SYSTEM:
                System.out.println("Система вырожденная");
                return null;
            case INFINITE_SOLUTIONS:
                System.out.println("Система имеет бесконечное количество решений");
                return null;
            case NO_SOLUTIONS:
                System.out.println("Система не имеет решений");
                return null;
            default:
                return null;
        }
    }


    // Нахождение решений
    public double[] FindSolutions() {
        // Создаем массив решений размером rowAmount
        double[] solutions = new double[rowAmount];

        // Цикл по строкам матрицы в обратном порядке
        for (int i = rowAmount - 1; i >= 0; i--) {
            // Вызываем метод GetSolution() для получения решения текущей строки
            double solution = GetSolution(i);
            // Обновляем значение решения для текущей строки
            solutions[i] = solution;
        }
        // Возвращаем массив решений
        return solutions;
    }

    // Создание матрицы
    private void Create(int rowAmount, int columnAmount) {
        // Создаем массив массивов для матрицы
        matrixArray = new double[rowAmount][columnAmount];

        // Цикл по строкам матрицы
        for (int i = 0; i < rowAmount; i++) {
            // Создаем массив элементов для каждой строки
            matrixArray[i] = new double[columnAmount];
        }
    }

    // Найти строку с ненулевым элементом
    private int FindLineWithNotZeroElement(int lineNumber) {

        // Цикл по строкам, начиная с lineNumber
        for (int i = lineNumber; i < rowAmount; i++) {
            // Если элемент строки на позиции lineNumber не равен нулю
            if (!IsZeroElement(matrixArray[i][lineNumber])) {
                // Возвращаем индекс строки
                return i;
            }
        }
        // Возвращаем NotFounded
        return NotFounded;
    }

    // Поменять строки местами
    private void SwapLines(int firstLine, int secondLine) {
        // Сохраняем ссылку на строку secondLine во временную переменную
        double[] temp = matrixArray[secondLine];
        // Заменяем строку secondLine на firstLine
        matrixArray[secondLine] = matrixArray[firstLine];
        // Заменяем строку firstLine на временную переменную (бывшую secondLine)
        matrixArray[firstLine] = temp;
    }

    // Проверка на равенство элемента нулю
    private boolean IsZeroElement(double element) {
        // Сравниваем абсолютное значение элемента с epsilon
        // Возвращаем результат сравнения
        return Math.abs(element) < epsilon;
    }

    // Умножение и вычитание строк
    private void MultiplicationAndSubtractionOfLine(double multiplier, int iterationNumber, int lineNumber) {
        // Цикл по столбцам матрицы
        for (int j = 0; j < columnAmount; j++) {
            // Вычитаем произведение множителя на элемент строки iterationNumber из элемента строки lineNumber
            matrixArray[lineNumber][j] -= multiplier * matrixArray[iterationNumber][j];

            // Если элемент строки lineNumber равен нулю, устанавливаем его значение в 0
            if (IsZeroElement(matrixArray[lineNumber][j])) {
                matrixArray[lineNumber][j] = 0;
            }
        }
    }

    // Получение решения
    private double GetSolution(int i) {
        // Выделение памяти под массив solution:
        solution = new double[columnAmount];
        // Инициализация переменной для суммы
        double sum = 0;

        // Цикл по столбцам матрицы
        for (int j = 0; j < columnAmount - 1; j++) {
            // Увеличиваем сумму на произведение текущего решения на соответствующий элемент строки
            sum += solution[j] * matrixArray[i][j];
        }
        // Возвращаем сумму
        return (matrixArray[i][columnAmount - 1] - sum) / matrixArray[i][i];
    }
}

