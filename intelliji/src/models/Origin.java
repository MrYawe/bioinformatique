package models;

import java.util.InputMismatchException;

/**
 * Created by mourse on 20/02/17.
 */
public class Origin {
    //Fonction qui met en forme pour la chaine finale pour 1 cds
    public static String formatpchain(String chain,CDS cds)
    {
        String[] pchain = chain.split(" +");
        System.out.print(chain);
        String finalChain="";
        for (int j = 1; j < pchain.length; j+=7) {
            int startl =Integer.parseInt(pchain[j]);
            int diff = cds.getStart()-startl;
            if(startl<cds.getStart())
            {
                if (diff<60)
                {
                    for (int i = j+1; i < j+7; i++) {
                        finalChain += pchain[i];
                    }
                }
            }
            if (startl>cds.getStart()&& startl<cds.getEnd())
            {
                for (int i = j+1; i < j+7; i++) {
                    finalChain += pchain[i];
                }
            }

        }
        System.out.print(finalChain);
        return chain;
    }
}
