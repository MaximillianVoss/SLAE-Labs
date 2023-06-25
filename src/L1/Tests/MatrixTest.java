package L1.Tests;

import L1.WithComments.Matrix;
import L1.WithComments.Results;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

public class MatrixTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testInit() throws FileNotFoundException {
        Matrix matrix = new Matrix("matrix.txt");

        // Assert the matrix dimensions
        Assertions.assertEquals(3, matrix.getRowAmount());
        Assertions.assertEquals(4, matrix.getColumnAmount());

        // Assert the matrix values
        double[][] expectedMatrixArray = {
                {2.0, -3.0, 4.0, 5.0},
                {1.0, 2.0, -1.0, 0.0},
                {-4.0, -1.0, 3.0, 2.0}
        };
        Assertions.assertArrayEquals(expectedMatrixArray, matrix.getMatrixArray());
    }

    @Test
    public void testMakeTriangle_singleSolution() throws IOException {
        Matrix matrix = new Matrix("matrix_single_solution.txt");
        Results result = matrix.MakeTriangle();

        Assertions.assertEquals(Results.SINGLE_SOLUTION, result);
    }

    @Test
    public void testMakeTriangle_degenerateSystem() throws IOException {
        Matrix matrix = new Matrix("matrix_degenerate_system.txt");
        Results result = matrix.MakeTriangle();

        Assertions.assertEquals(Results.DEGENERATE_SYSTEM, result);
    }

    @Test
    public void testMakeTriangle_infiniteSolutions() throws IOException {
        Matrix matrix = new Matrix("matrix_infinite_solutions.txt");
        Results result = matrix.MakeTriangle();

        Assertions.assertEquals(Results.INFINITE_SOLUTIONS, result);
    }

    @Test
    public void testMakeTriangle_noSolutions() throws IOException {
        Matrix matrix = new Matrix("matrix_no_solutions.txt");
        Results result = matrix.MakeTriangle();

        Assertions.assertEquals(Results.NO_SOLUTIONS, result);
    }

    @Test
    public void testCheckSolutions_singleSolution() throws IOException {
        Matrix matrix = new Matrix("matrix_single_solution.txt");
        double[] solutions = matrix.CheckSolutions();

        Assertions.assertArrayEquals(new double[]{1.0, 2.0, 3.0}, solutions, 0.0001);
    }

    @Test
    public void testCheckSolutions_degenerateSystem() throws IOException {
        Matrix matrix = new Matrix("matrix_degenerate_system.txt");
        double[] solutions = matrix.CheckSolutions();

        Assertions.assertTrue(solutions.length == 0);
        Assertions.assertEquals("Система вырожденная\r\n", outputStream.toString());
    }

    @Test
    public void testCheckSolutions_infiniteSolutions() throws IOException {
        Matrix matrix = new Matrix("matrix_infinite_solutions.txt");
        double[] solutions = matrix.CheckSolutions();

        Assertions.assertNull(solutions);
        Assertions.assertEquals("Система имеет бесконечное количество решений\n", outputStream.toString());
    }

    @Test
    public void testCheckSolutions_noSolutions() throws IOException {
        Matrix matrix = new Matrix("matrix_no_solutions.txt");
        double[] solutions = matrix.CheckSolutions();

        Assertions.assertTrue(solutions.length == 0);
        Assertions.assertEquals("Система не имеет решений\n", outputStream.toString());
    }


    private void createMatrixFile(String fileName, String content) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.write(content);
        }
    }

    @BeforeEach
    public void createTestFiles() throws IOException {
        createMatrixFile("matrix.txt", "3 4 1\n2 -3 4 5\n1 2 -1 0\n-4 -1 3 2");
        createMatrixFile("matrix_single_solution.txt", "3 4 1\n1 2 3 10\n2 3 4 20\n3 4 5 30");
        createMatrixFile("matrix_degenerate_system.txt", "3 4 1\n1 2 3 10\n2 4 6 20\n3 6 9 30");
        createMatrixFile("matrix_infinite_solutions.txt", "3 4 1\n1 2 3 10\n2 4 6 20\n3 6 9 30");
        createMatrixFile("matrix_no_solutions.txt", "3 4 1\n1 2 3 10\n2 4 6 20\n3 6 9 40");
    }

    @Test
    public void cleanupTestFiles() {
        File[] filesToDelete = {
                new File("matrix.txt"),
                new File("matrix_single_solution.txt"),
                new File("matrix_degenerate_system.txt"),
                new File("matrix_infinite_solutions.txt"),
                new File("matrix_no_solutions.txt")
        };

        for (File file : filesToDelete) {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
