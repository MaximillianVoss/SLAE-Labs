package L2.Tests;

import L2.WithoutComments.Matrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
    private static final String TEST_FILES_DIR = "";
    private Matrix matrix;

    @BeforeEach
    void setUp() throws IOException {
        createTestFile("matrix.txt", "3 4\n1 2 3 10\n2 3 4 20\n3 4 5 30");
        createTestFile("matrix_single_solution.txt", "2 3\n1 2 6\n2 4 16");
        createTestFile("matrix_degenerate_system.txt", "3 4\n1 2 3 10\n2 4 6 20\n3 6 9 30");
        createTestFile("matrix_infinite_solutions.txt", "3 4\n1 2 3 10\n2 4 6 20\n3 6 9 30");
        createTestFile("matrix_no_solutions.txt", "3 4\n1 2 3 10\n2 4 6 20\n3 6 9 40");
        createTestFile("matrix_custom.txt", "4 5\n9 3 1 2 -5\n1 4 5 1 8\n2 1 0 -6 9\n0 3 -1 6 0");
    }

    @Test
    void testSolveWithControlForCustomMatrix() throws FileNotFoundException {
        matrix = new Matrix(TEST_FILES_DIR + "matrix_custom.txt");
        double[] actualSolution = matrix.SolveByIterations();

        double x1 = -1383.0/1193;
        double x2 = 3339.0/1193;
        double x3 = -147.0/1193;
        double x4 = -1694.0/1193;

        double[] expectedSolution = {x1, x2, x3, x4};
        assertArrayEquals(expectedSolution, actualSolution, 0.000001,
                "The actual solution does not match the expected solution");
    }


    @Test
    void testSolveWithControlForMatrixSingleSolution() throws FileNotFoundException {
        matrix = new Matrix(TEST_FILES_DIR + "matrix_single_solution.txt");
        double[] expectedSolution = new double[]{2.0, 2.0};
        double[] actualSolution = matrix.SolveByIterations();
        assertArrayEquals(expectedSolution, actualSolution, 0.000001,
                "The actual solution does not match the expected solution");
    }

    @Test
    void testSolveWithControlForMatrixDegenerateSystem() throws FileNotFoundException {
        matrix = new Matrix(TEST_FILES_DIR + "matrix_degenerate_system.txt");
        assertThrows(IllegalArgumentException.class, matrix::SolveByIterations,
                "Solving a degenerate system should throw an exception");
    }

    @Test
    void testSolveWithControlForMatrixInfiniteSolutions() throws FileNotFoundException {
        matrix = new Matrix(TEST_FILES_DIR + "matrix_infinite_solutions.txt");
        assertThrows(IllegalArgumentException.class, matrix::SolveByIterations,
                "Solving a system with infinite solutions should throw an exception");
    }

    @Test
    void testSolveWithControlForMatrixNoSolutions() throws FileNotFoundException {
        matrix = new Matrix(TEST_FILES_DIR + "matrix_no_solutions.txt");
        assertThrows(IllegalArgumentException.class, matrix::SolveByIterations,
                "Solving a system with no solutions should throw an exception");
    }

    private void createTestFile(String fileName, String content) throws IOException {
        File file = new File(TEST_FILES_DIR + fileName);
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
    }
}
