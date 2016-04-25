package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

import parsers.FenParser;

public class CsvReader
{

    public static void main(final String[] args)
    {

        try
        {

            //csv file containing data
            final String strFile = "C:/tmp/chs.pgn";

            //create BufferedReader to read csv file
            final BufferedReader br = new BufferedReader(new FileReader(strFile));
            String strLine = "";
            final StringTokenizer st = null;
            int lineNumber = 0, tokenNumber = 0;

            //read comma separated file line by line
            while ((strLine = br.readLine()) != null)
            {
                lineNumber++;

                if (strLine.contains("[FEN"))
                {
                    final String sub = strLine.substring(strLine.indexOf("\"") + 1, strLine.lastIndexOf("\""));
                    FenParser.validate(sub);
                    // System.out.println(sub);
                }
                //reset token number
                tokenNumber = 0;

            }

        }
        catch (final Exception e)
        {
            System.out.println("Exception while reading csv file: " + e);
        }
    }
}
