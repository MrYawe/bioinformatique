package models;

/*Suppose que la lecture du fichier a déjà été faite et que les join+ complément ont déjà été fait*/
public class Chain
{
    /** isLengthMultipleOf3, check if the string chain is a multiple of 3*/
    public static boolean isLengthMultipleOf3(String chain)
    {
        return chain.length() % 3 == 0;
    }
    /** charvalid, check if the char is only {A,C,G,T} **/
    public static boolean isChainValid(String chain)
    {
        return chain.toUpperCase().matches("[ATCG]+");
    }
    public static boolean startEnd(String chain)
    {
        chain=chain.toUpperCase();
        return (chain.startsWith("ATG") || chain.startsWith("CTG")/*Codon init*/
                || chain.startsWith("TTG") || chain.startsWith("GTG")
                || chain.startsWith("ATA") || chain.startsWith("ATC")
                || chain.startsWith("ATT") || chain.startsWith("TTA"))
                && ((chain.endsWith("TAA") || chain.endsWith("TAG")/*Codon Stop*/
                || chain.endsWith("TGA") || chain.endsWith("TTA")));
    }

    /**
     * Méthode permettant de faire le complément d'une séquence
     * @param chain Chaîne à inverser
     * @return La séquence inversée
     */
    public static String complement(String chain)
    {
        String res = "";
        for (int i = 0; i < chain.length(); i++)
        {
            char c = chain.charAt(chain.length() - i - 1);
            char ch;
            switch (c)
            {
                case 'A':
                    ch = 'T';
                    break;

                case 'T':
                    ch = 'A';
                    break;

                case 'C':
                    ch = 'G';
                    break;

                default:
                    ch = 'C';
                    break;
            }
            res += ch;
        }
        return res;
    }
}