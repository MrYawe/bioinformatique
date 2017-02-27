import models.TreatFile;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mourse on 20/02/17.
 */
public class TreatFileTest {
    @Test
    public void processFileTest() throws Exception {
        java.io.File file = new java.io.File("tests/test7.gbk");
        TreatFile.processFile(file);
    }

}