import models.CDS;
import models.Origin;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mourse on 26/02/17.
 */
public class OriginTest {
    @Test
    public void formatTest() throws Exception {
        CDS elmt = new CDS("j", 431, 518);
        Origin.formatpchain("        1 tttgaaaact gctctaattg ctttggtttt ggtttttcga cagtcatttg aaaaccgctc      421 ttcaggtgtg atggtggggg atgaaatcat tgcagttaat gacattgatg taacagaagt      481 agagaatgct gtggaagatc tgagggaagc tttgaaaggt gaggatgcca tctagtgaat      541 caataatagc actggataca tttcatttcc agatttatga aggctcggag agaggttatt\n",elmt);
    }
}