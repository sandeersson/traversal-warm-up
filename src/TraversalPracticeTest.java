import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.util.*;

public class TraversalPracticeTest {

    /**
     * Tree:
     *          9
     *         / \
     *        2   5
     *       / \   \
     *      7   1   3
     *     /       / \
     *    4       8  33
     *     \      /  \
     *      6    0    77
     */
    @Test
    public void testSampleTree() {
        // Construct the sample tree from the Javadoc
        TreeNode root = new TreeNode(9,
            new TreeNode(2,
                new TreeNode(7,
                    new TreeNode(4, null, new TreeNode(6)),
                    null
                ),
                new TreeNode(1)
            ),
            new TreeNode(5,
                null,
                new TreeNode(3,
                    new TreeNode(8),
                    new TreeNode(33, new TreeNode(0), new TreeNode(77))
                )
            )
        );

        String output = captureOutput(() -> TraversalPractice.printLeafNodes(root));
        List<String> lines = getLines(output);
        List<String> expected = Arrays.asList("6", "1", "8", "0", "77");
        assertEquals(expected, lines);
    }

    /**
     * Tree:
     *     42
     */
    @Test
    public void testSingleNode() {
        TreeNode root = new TreeNode(42);
        String output = captureOutput(() -> TraversalPractice.printLeafNodes(root));
        List<String> lines = getLines(output);
        assertEquals(Collections.singletonList("42"), lines);
    }

    /**
     * Tree:
     *     1
     *    /
     *   2
     */
    @Test
    public void testLeftOnlyChild() {
        TreeNode root = new TreeNode(1, new TreeNode(2), null);
        String output = captureOutput(() -> TraversalPractice.printLeafNodes(root));
        List<String> lines = getLines(output);
        assertEquals(Collections.singletonList("2"), lines);
    }

    /**
     * Tree:
     *   1
     *    \
     *     3
     */
    @Test
    public void testRightOnlyChild() {
        TreeNode root = new TreeNode(1, null, new TreeNode(3));
        String output = captureOutput(() -> TraversalPractice.printLeafNodes(root));
        List<String> lines = getLines(output);
        assertEquals(Collections.singletonList("3"), lines);
    }

    /**
     * Tree: null (empty tree)
     */
    @Test
    public void testNullInput() {
        String output = captureOutput(() -> TraversalPractice.printLeafNodes(null));
        List<String> lines = getLines(output);
        assertTrue(lines.isEmpty());
    }

    /**
     * Tree:
     *   1
     *    \
     *     2
     *      \
     *       3
     */
    @Test
    public void testRightChain() {
        // A tree that only has right children: 1 -> 2 -> 3
        TreeNode root = new TreeNode(1, null,
            new TreeNode(2, null,
                new TreeNode(3)
            )
        );
        String output = captureOutput(() -> TraversalPractice.printLeafNodes(root));
        List<String> lines = getLines(output);
        assertEquals(Collections.singletonList("3"), lines);
    }

    /**
     * Tree:
     *       10
     *      /  \
     *     5    15
     *         /  \
     *        12   20
     */
    @Test
    public void testMultipleLeavesMixed() {
        // A balanced tree with leaves at different depths
        TreeNode root = new TreeNode(10,
            new TreeNode(5),
            new TreeNode(15,
                new TreeNode(12),
                new TreeNode(20)
            )
        );
        String output = captureOutput(() -> TraversalPractice.printLeafNodes(root));
        List<String> lines = getLines(output);
        List<String> expected = Arrays.asList("5", "12", "20");
        assertEquals(expected, lines);
    }

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


    /** The below are utility classes that help with testing. You do not need to modify them. */
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