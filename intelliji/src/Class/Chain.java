package Class;

/*Suppose que la lecture du fichier a déjà été faite et que les join+ complément ont déjà été fait*/
public class Chain
{
    /** length, check if the string chain is a multiple of 3*/
    public static boolean length(String chain)
    {
        return chain.length() % 3 == 0;
    }
    /** charvalid, check if the char is only {A,C,G,T} **/
    public static boolean chainValid(String chain)
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
    public static String extraction()
    {
        return  "t";
    }


}