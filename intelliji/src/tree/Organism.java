package tree;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import config.Config;
import config.ConfigManager;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Organism implements Serializable {
    private static final long serialVersionUID = -2867789287775171672L;
    private String kingdom;
    private String group;
    private String subgroup;
    private String name;
    private String bioproject;
    private String taxonomy;
    private String accession;
    private String creation_date;
    private String modification_date;
    private boolean activated;
    private Map<String, String> replicons;
    private ArrayList<String> processed_replicons;

    public Organism(String kingdom, String group, String subgroup, String name, String bioproject, String creation_date, String modification_date){
        this.kingdom = kingdom.replace("/", "_").replace(" ", "_");
        this.group = group.replace("/", "_").replace(" ", "_");
        this.subgroup = subgroup.replace("/", "_").replace(" ", "_");
        this.name = name.replace("/", "_").replace(" ", "_");
        this.bioproject = bioproject;
        this.creation_date = creation_date;
        this.modification_date = modification_date;
        this.replicons = new HashMap<String, String>();
        this.processed_replicons = new ArrayList<String>();
        this.activated = true;
    }

    public Organism(){
        this.replicons = new HashMap<String, String>();
        this.processed_replicons = new ArrayList<String>();
        this.activated = true;
    }

    public boolean addReplicon(String name, String id){
        if(this.replicons.containsKey(name)){
            return false;
        } else {
            this.replicons.put(name, id);
            return true;
        }
    }

    public void updateTree(Tree mainT){
        Tree kingdomT;
        Tree groupT;
        Tree subgroupT;
        if(mainT.contains(this.kingdom)){
            kingdomT = (Tree)mainT.get(this.kingdom);
        } else {
            kingdomT = new Tree<Tree>();
            mainT.add(this.kingdom, kingdomT);
        }

        if(kingdomT.contains(this.group)){
            groupT = (Tree)kingdomT.get(this.group);
        } else {
            groupT = new Tree<Tree>();
            kingdomT.add(this.group, groupT);
        }

        if(groupT.contains(this.subgroup)){
            subgroupT = (Tree)groupT.get(this.subgroup);
        } else {
            subgroupT = new Tree<Organism>();
            groupT.add(this.subgroup, subgroupT);
        }

        SimpleTreeWalker walker = new SimpleTreeWalker(subgroupT);
        Organism currentOrg;
        boolean isDuplicated = false;
        while(walker.hasNext()){
            currentOrg = walker.next();
            if(currentOrg.isDuplicatedOrganism(this)) {
                isDuplicated = true;
            }
        }

        if(!isDuplicated) {
            subgroupT.add(this.name, this);
        }
    }

    @Override
    public String toString(){
        /*
        String str = this.kingdom+"/"+this.group+"/"+this.subgroup+"/"+this.name+"("+this.bioproject+")";
        str += "\nReplicons :";
        for(String name : this.replicons.keySet()){
            str += "\n - "+name+" - "+this.replicons.get(name);
        }
        return str;
        */

        //On renvoie juste le nom (affichage JTREE)
        return this.getName();
    }

    public void readObject(ObjectInputStream inputstream) throws IOException, ClassNotFoundException
    {
        kingdom = (String) inputstream.readObject();
        group = (String) inputstream.readObject();
        subgroup = (String) inputstream.readObject();
        name = (String) inputstream.readObject();
        bioproject = (String) inputstream.readObject();
        accession = (String) inputstream.readObject();
        taxonomy = (String) inputstream.readObject();
        creation_date = (String) inputstream.readObject();
        modification_date = (String) inputstream.readObject();
        replicons = (HashMap<String,String>) inputstream.readObject();
        processed_replicons = (ArrayList<String>) inputstream.readObject();
    }

    public void writeObject(ObjectOutputStream outputstream) throws IOException
    {
        outputstream.writeObject(kingdom);
        outputstream.writeObject(group);
        outputstream.writeObject(subgroup);
        outputstream.writeObject(name);
        outputstream.writeObject(bioproject);
        outputstream.writeObject(accession);
        outputstream.writeObject(taxonomy);
        outputstream.writeObject(creation_date);
        outputstream.writeObject(modification_date);
        outputstream.writeObject(replicons);
        outputstream.writeObject(processed_replicons);
    }

    public String getKingdom() {
        return kingdom;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom.replace("/", "_").replace(" ", "_");
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group.replace("/", "_").replace(" ", "_");
    }

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup.replace("/", "_").replace(" ", "_");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.replace("/", "_").replace(" ", "_");
    }

    public String getBioproject() {
        return bioproject;
    }

    public void setBioproject(String bioproject) {
        this.bioproject = bioproject;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getCreationDate() {
        return creation_date;
    }

    public void setCreationDate(String creation_date) {
        this.creation_date = creation_date;
    }

    public Date getModificationDate() {
       Date date = null;
       try {
           SimpleDateFormat parser = new SimpleDateFormat("YYYY/MM/dd");
           return parser.parse(this.modification_date);
       } catch (Exception ex) {
           ex.printStackTrace();
       }
       return date;
    }

    public void setModificationDate(String modification_date) {
        this.modification_date = modification_date;
    }

    public Map<String, String> getReplicons() {
        return replicons;
    }

    public void setReplicons(Map<String, String> replicons) {
        this.replicons = replicons;
    }

    public void setActivated(boolean a){
        this.activated = a;
    }

    public boolean getActivated(){
        return this.activated;
    }

    public int size(){
        return this.activated ? this.replicons.size() : 0;
    }

    public void addProcessedReplicon(String replicon){
        this.processed_replicons.add(replicon);
    }

    public ArrayList<String> getProcessedReplicons(){
        return this.processed_replicons;
    }

    public void removeReplicons(ArrayList<String> replicons){
        for(String replicon : replicons){
            this.replicons.remove(replicon);
        }
    }

    // Creation du dossier sur le système de fichiers local
    public String getPath(){
        Config config = ConfigManager.getConfig();
        // Construction de la chaine de charactere
        String cur = "";
        cur += config.getFolderSeparator()+this.getKingdom();
        cur += config.getFolderSeparator()+this.getGroup();
        cur += config.getFolderSeparator()+this.getSubgroup();

        return cur;
    }

    public String getOrganismPath()
    {
        Config config = ConfigManager.getConfig();
        return config.getOrganismsFolder() + config.getFolderSeparator() + this.getName();
    }

    public String getResultsPath()
    {
        Config config = ConfigManager.getConfig();
        return config.getResultsFolder() + this.getPath();
    }

     public boolean isDuplicatedOrganism(Organism org) {
         Pattern globalPattern = Pattern.compile(".+?(?=[_][0-9A-Z(])");
         Matcher globalMatcher;
         Pattern localPattern;

         globalMatcher = globalPattern.matcher(this.getName());
         if(!globalMatcher.find()) {
             return false;
         }
         String localString = globalMatcher.group(0);
         localPattern = Pattern.compile(Pattern.quote(localString));

         return  localPattern.matcher(org.getName()).find()
                 && !this.getName().equals(org.getName())
                 && this.getKingdom().equals(org.getKingdom())
                 && this.getGroup().equals(org.getGroup())
                 && this.getSubgroup().equals(org.getSubgroup());
     }

     public Date getLastStatsDate() {
         String resultPath = this.getResultsPath() + File.separator + this.getName() + ".xlsx";
         if(!Files.exists(Paths.get(resultPath))) {
            return null;
         }

         Date date = null;
         try {
             XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(resultPath));
             XSSFSheet sheet = wb.getSheetAt(0);

             String sDate = sheet.getRow(4).getCell(1).toString();
             SimpleDateFormat parser = new SimpleDateFormat("dd-MM-YYYY HH:mm");
             date = parser.parse(sDate);

         } catch(Exception ex) {
             try {
                 if (Files.exists(Paths.get(resultPath))) {
                     Files.delete(Paths.get(resultPath));
                 }
             }
             catch (Exception e) {
                 ex.printStackTrace();
             }
         }
         return date;
     }

    /*
    public boolean createPath(){
        String path = this.getPath();
        // Création du dossier

        AccessManager.accessFile(path);
        File p = new File(path);

        if(p.exists() && p.isDirectory()) {
            // Si le dossier existe déjà
            AccessManager.doneWithFile(path);
            return true;
        }else{
            // Si le fichier n'existe pas
            boolean ok = p.mkdirs();
            AccessManager.doneWithFile(path);
            return ok;
        }
    }
    */
}
