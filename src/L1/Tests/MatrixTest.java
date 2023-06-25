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
        writer.write("1 2 3 4\n");
        writer.write("5 6 7 8\n");
        writer.write("9 10 11 12\n");
        writer.close();

        // Создание объекта Matrix и вызов методов для проверки решения
        Matrix matrix = new Matrix(filename);
        matrix.MakeTriangle();
        double[] solutions = matrix.CheckSolutions();

        // Проверка правильности решения
        double[] expected = {1, 1, 1};
        Assert.assertArrayEquals(expected, solutions, 0.0001);
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
        writer.write("3 6 9 10\n");
        writer.close();

        // Создание объекта Matrix и вызов методов для проверки решения
        Matrix matrix = new Matrix(filename);
        matrix.MakeTriangle();
        double[] solutions = matrix.CheckSolutions();

        // Проверка правильности решения
        Assert.assertEquals(Results.NO_SOLUTIONS, matrix.MakeTriangle());
        Assert.assertArrayEquals(new double[0], solutions, 0.0001);
    }
}
