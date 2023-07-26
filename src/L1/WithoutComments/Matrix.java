package L1.WithoutComments;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Matrix {

    private double[][] matrixArray;
    private int rowAmount;
    private int columnAmount;
    private double epsilon;
    private static final int NotFounded = -1;
    private double[] solution;

    public Matrix(String fileName) throws FileNotFoundException {
        this.Init(fileName);
    }

    public void Print() {
        for (int i = 0; i < rowAmount; i++) {
            for (int j = 0; j < columnAmount; j++) {
                System.out.printf("%18.6E", matrixArray[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void Init(String s) throws FileNotFoundException {
        File file = new File(s);
        Scanner scan = new Scanner(file);
        Pattern pat = Pattern.compile("[ \t]");
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
            for (j = 0; j < columnAmount; j++) {
                matrixArray[i][j] = Double.parseDouble(sn[j]);
            }
        }
        scan.close();
    }

    public boolean IsAllZeroRow(int row) {
        for (int i = 0; i < columnAmount; i++) {
            if (Math.abs(matrixArray[row][i]) > this.epsilon) {
                return false;
            }
        }
        return true;
    }


    public Results MakeTriangle() {
        for (int iteration = 0; iteration < rowAmount; iteration++) {
            boolean swapResult = SwapFirstNotZeroLine(iteration);
            if (!swapResult) {
                if (IsZeroElement(matrixArray[iteration][columnAmount - 1])) {
                    return Results.INFINITE_SOLUTIONS;
                } else {
                    return Results.NO_SOLUTIONS;
                }
            }

            if (Math.abs(matrixArray[iteration][iteration]) < this.epsilon) {
                if (iteration == rowAmount - 1 && IsZeroElement(matrixArray[iteration][columnAmount - 1])) {
                    return Results.INFINITE_SOLUTIONS;
                } else {
                    return Results.DEGENERATE_SYSTEM;
                }
            }

            RecalculateCoefficients(iteration, iteration);
        }

        // Проверка после приведения к треугольному виду
        for (int i = 0; i < rowAmount; i++) {
            boolean allZeros = true;
            for (int j = 0; j < columnAmount - 1; j++) {
                if (Math.abs(matrixArray[i][j]) > this.epsilon) {
                    allZeros = false;
                    break;
                }
            }
            if (allZeros && Math.abs(matrixArray[i][columnAmount - 1]) > this.epsilon) {
                return Results.NO_SOLUTIONS;
            }
        }

        return Results.SINGLE_SOLUTION;
    }






    private void RecalculateCoefficients(int row, int iteration) {
        double diagElement = matrixArray[iteration][iteration];
        for (int j = iteration; j < columnAmount; j++) {
            matrixArray[iteration][j] /= diagElement;
        }
        for (int i = iteration + 1; i < rowAmount; i++) {
            double upperRowCoeff = matrixArray[i][iteration];
            for (int j = iteration; j < columnAmount; j++) {
                matrixArray[i][j] -= matrixArray[iteration][j] * upperRowCoeff;
            }
        }
    }


    private boolean SwapFirstNotZeroLine(int lineNumber) {
        if (IsZeroElement(matrixArray[lineNumber][lineNumber])) {
            int notZeroLine = FindLineWithNotZeroElement(lineNumber);
            if (notZeroLine == NotFounded) {
                return false;
            }
            SwapLines(lineNumber, notZeroLine);
        }
        return true;
    }

    public double[] CheckSolutions() {
        Results result = MakeTriangle();
        switch (result) {
            case SINGLE_SOLUTION:
                solution = FindSolutions();
                return solution;
            case DEGENERATE_SYSTEM:
                System.out.println("Система вырожденная");
                return new double[0];
            case INFINITE_SOLUTIONS:
                System.out.println("Система имеет бесконечное количество решений");
                return new double[0];
            case NO_SOLUTIONS:
                System.out.println("Система не имеет решений");
                return new double[0];
            default:
                return new double[0];
        }

    }

    public double[] FindSolutions() {
        solution = new double[rowAmount];
        for (int i = rowAmount - 1; i >= 0; i--) {
            double sol = GetSolution(i);
            solution[i] = sol;
        }
        return solution;
    }

    private void Create(int rowAmount, int columnAmount) {
        matrixArray = new double[rowAmount][columnAmount];
        for (int i = 0; i < rowAmount; i++) {
            matrixArray[i] = new double[columnAmount];
        }
    }

    private int FindLineWithNotZeroElement(int lineNumber) {
        for (int i = lineNumber; i < rowAmount; i++) {
            if (!IsZeroElement(matrixArray[i][lineNumber])) {
                return i;
            }
        }
        return NotFounded;
    }

    private void SwapLines(int firstLine, int secondLine) {
        double[] temp = matrixArray[secondLine];
        matrixArray[secondLine] = matrixArray[firstLine];
        matrixArray[firstLine] = temp;
    }

    private boolean IsZeroElement(double element) {
        return Math.abs(element) < epsilon;
    }

    private double GetSolution(int i) {
        double sum = 0;
        for (int j = i + 1; j < columnAmount - 1; j++) {
            sum += solution[j] * matrixArray[i][j];
        }
        return (matrixArray[i][columnAmount - 1] - sum) / matrixArray[i][i];
    }

    public int getRowAmount() {
        return this.rowAmount;
    }

    public int getColumnAmount() {
        return this.columnAmount;
    }

    public double[][] getMatrixArray() {
        return this.matrixArray;
    }
}