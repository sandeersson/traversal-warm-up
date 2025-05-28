// JUnit test class for TraversalPractice methods
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.*;

public class TraversalPracticeTest {

    /**
     * Splits the captured output into lines, handling different newline conventions.
     */
    private List<String> getLines(String output) {
        String trimmed = output.trim();
        if (trimmed.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(trimmed.split("\\R"));
    }


    /** The below are utility classes that help with testing. You do not need to modify them, but you can look with interest! */
    /**
     * Simple PrintStream that writes output to both the console and an internal buffer.
     */
    private static class TeePrintStream extends PrintStream {
        private final PrintStream second;

        public TeePrintStream(PrintStream main, PrintStream second) {
            super(main);
            this.second = second;
        }

        @Override
        public void write(byte[] buf, int off, int len) {
            super.write(buf, off, len);
            second.write(buf, off, len);
        }

        @Override
        public void write(int b) {
            super.write(b);
            second.write(b);
        }
    }

    /**
     * Captures the output of a Runnable while still printing to the console.
     * @param runnable the code that prints to System.out
     * @return the captured output as a String
     */
    private String captureOutput(Runnable runnable) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream second = new PrintStream(baos, true);
        TeePrintStream tee = new TeePrintStream(originalOut, second);
        System.setOut(tee);
        try {
            runnable.run();
        } finally {
            System.out.flush();
            System.setOut(originalOut);
        }
        return baos.toString();
    }
}
