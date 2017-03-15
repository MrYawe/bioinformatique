import models.TreatFile;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by mourse on 20/02/17.
 */
public class TreatFileTest {
    @Test
    public void processFileTest() throws Exception
    {
        InputStream is = new FileInputStream("tests/test4.gbk");
        TreatFile.processFile(is);
    }
}