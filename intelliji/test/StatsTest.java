import models.CDSResult;
import models.TreatFile;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

/**
 * Created by brice on 15/03/17.
 */
public class StatsTest
{
    @Test
    public void verifyStatsTest()
    {
        java.io.File file = new java.io.File("tests/test4.gbk");
        try
        {
            CDSResult results = TreatFile.processFile(file);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
