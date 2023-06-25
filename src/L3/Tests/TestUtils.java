package L3.Tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestUtils {

    public static String captureStandardOutput(Runnable runnable) {
        PrintStream originalOut = System.out;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));
            runnable.run();
            return outputStream.toString();
        } finally {
            System.setOut(originalOut);
        }
    }
}
