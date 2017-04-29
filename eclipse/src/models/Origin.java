package models;

/**
 * Created by mourse on 20/02/17.
 */
public class Origin {
    //Fonction qui met en forme pour la chaine finale pour 1 cds
    public static String formatpchain(String chain, CDS cds)
    {
        String[] spl = chain.split("[0-9]+");
        String[] split = chain.split("( +)");
        int start = Integer.parseInt(split[1]);
        //System.out.println("start = " + start);
        String ch = "";
        String res = "";
        for (String s : spl)
        {
            ch += s;
        }
        String chaine = ch.replace(" ", "");
        //System.out.println(chaine);
        int length = cds.getEnd() - cds.getStart();
        //System.out.println("length = " + length + " ; " + "startCDS = " + cds.getStart() + " ; endCDS = " + cds.getEnd());
        int startIndex = cds.getStart() - start;
        for (int i = startIndex; i < length + startIndex; i++)
        {
            res += chaine.charAt(i);
        }
        //System.out.println(res);
        //System.out.println(res.length());
        return res;
    }

    /**
     * Fonction pour formater correctement la séquence origin (suppression des espaces et des chiffres)
     * @param chain
     * @return
     */
    public static String formatOriginLine(String chain)
    {
        return chain.replace(" ","").replaceAll("[0-9]","").toUpperCase();
    }


}
