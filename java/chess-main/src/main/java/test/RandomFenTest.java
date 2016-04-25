package test;

import generators.FenStringGenerator;

import org.junit.Test;

import parsers.FenParser;

public class RandomFenTest
{
    @Test
    public void testFenGeneration()
            throws InterruptedException
    {
        for (int i = 0; i < 1000000; i++)
        {
            FenParser.validate(FenStringGenerator.generateString());
        }

    }
}
