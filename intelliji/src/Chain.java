public class Chain
{
    /** length, check if the string chain is a multiple of 3*/
    public static boolean length(String chain)
    {
        if (chain.length() % 3 == 0)
        {
            return  true;
        }
        else
        {
            return false;
        }
    }
    /** charvalid, check if the char is only {A,C,G,T} **/
    public static boolean chainValid(String chain)
    {
        if (chain.toUpperCase().matches("[ATCG]+"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public static boolean startEnd(String chain)
    {
        chain=chain.toUpperCase();
        if (chain.startsWith("ATG") || chain.startsWith("CTG") || chain.startsWith("TTG")
                || chain.startsWith("GTG") || chain.startsWith("ATA") || chain.startsWith("ATC")
                || chain.startsWith("ATT") || chain.startsWith("TTA"))
        {
            if (chain.endsWith("TAA") || chain.endsWith("TAG") || chain.endsWith("TGA") || chain.endsWith("TTA"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }


}