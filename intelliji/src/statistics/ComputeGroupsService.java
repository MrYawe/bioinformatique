package statistics;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import config.Config;
import config.ConfigManager;
import tree.TreeBuilderService.OrganismType;

/**
 * Created by brice on 14/05/17.
 */
public class ComputeGroupsService extends AbstractExecutionThreadService
{
    private OrganismType type;
    private String path;
    private Config config;

    public ComputeGroupsService(OrganismType type)
    {
        this.type = type;
        this.config = ConfigManager.getConfig();
        this.path = this.config.getResultsFolder() + this.config.getFolderSeparator();
        switch(this.type)
        {
            case EUKARYOTES:
                this.path += "EUKARYOTES";
                break;
            case PROKARYOTES:
                this.path += "PROKARYOTES";
                break;
            case VIRUSES:
                this.path += "VIRUSES";
        }
    }

    protected void run()
    {
        XlsExport.computePartialSums(this.path);
    }
}
