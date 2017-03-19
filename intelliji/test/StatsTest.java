import models.CDSResult;
import models.TreatFile;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by brice on 15/03/17.
 */
public class StatsTest
{
    @Test
    public void verifyStatsTest()
    {
        try
        {
            FileInputStream is = new FileInputStream("tests/test4.gbk");
            CDSResult results = TreatFile.processFile(is);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
