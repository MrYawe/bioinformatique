package models;

import java.util.ArrayList;

/**
 * Created by mourse on 20/02/17.
 */
public class CDS {

    //TODO: ENUM pour le type
    private String type = "none";
    private int start = 0;
    private int end = 0;

    //constructeur de la class CDS
    public CDS(String type, int start, int end)
    {
        this.type = type;
        this.start = start;
        this.end = end;
    }

    //accesseurs des paramètres de la class CDS
    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
    //---- fin des accesseurs ----

    public String toString()
    {
        return "start = " + this.getStart() + ", end = " + this.getEnd() + ";";
    }

    //Fonction qui enlève les espaces et le CDS en préfixe
    public static String format(String CDS)
    {
        return CDS.replace(" ","").replace("CDS","");
    }
    

    //// TODO: 20/02/17 améliorer la regex de processCDS
    /**
     * Traite un CDS en paramètre, permet d'extraire dans un tableau l'ensemble des séquences
     * à extraire dans la partie ORIGIN qui suivra la partie des CDS.
     * @param current_CDS
     * @return Le tableau de toutes les séquences du CDS
     */
    public static ArrayList<CDS> processCDS(String current_CDS){

        ArrayList<CDS> res = new ArrayList<>();

        String result="";

        String DATA_TO_REMOVE="";
        String DATA_TYPE="";
        
        //complement(join))
        if (current_CDS.matches( "(^complement\\(join\\()([0-9]+(\\.\\.)[0-9]+,)+([0-9]+(\\.\\.)[0-9]+)(\\)){2}"))
        {
            DATA_TO_REMOVE = "complement(join(";
            DATA_TYPE="cj";
        }
        //complement()
        if (current_CDS.matches("(^complement\\()([0-9]+(\\.\\.)[0-9]+,)+([0-9]+(\\.\\.)[0-9]+)(\\)){1}"))
        {
            DATA_TO_REMOVE="complement(";
            DATA_TYPE="c";
        }
        //join()
        if (current_CDS.matches("(^join\\()([0-9]+(\\.\\.)[0-9]+,)+([0-9]+(\\.\\.)[0-9]+)(\\)){1}"))
        {
            DATA_TO_REMOVE="join(";
            DATA_TYPE="j";

        }
        //basic case
        if (current_CDS.matches("([0-9]+(\\.\\.)[0-9]+)"))
        {
            DATA_TYPE="b";
        }


        //On enleve le les mots clés 'complement' et 'join' si jamais ils sont présents, avec leurs
        // parenthèses associées

        current_CDS = current_CDS.replace(DATA_TO_REMOVE, "");

        current_CDS = current_CDS.replaceAll("\\)", "");


        //On split sur les virgules pour récupérer les codons
        String[] codon = current_CDS.split(",");

        String[] index;

        if(!DATA_TYPE.equals(""))
        {
            //on parcours les codons
            for (String word : codon) {

                //On split sur les '..' pour avoir les index de chaque codon
                index = word.split("(\\.\\.)");

                int a = Integer.parseInt(index[0]);

                int b = Integer.parseInt(index[1]);

                //On vérifie l'ordre des coordonnées
                if (a < b) {

                    //On crée un objet CDS avec type,start et end
                    CDS elmt = new CDS(DATA_TYPE, a, b);

                    //System.out.printf("type: %s,start: %d,end: %d\n", elmt.getType(), elmt.getStart(), elmt.getEnd());

                    res.add(elmt);
                }
                else
                {
                    //CDS INVALIDE
                    res.clear();
                    return res;
                }

                //On test maintenant si les coordonnées des séquences ne se chevauchent pas
                if(res.size()>1)
                {
                    for(int i = 0; i < res.size(); i++)
                    {
                        int data = res.get(i).getEnd();

                        //on vérifie qu'il n'y ai pas de outOfRange
                        if((i+1)!=res.size() && res.get(i+1).getStart()<data)
                        {
                            //CDS INVALIDE
                            res.clear();
                            return res;
                        }
                    }
                }


            }
        }

        return res;
    }


}
