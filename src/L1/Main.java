package L1;

import java.io.FileNotFoundException;

// Класс Main
class Main {
    // Метод вывода результата на экран
    public static void GetResult(Results result) {
        // Используем оператор switch для вывода соответствующего текстового сообщения в зависимости от значения result
        switch (result) {
            case SINGLE_SOLUTION:
                System.out.println("Система имеет единственное решение");
                break;
            case DEGENERATE_SYSTEM:
                System.out.println("Система вырожденная");
                break;
            case INFINITE_SOLUTIONS:
                System.out.println("Система имеет бесконечное количество решений");
                break;
            case NO_SOLUTIONS:
                System.out.println("Система не имеет решений");
                break;
        }
    }

    // Метод вывода массива решений на экран
    public static void PrintResult(double[] resultArray) {
        // Выводим текст "Результат: "
        System.out.print("Результат: ");

        // Цикл по элементам массива resultArray
        for (double result : resultArray) {
            // Выводим элемент массива на экран с форматированием
            System.out.printf("%.2f\t", result);
        }

        // Выводим пустую строку
        System.out.println();
    }

    public static void main(String[] args) {
        // Создаем объект mat типа Matrix
        Matrix mat = new Matrix();

        try {
            // Загружаем матрицу из файла "src/Input.txt"
            mat.Init("C:\\Users\\FossW\\IdeaProjects\\SLAE Labs\\src\\L1\\input.txt");
            // Выводим матрицу на экран
            mat.Print();
            // Вызываем метод MakeTriangle для матрицы и сохраняем результат в переменную result
            Results result = mat.MakeTriangle();
            // Выводим матрицу на экран после преобразования
            mat.Print();
            // Выводим результат операции (текстовое сообщение) с помощью метода GetResult
            GetResult(result);
            // Если результат равен "система может быть продолжена"
            if (result == Results.SINGLE_SOLUTION) {
                // Вызываем метод CheckSolutions для матрицы и обновляем переменную result
                double[] solutions = mat.CheckSolutions();

                // Если результат равен "система имеет единственное решение"
                if (result == Results.SINGLE_SOLUTION) {
                    // Вызываем метод FindSolutions для матрицы и сохраняем результат в массив solutions
                    double[] array = mat.FindSolutions();

                    // Выводим массив решений на экран с помощью метода PrintResult
                    PrintResult(array);
                } else {
                    // Иначе
                    // Выводим результат операции (текстовое сообщение) с помощью метода GetResult
                    GetResult(result);
                }
            }
        } catch (FileNotFoundException e) {
            // В случае ошибки FileNotFoundException, выводим сообщение "FILE NOT FOUND!!!"
            System.out.println("FILE NOT FOUND!!!");
        }


    }

}