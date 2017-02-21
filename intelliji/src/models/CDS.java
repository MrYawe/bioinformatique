package models;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mourse on 20/02/17.
 */
public class CDS {

    String type = "none";
    int start = 0;
    int end = 0;
    //Fonction qui enleve les espaces et le CDN en préfixe
    public static String format(String CDS)
    {
        String result="";
        //On enleve les espaces
        String[] tmp = CDS.split(" ");

        for (String word : tmp) {
            result += word;
        }
        System.out.println(result);
        //On enleve CDS
        tmp = result.split("CDS");
        result="";
        for (String word : tmp) {
            result += word;
        }
        System.out.println(result);

        return result;
    }
    
    //Fonction qui retourne une liste permettant l'extraction,le type indique si on doit faire un join(j),
    //complement(c),complement+join(cj),basic(b), start indique le début de la séquence, end la fin
    //// TODO: 20/02/17 améliorer la regex de listInstruct
    //// TODO: 21/02/17 code dupliqué à enlever convention à établir 
    public static ArrayList<CDS> listInstruct(String CDS){

        ArrayList<CDS> res = new ArrayList<CDS>();
        String result="";
        System.out.println(CDS);
        
        //complement(join))
        if (CDS.matches( "(^complement\\(join\\()([0-9]+(\\.\\.)[0-9]+,)+([0-9]+(\\.\\.)[0-9]+)(\\)){2}"))
        {

            //On enleve le complement(join(
            String[] tmp = CDS.split("(complement\\(join\\()");
            String[] tmp2;

            for (String word : tmp) {
                result += word;
            }
            /*System.out.println(result);*/

            //On enleve les parenthèses
            tmp = result.split("\\){2}");
            result="";
            for (String word : tmp) {
                result += word;
            }
            /*System.out.println(result);*/

            //On enlève les virgules
            tmp = result.split(",");
            result="";
            for (String word : tmp) {
                /*System.out.println(word);*/

                //On enlève les ..
                tmp2=word.split("(\\.\\.)");
                int a=Integer.parseInt(tmp2[0]);
                int b=Integer.parseInt(tmp2[1]);

                //On vérifie l'ordre
                if (a<b){

                    //On crée un objet CDS avec type,start et end
                    CDS elmt = new CDS();
                    elmt.type="cj";
                    elmt.start=a;
                    elmt.end=b;
                    System.out.printf("type: %s,start: %d,end: %d\n",elmt.type,elmt.start,elmt.end);
                    res.add(elmt);
                }

            }



        }
        //complement()
        if (CDS.matches("(^complement\\()([0-9]+(\\.\\.)[0-9]+,)+([0-9]+(\\.\\.)[0-9]+)(\\)){1}"))
        {
            //On enleve le complement(
            String[] tmp = CDS.split("(complement\\()");
            String[] tmp2;

            for (String word : tmp) {
                result += word;
            }


            //On enleve les parenthèses
            tmp = result.split("\\){1}");
            result="";
            for (String word : tmp) {
                result += word;
            }
            /*System.out.println(result);*/

            //On enlève les virgules
            tmp = result.split(",");
            result="";
            for (String word : tmp) {
                /*System.out.println(word);*/

                //On enlève les ..
                tmp2 = word.split("(\\.\\.)");
                int a = Integer.parseInt(tmp2[0]);
                int b = Integer.parseInt(tmp2[1]);

                //On vérifie l'ordre
                if (a < b) {

                    //On crée un objet CDS avec type,start et end
                    CDS elmt = new CDS();
                    elmt.type = "c";
                    elmt.start = a;
                    elmt.end = b;
                    System.out.printf("type: %s,start: %d,end: %d\n", elmt.type, elmt.start, elmt.end);
                    res.add(elmt);
                }
            }

        }
        //join()
        if (CDS.matches("(^join\\()([0-9]+(\\.\\.)[0-9]+,)+([0-9]+(\\.\\.)[0-9]+)(\\)){1}")){

            //On enleve le join(
            String[] tmp = CDS.split("(join\\()");
            String[] tmp2;

            for (String word : tmp) {
                result += word;
            }


            //On enleve les parenthèses
            tmp = result.split("\\){1}");
            result="";
            for (String word : tmp) {
                result += word;
            }
            /*System.out.println(result);*/

            //On enlève les virgules
            tmp = result.split(",");
            result="";
            for (String word : tmp) {
                /*System.out.println(word);*/

                //On enlève les ..
                tmp2 = word.split("(\\.\\.)");
                int a = Integer.parseInt(tmp2[0]);
                int b = Integer.parseInt(tmp2[1]);

                //On vérifie l'ordre
                if (a < b) {

                    //On crée un objet CDS avec type,start et end
                    CDS elmt = new CDS();
                    elmt.type = "j";
                    elmt.start = a;
                    elmt.end = b;
                    System.out.printf("type: %s,start: %d,end: %d\n", elmt.type, elmt.start, elmt.end);
                    res.add(elmt);
                }
            }
        }
        //basic case
        if (CDS.matches("([0-9]+(\\.\\.)[0-9]+)")){
            //On enlève les ..
            String[] tmp = CDS.split("(\\.\\.)");
            int a = Integer.parseInt(tmp[0]);
            int b = Integer.parseInt(tmp[1]);

            //On vérifie l'ordre
            if (a < b) {

                //On crée un objet CDS avec type,start et end
                CDS elmt = new CDS();
                elmt.type = "b";
                elmt.start = a;
                elmt.end = b;
                System.out.printf("type: %s,start: %d,end: %d\n", elmt.type, elmt.start, elmt.end);
                res.add(elmt);
            }
        }
        return res;
    }


}
