package L2;


import java.io.FileNotFoundException;

// Класс Main
// Класс Main
public class Main {

    public static void main(String[] args) {
        // Создание объекта матрицы
        try {
            Matrix matrix = new Matrix("C:\\Users\\FossW\\IdeaProjects\\SLAE Labs\\src\\L2\\input.txt");
            // Вывод исходной матрицы на экран
            matrix.Print();
            // Решение системы методом итераций
            double[] solution = matrix.SolveByIterations();
            // Проверка, есть ли решение
            if (solution == null) {
                System.out.println("Система не имеет решений!");
            } else {
                // Вывод результата на экран
                printArray(solution);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    public static void printArray(double[] array) {
        // Вывод текста "Результат: "
        System.out.print("Результат: ");
        // Цикл по элементам массива matrix
        for (double element : array) {
            // Вывод элемента массива на экран с форматированием
            System.out.printf("%.2f ", element);
        }
        // Вывод пустой строки
        System.out.println();
    }
}



