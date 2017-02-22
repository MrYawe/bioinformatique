package models;

import java.util.ArrayList;

/**
 * Created by mourse on 20/02/17.
 */
public class CDS {

    //TODO: ENUM pour le type
    String type = "none";
    int start = 0;
    int end = 0;

    //constructeur de la class CDS
    public CDS (String type, int start, int end)
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

    //Fonction qui enlève les espaces et le CDS en préfixe
    public static String format(String CDS)
    {
        return CDS.replace(" ","").replace("CDS","");
    }
    
    //Fonction qui retourne une liste permettant l'extraction,le type indique si on doit faire un join(j),
    //complement(c),complement+join(cj),basic(b), start indique le début de la séquence, end la fin
    //// TODO: 20/02/17 améliorer la regex de processCDS
    public static ArrayList<CDS> processCDS(String current_CDS){

        ArrayList<CDS> res = new ArrayList<CDS>();

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
        if(DATA_TO_REMOVE!="")
        {
            current_CDS = current_CDS.replace(DATA_TO_REMOVE,"");

            current_CDS = current_CDS.replaceAll("\\)","");
        }

        //On split sur les virgules pour récupérer les codons
        String[] codon = current_CDS.split(",");

        String[] index;

        //on parcours les codons
        for (String word : codon) {

            //On split sur les '..' pour avoir les index de chaque codon
            index=word.split("(\\.\\.)");

            int a=Integer.parseInt(index[0]);

            int b=Integer.parseInt(index[1]);

            //On vérifie l'ordre
            if (a<b){

                //On crée un objet CDS avec type,start et end
                CDS elmt = new CDS(DATA_TYPE,a,b);

                System.out.printf("type: %s,start: %d,end: %d\n",elmt.getType(),elmt.getStart(),elmt.getEnd());

                res.add(elmt);
            }

        }



        return res;
    }


}
