package models;

import java.util.ArrayList;

/**
 * Classe représentant un CDS
 */
public class CDS {

    public enum Type {NONE, JOIN, COMPLEMENT, COMPLEMENT_JOIN, BASIC}
    private Type type = Type.NONE;
    private int start = 0;
    private int end = 0;

    //constructeur de la class CDS
    public CDS(Type type, int start, int end)
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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

    /**
     * Traite un CDS en paramètre, permet d'extraire dans un tableau l'ensemble des séquences
     * à extraire dans la partie ORIGIN qui suivra la partie des CDS.
     * @param current_CDS CDS à traiter
     * @return Le tableau de toutes les séquences du CDS
     */
    public static ArrayList<CDS> processCDS(String current_CDS){

        ArrayList<CDS> res = new ArrayList<>();

        String DATA_TO_REMOVE="";
        Type DATA_TYPE= Type.NONE;
        
        //complement(join))
        if (current_CDS.matches( "(^complement\\(join\\([0-9]+\\.\\.[0-9]+((\\,([0-9]+\\.\\.[0-9]+))+)?\\)\\))"))
        {
            DATA_TO_REMOVE = "complement(join(";
            DATA_TYPE=Type.COMPLEMENT_JOIN;
        }
        //complement()
        if (current_CDS.matches("(^complement\\([0-9]+\\.\\.[0-9]+((\\,([0-9]+\\.\\.[0-9]+))+)?\\))"))
        {
            DATA_TO_REMOVE="complement(";
            DATA_TYPE=Type.COMPLEMENT;
        }
        //join()
        if (current_CDS.matches("(^join\\([0-9]+\\.\\.[0-9]+((\\,([0-9]+\\.\\.[0-9]+))+)?\\))"))
        {
            DATA_TO_REMOVE="join(";
            DATA_TYPE=Type.JOIN;

        }
        //basic case
        if (current_CDS.matches("([0-9]+(\\.\\.)[0-9]+)"))
        {
            DATA_TYPE=Type.BASIC;
        }


        //On enleve le les mots clés 'complement' et 'join' si jamais ils sont présents, avec leurs
        // parenthèses associées

        current_CDS = current_CDS.replace(DATA_TO_REMOVE, "");

        current_CDS = current_CDS.replaceAll("\\)", "");


        //On split sur les virgules pour récupérer les codons
        String[] codon = current_CDS.split(",");

        String[] index;

        if(DATA_TYPE != Type.NONE)
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

                if (res.size() == 1 && res.get(0).getType() == Type.JOIN)
                {
                    res.get(0).setType(Type.BASIC);
                }

                //On test maintenant si les coordonnées des séquences ne se chevauchent pas
                if(res.size()>1)
                {
                    for(int i = 0; i < res.size(); i++)
                    {
                        int data = res.get(i).getEnd();

                        //on vérifie qu'il n'y ait pas de outOfRange
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

    /**
     * Fonction permettant de vérifier que le CDS est bien unique
     * @param lcds CDS à tester, contenant toutes les bornes
     * @param listCDS Liste des CDS dans laquelle vérifier que le CDS n'existe pas déjà
     * @return L'index auquel il faut insérer le CDS s'il n'existe pas encore dans la liste de CDS, -1 sinon
     */
    public static int getIndex(ArrayList<CDS> lcds, ArrayList<ArrayList<CDS>> listCDS)
    {
        boolean exit = true;
        int j = 0;
		int res = listCDS.size();

        // On s'arrête à la fin de la liste ou dès qu'on a trouvé un CDS identique
        while (exit && j < listCDS.size())
        {
            ArrayList<CDS> l = listCDS.get(j);

			if (lcds.get(0).getStart() > l.get(0).getStart())
			{
				res = j+1;
			}
            if (l.size() == lcds.size() && l.get(0).getType() == lcds.get(0).getType())
            {
                int i = 0;
                exit = false;
                // On sort de la boucle des bornes dès qu'une des bornes est différente du CDS à tester
                while (!exit && i < l.size() && i < lcds.size())
                {
                    CDS c = l.get(i);
                    CDS cdsToTest = lcds.get(i);

                    // Il faut que les deux bornes soient identiques
                    if (c.getStart() == cdsToTest.getStart() && c.getEnd() == cdsToTest.getEnd())
                    {
                        i++;
                    }
                    else
                    {
                        exit = true;
                    }
                }
            }
            j++;
        }
        if (!exit)
		{
			res = -1;
		}
        return res;
    }
}
