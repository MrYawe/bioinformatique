package models;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mourse on 20/02/17.
 */
public class CDS {
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

    //// TODO: 20/02/17 améliorer la regex de listInstruct
    public static ArrayList listInstruct(String CDS){

        ArrayList res = new ArrayList();
        String result="";
        System.out.println(CDS);
        //complement(join))
        if (CDS.matches( "(^complement\\(join\\()([0-9]+(\\.\\.)[0-9]+,)+([0-9]+(\\.\\.)[0-9]+)(\\)){2}"))
        {

            //On enleve le complement(join(
            String[] tmp = CDS.split("(complement\\(join\\()");

            for (String word : tmp) {
                result += word;
            }
            System.out.println(result);

            tmp = result.split("\\){2}");
            result="";
            for (String word : tmp) {
                result += word;
            }
            System.out.println(result);


        }
        //complement()
        if (CDS.matches("(^complement\\()[0-9]+(..)\\)"))
        {

        }
        //join()
        if (CDS.matches("(^join\\()[0-9]+(..)\\)")){

        }
        //basic case
        if (CDS.matches("([0-9]+)(\\.\\.)([0-9]+)")){

        }
        return res;
    }


}
