package L1.Tests;

import L1.WithoutComments.Matrix;
import L1.WithoutComments.Results;
import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class MatrixTest {

    @Test
    public void testSingleSolution() throws IOException {
        // Создание файла с данными для случая с единственным решением
        String filename = "input_single_solution.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("3 4 1\n");
        writer.write("1 2 3 14\n");
        writer.write("4 -5 6 32\n");
        writer.write("7 8 -9 14\n");
        writer.close();

        // Создание объекта Matrix и вызов методов для проверки решения
        Matrix matrix = new Matrix(filename);
        Results result = matrix.MakeTriangle();
        Assert.assertEquals(Results.SINGLE_SOLUTION, result);

        if (result == Results.SINGLE_SOLUTION) {
            double[] solutions = matrix.FindSolutions();
            // Проверка правильности решения
            double[] expected = {4.745762711864407, 0.6101694915254238, 2.6779661016949152};
            ; // Здесь ожидаемый результат будет зависеть от ваших данных и метода решения
            Assert.assertArrayEquals(expected, solutions, 0.0001);
        }
    }

    @Test
    public void testSingleSolution2() throws IOException {
        // Создание файла с данными для случая с единственным решением
        String filename = "input_single_solution.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("4 5 1\n");
        writer.write("0 4 4 0 -5\n");
        writer.write("5 5 0 0 8\n");
        writer.write("0 2 2 9 9\n");
        writer.write("4 0 0 4 0\n");
        writer.close();

        // Создание объекта Matrix и вызов методов для проверки решения
        Matrix matrix = new Matrix(filename);
        Results result = matrix.MakeTriangle();
        Assert.assertEquals(Results.SINGLE_SOLUTION, result);

        if (result == Results.SINGLE_SOLUTION) {
            double[] solutions = matrix.FindSolutions();
            // Проверка правильности решения
            double[] expected = {
                    -1.2777777777777781,
                    2.877777777777778,
                    -4.127777777777778,
                    1.2777777777777777
            };
            ; // Здесь ожидаемый результат будет зависеть от ваших данных и метода решения
            Assert.assertArrayEquals(expected, solutions, 0.0001);
        }
    }

    @Test
    public void testSingleSolution3() throws IOException {
        // Создание файла с данными для случая с единственным решением
        String filename = "input_single_solution.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("4 5 1\n");
        writer.write("0 4 4 0 -5\n");
        writer.write("5 5 0 0 8\n");
        writer.write("0 2 2 9 9\n");
        writer.write("4 0 0 4 0\n");
        writer.close();

        // Создание объекта Matrix и вызов методов для проверки решения
        L1.WithComments.Matrix matrix = new L1.WithComments.Matrix(filename);
        L1.WithComments.Results result = matrix.MakeTriangle();
        Assert.assertEquals(L1.WithComments.Results.SINGLE_SOLUTION, result);

        if (result == L1.WithComments.Results.SINGLE_SOLUTION) {
            double[] solutions = matrix.FindSolutions();
            // Проверка правильности решения
            double[] expected = {
                    -1.2777777777777781,
                    2.877777777777778,
                    -4.127777777777778,
                    1.2777777777777777
            };
            ; // Здесь ожидаемый результат будет зависеть от ваших данных и метода решения
            Assert.assertArrayEquals(expected, solutions, 0.0001);
        }
    }

    @Test
    public void testDegenerateSystem() throws IOException {
        // Создание файла с данными для случая с вырожденной системой
        String filename = "input_degenerate_system.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("3 4 1\n");
        writer.write("0 0 0 0\n");
        writer.write("5 6 7 8\n");
        writer.write("9 10 11 12\n");
        writer.close();

        // Создание объекта Matrix и вызов методов для проверки решения
        Matrix matrix = new Matrix(filename);
        matrix.MakeTriangle();
        double[] solutions = matrix.CheckSolutions();

        // Проверка правильности решения
        Assert.assertEquals(Results.DEGENERATE_SYSTEM, matrix.MakeTriangle());
        Assert.assertArrayEquals(new double[0], solutions, 0.0001);
    }

    @Test
    public void testInfiniteSolutions() throws IOException {
        // Создание файла с данными для случая с бесконечным количеством решений
        String filename = "input_infinite_solutions.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("3 4 1\n");
        writer.write("1 2 3 4\n");
        writer.write("2 4 6 8\n");
        writer.write("3 6 9 12\n");
        writer.close();

        // Создание объекта Matrix и вызов методов для проверки решения
        Matrix matrix = new Matrix(filename);
        matrix.MakeTriangle();
        double[] solutions = matrix.CheckSolutions();

        // Проверка правильности решения
        Assert.assertEquals(Results.INFINITE_SOLUTIONS, matrix.MakeTriangle());
        Assert.assertArrayEquals(new double[0], solutions, 0.0001);
    }

    @Test
    public void testNoSolutions() throws IOException {
        // Создание файла с данными для случая без решений
        String filename = "input_no_solutions.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("3 4 1\n");
        writer.write("1 2 3 4\n");
        writer.write("2 4 6 8\n");
        writer.write("3 6 9 15\n");
        writer.close();

        // Создание объекта Matrix и вызов методов для проверки решения
        Matrix matrix = new Matrix(filename);
        Results result = matrix.MakeTriangle(); // Сохранение результата в переменную
        double[] solutions = matrix.CheckSolutions();

        // Проверка правильности решения
        Assert.assertEquals(Results.NO_SOLUTIONS, result); // Использование переменной здесь
        Assert.assertArrayEquals(new double[0], solutions, 0.0001);
    }


}
