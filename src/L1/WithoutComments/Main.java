package L1.WithoutComments;

import java.io.FileNotFoundException;

class Main {
    public static void GetResult(Results result) {
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

    public static void PrintResult(double[] resultArray) {
        System.out.print("Результат: ");

        for (double result : resultArray) {
            System.out.printf("%.2f\t", result);
        }

        System.out.println();
    }

    public static void main(String[] args) {
        try {
            Matrix mat = new Matrix("C:\\Users\\FossW\\IdeaProjects\\SLAE Labs\\src\\L1\\input.txt");
            System.out.println("Исходная матрица:");
            mat.Print();
            Results result = mat.MakeTriangle();
            System.out.println("Матрица после преобразования:");
            mat.Print();
            GetResult(result);
            if (result == Results.SINGLE_SOLUTION) {
                double[] solutions = mat.CheckSolutions();
                if (result == Results.SINGLE_SOLUTION) {
                    double[] array = mat.FindSolutions();
                    PrintResult(array);
                } else {
                    GetResult(result);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("FILE NOT FOUND!!!");
        }
    }
}