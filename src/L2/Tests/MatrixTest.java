package L2.Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
    private static final String TEST_FILES_DIR = "";
    private L2.Matrix matrix;

    @BeforeEach
    void setUp() throws IOException {
        createTestFile("matrix.txt", "3 4\n1 2 3 4\n5 6 7 8\n9 10 11 12");
        createTestFile("matrix_single_solution.txt", "3 4\n1 2 3 10\n2 3 4 20\n3 4 5 30");
        createTestFile("matrix_degenerate_system.txt", "3 4\n1 2 3 10\n2 4 6 20\n3 6 9 30");
        createTestFile("matrix_infinite_solutions.txt", "3 4\n1 2 3 10\n2 4 6 20\n3 6 9 30");
        createTestFile("matrix_no_solutions.txt", "3 4\n1 2 3 10\n2 4 6 20\n3 6 9 40");

        matrix = new L2.Matrix(TEST_FILES_DIR + "matrix.txt");
    }

    @Test
    void testCheckForZeros() {
        boolean result = matrix.CheckForZeros();
        assertFalse(result, "There should be no zeros on the diagonal");
    }

    @Test
    void testCheckSCC() {
        boolean result = matrix.CheckSCC();
        assertTrue(result, "The sufficient convergence condition should be met");
    }

    @Test
    void testSolveByIterations() {
        double[] expectedSolution = new double[]{1.0, 2.0, 3.0};
        double[] actualSolution = matrix.SolveByIterations();
        assertArrayEquals(expectedSolution, actualSolution, 0.000001,
                "The actual solution does not match the expected solution");
    }

    @Test
    void testSolveWithoutControl() {
        double[] expectedSolution = new double[]{1.0, 2.0, 3.0};
        double[] actualSolution = matrix.SolveWithoutControl();
        assertArrayEquals(expectedSolution, actualSolution, 0.000001,
                "The actual solution does not match the expected solution");
    }

    @Test
    void testSolveWithControl() {
        double[] expectedSolution = new double[]{1.0, 2.0, 3.0};
        double[] actualSolution = matrix.SolveWithControl();
        assertArrayEquals(expectedSolution, actualSolution, 0.000001,
                "The actual solution does not match the expected solution");
    }

    @Test
    void testInit() {
        assertThrows(FileNotFoundException.class, () -> new L2.Matrix(TEST_FILES_DIR + "non_existent_file.txt"),
                "A FileNotFoundException should be thrown if the file does not exist");
    }

    private void createTestFile(String fileName, String content) throws IOException {
        File file = new File(TEST_FILES_DIR + fileName);
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.close();
    }
}
