package tree;

import config.Config;
import config.ConfigManager;
import io.Net;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yannis on 29/01/17.
 */
public class OrganismTree extends Tree {

    public static OrganismTree fromGenBank() {
        Config config = ConfigManager.getConfig();

        System.out.println("Building Tree... (2-3min)");
        OrganismTree res = new OrganismTree();
        String baseurl = config.getTreeUrl();
        int page = 1;
        int pageSize = 100;
        Scanner buffer = new Scanner("");
        Boolean flag = true;
        while (flag){
            buffer = Net.getUrl(baseurl+page+"&amp;pageSize="+pageSize);
            System.out.println("Building Tree... (page "+page+")");
            int debut=0;
            while(buffer.hasNext()){
                String cur = buffer.next();
                if (debut==0){
                    if (buffer.hasNext()){
                        cur = buffer.next();
                        debut++;
                    }
                    else{
                        flag=false;
                        break;
                    }
                }

                // regexp1
                Pattern regex1 = Pattern.compile("<td>(.*?)<\\/td>");
                Matcher m = regex1.matcher(cur);
                m.find();
                String organism = m.group(1);
                m.find();
                String kingdom = m.group(1);
                //System.out.println(kingdom);
                m.find();
                String group = m.group(1);
                //System.out.println(group);
                m.find();
                String subgroup = m.group(1);
                //System.out.println(subgroup);

                //Regexp2
                Pattern regex2 = Pattern.compile(">(.*?)<\\/a>");
                Matcher n = regex2.matcher(organism);
                n.find();
                organism = n.group(1);
                //System.out.println(organism);


                //Building Tree
                //Root Level
                Tree<Tree> level1 = new Tree<Tree>();
                if (!res.contains(kingdom)){
                    res.add(kingdom, level1);
                }
                else {
                    level1=(Tree<Tree>) res.get(kingdom);
                }

                //Level 1
                Tree<Tree> level2 = new Tree<Tree>();

                if (!level1.contains(group)){
                    level1.add(group, level2);
                }
                else {
                    level2=(Tree<Tree>) level1.get(group);
                }

                //Level 2
                Tree<String> level3 = new Tree<String>();

                if (!level2.contains(subgroup)){
                    level2.add(subgroup, level3);
                }
                else {
                    level3=(Tree<String>) level2.get(subgroup);
                }

                //Level 3
                level3.add(organism, null);

            }
            buffer.close();
            page++;
        }
        //res.printTree();
        System.out.println("Done. =D");
        return res;
    }
}
