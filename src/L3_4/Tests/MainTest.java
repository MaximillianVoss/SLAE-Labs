package L3_4.Tests;

import L3_4.Common.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testMain() {
        // Call your main function
        Main.main(new String[]{});

        // Now you can compare the contents of outputStreamCaptor to your expected output.
        // You can convert outputStreamCaptor to a string using outputStreamCaptor.toString().

        String actualOutput = outputStreamCaptor.toString().trim();
        String newline = System.lineSeparator();

        String expectedOutput = "Полином в форме Лагранжа:" + newline +
                "  -1,630478E-02*x^6  -9,903393E-03*x^5 +   8,519188E-03*x^4  -3,520055E-03*x^3 +   5,006830E-01*x^2  -4,843747E-05*x^1" + newline +
                newline +
                "Полином в форме Ньютона:" + newline +
                "  -1,630478E-02*x^6  -9,903393E-03*x^5 +   8,519188E-03*x^4  -3,520055E-03*x^3 +   5,006830E-01*x^2  -4,843747E-05*x^1" + newline +
                newline +
                "                   X                    Y                 f(x)                Ln(x)                Nn(x)" + newline +
                "        0,000000E+00         0,000000E+00         0,000000E+00        -0,000000E+00        -0,000000E+00" + newline +
                "        8,333333E-02                              3,472215E-03         3,471257E-03         3,471257E-03" + newline +
                "        1,666667E-01         1,388844E-02         1,388844E-02         1,388844E-02         1,388844E-02" + newline +
                "        2,500000E-01                              3,124491E-02         3,124520E-02         3,124520E-02" + newline +
                "        3,333333E-01         5,552698E-02         5,552698E-02         5,552698E-02         5,552698E-02" + newline +
                "        4,166667E-01                              8,669658E-02         8,669640E-02         8,669640E-02" + newline +
                "        5,000000E-01         1,246747E-01         1,246747E-01         1,246747E-01         1,246747E-01" + newline +
                "        5,833333E-01                              1,693192E-01         1,693194E-01         1,693194E-01" + newline +
                "        6,666667E-01         2,203977E-01         2,203977E-01         2,203977E-01         2,203977E-01" + newline +
                "        7,500000E-01                              2,775568E-01         2,775563E-01         2,775563E-01" + newline +
                "        8,333333E-01         3,402871E-01         3,402871E-01         3,402871E-01         3,402871E-01" + newline +
                "        9,166667E-01                              4,078873E-01         4,078889E-01         4,078889E-01" + newline +
                "        1,000000E+00         4,794255E-01         4,794255E-01         4,794255E-01         4,794255E-01";



        assertEquals(expectedOutput, actualOutput);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
}
