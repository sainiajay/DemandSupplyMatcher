package org.deeprooted;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private void testHelper(String inputFilePath, String outputFilePath) throws IOException {
        PipedOutputStream outputStream = new PipedOutputStream();
        System.setOut(new PrintStream(outputStream));
        Scanner actualOutputScanner = new Scanner(new PipedInputStream(outputStream));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(inputFilePath);
        App.run(inputStream);

        InputStream expectedOutputStream = getClass().getClassLoader().getResourceAsStream(outputFilePath);
        Scanner expectedOutputScanner = new Scanner(expectedOutputStream);

        while(expectedOutputScanner.hasNext() && actualOutputScanner.hasNext()) {
            Assert.assertEquals(expectedOutputScanner.nextLine(), actualOutputScanner.nextLine());
        }
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void case1() throws Exception {
        testHelper("test-cases/case1/input.txt", "test-cases/case1/output.txt");
    }

    @Test
    public void case2() throws Exception {
        testHelper("test-cases/case2/input.txt", "test-cases/case2/output.txt");
    }
}
