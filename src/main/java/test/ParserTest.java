package test;

import org.junit.Test;

import parsers.FenParser;

public class ParserTest
{
    @Test
    public void testParser()
    {
        final String a = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        final String b = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1";
        final String c = "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2";
        final String d = "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2";

        FenParser.validate(a);
        FenParser.validate(b);
        FenParser.validate(c);
        FenParser.validate(d);

    }
}
