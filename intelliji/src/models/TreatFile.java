package models;

import javax.swing.text.html.CSS;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by mourse on 20/02/17.
 */
public class TreatFile {

    public static HashMap<String, Integer> initializationHmap() {

      /*Adding elements to HashMap*/
        HashMap<String, Integer> hmap = new HashMap<>();
        hmap.put("TTT", 0);hmap.put("TCT", 0);hmap.put("TAT", 0);hmap.put("TGT", 0);
        hmap.put("TTC", 0);hmap.put("TCC", 0);hmap.put("TAC", 0);hmap.put("TGC", 0);
        hmap.put("TTA", 0);hmap.put("TCA", 0);hmap.put("TAA", 0);hmap.put("TGA", 0);
        hmap.put("TTG", 0);hmap.put("TCG", 0);hmap.put("TAG", 0);hmap.put("TGG", 0);

        hmap.put("CTT", 0);hmap.put("CCT", 0);hmap.put("CAT", 0);hmap.put("CGT", 0);
        hmap.put("CTC", 0);hmap.put("CCC", 0);hmap.put("CAC", 0);hmap.put("CGC", 0);
        hmap.put("CTA", 0);hmap.put("CCA", 0);hmap.put("CAA", 0);hmap.put("CGA", 0);
        hmap.put("CTG", 0);hmap.put("CCG", 0);hmap.put("CAG", 0);hmap.put("CGG", 0);

        hmap.put("ATT", 0);hmap.put("ACT", 0);hmap.put("AAT", 0);hmap.put("AGT", 0);
        hmap.put("ATC", 0);hmap.put("ACC", 0);hmap.put("AAC", 0);hmap.put("AGC", 0);
        hmap.put("ATA", 0);hmap.put("ACA", 0);hmap.put("AAA", 0);hmap.put("AGA", 0);
        hmap.put("ATG", 0);hmap.put("ACG", 0);hmap.put("AAG", 0);hmap.put("AGG", 0);

        hmap.put("GTT", 0);hmap.put("GCT", 0);hmap.put("GAT", 0);hmap.put("GGT", 0);
        hmap.put("GTC", 0);hmap.put("GCC", 0);hmap.put("GAC", 0);hmap.put("GGC", 0);
        hmap.put("GTA", 0);hmap.put("GCA", 0);hmap.put("GAA", 0);hmap.put("GGA", 0);
        hmap.put("GTG", 0);hmap.put("GCG", 0);hmap.put("GAG", 0);hmap.put("GGG", 0);

        return hmap;
    }

    public static CDSResult processFile(java.io.File file) throws FileNotFoundException {

        //Initialiser l' hashmap
        CDSResult res = new CDSResult();

        //TODO : clarifier l'initialisation des hmap
        HashMap<String, Integer> hmapTriPhase0 = initializationHmap();
        HashMap<String, Integer> hmapTriPhase1 = initializationHmap();
        HashMap<String, Integer> hmapTriPhase2 = initializationHmap();

        HashMap<String, Integer> hmapDiPhase0 = initializationHmap();
        HashMap<String, Integer> hmapDiPhase1 = initializationHmap();

        int cdsInvalides = 0;

        //Ouverture du scanner
        Scanner sc = new Scanner(file);

        //on récupère la première ligne du fichier
        String nowLine = sc.nextLine();

        //On initialise la liste de CDS
        // Liste de listes de CDS
        ArrayList<ArrayList<CDS>> listCDS = new ArrayList<>();

        //boucle principale de lecture
        while(sc.hasNextLine())
        {
            listCDS.clear();
            int k = 0;
            //Detection des CDS, tant que le fichier contient une ligne
            while(sc.hasNextLine() && !nowLine.startsWith("ORIGIN"))
            {

                //TODO: expression régulière sur le nombre d'espaces avant le mot clé 'CDS'
                if (nowLine.startsWith("     CDS"))
                {
                    //Récupérer le CDS complet
                    String current_CDS="";
                    while(!nowLine.contains("/"))
                    {
                        current_CDS+=nowLine;
                        nowLine = sc.nextLine();
                    }

                    ArrayList<CDS> lcds = CDS.processCDS(CDS.format(current_CDS));

                    if (!lcds.isEmpty())
                    {
                        // On vérifie que le CDS récupéré n'existe pas déjà
                        boolean ok = true;
                        int j = 0;
                        // On s'arrête à la fin de la liste ou dès qu'on a trouvé un CDS identique
                        while (ok && j < listCDS.size())
                        {
                            ArrayList<CDS> l = listCDS.get(j);
                            int i = 0;
                            ok = false;
                            // On sort de la boucle des bornes dès qu'une des bornes est différente du CDS à tester
                            while (!ok && i < l.size() && i < lcds.size())
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
                                    ok = true;
                                }
                            }
                            j++;
                        }


                        // On ajoute le CDS récupéré seulement s'il est unique
                        if (ok)
                        {
                            listCDS.add(lcds);
                        }
                    }
                }

                nowLine = sc.nextLine();
            }
            //Boucle origin
            while(sc.hasNextLine() && !nowLine.contains("//"))
            {
                //detect origin
                if (nowLine.startsWith("ORIGIN"))
                {

                    // TODO: TESTS sur la chaîne
                    // TODO: Complément si besoin
                    // TODO: HASHMAP
                    // TODO: Compteurs CDS et CDS invalides

                    //séquence origin
                    StringBuilder currentOrigin = new StringBuilder();

                    nowLine = sc.nextLine();

                    //on parcours la séquence origin et on la stocke dans un StringBuilder
                    while (!nowLine.contains("//"))
                    {
                        currentOrigin.append(Origin.formatOriginLine(nowLine)) ;
                        nowLine = sc.nextLine();
                    }

                    String sousChaine = "";

                    //on parcours la liste des CDS et on récupère les sous-chaines
                    for(ArrayList<CDS> cds : listCDS)
                    {
                        sousChaine = getSousChaine(currentOrigin, cds);
                        System.out.println(sousChaine);

                        if (Chain.isLengthMultipleOf3(sousChaine) && Chain.isChainValid(sousChaine) && Chain.startEnd(sousChaine))
                        {
                            // STATS

                            // Trinucléotides
                            for (int i = 0; i<sousChaine.length()-3; i+=3)
                            {
                                String s1 = sousChaine.substring(i, i+4);
                                hmapTriPhase0.put(s1, hmapTriPhase0.get(s1)+1);

                                String s2 = sousChaine.substring(i+1, i+5);
                                hmapTriPhase1.put(s2, hmapTriPhase1.get(s2)+1);

                                String s3 = sousChaine.substring(i+2, i+6);
                                hmapTriPhase2.put(s3, hmapTriPhase2.get(s3)+1);
                            }
                            // Dinucléotides
                            for (int i = 0; i<sousChaine.length()-2; i+=2)
                            {
                                String s1 = sousChaine.substring(i, i+3);
                                hmapDiPhase0.put(s1, hmapDiPhase0.get(s1)+1);

                                String s2 = sousChaine.substring(i+1, i+4);
                                hmapDiPhase1.put(s2, hmapDiPhase1.get(s2)+1);
                            }
                        }
                        else
                        {
                            cdsInvalides++;
                        }
                    }
                }
                if(sc.hasNextLine())
                {
                    sc.nextLine();
                }

            }

            //Renvoi l'hashtable
            res.setDiPhase0(hmapDiPhase0);
            res.setDiPhase1(hmapDiPhase1);
            res.setTriPhase0(hmapTriPhase0);
            res.setTriPhase1(hmapTriPhase1);
            res.setTriPhase2(hmapTriPhase2);
            res.setNbCDS(listCDS.size());
            res.setNbInvalidCDS(cdsInvalides);

            return res;
        }
/*        for(int i = 0; i < listCDS.size(); i++) {
            ArrayList<CDS> cds = listCDS.get(i);
            for (CDS cd : cds) {
                System.out.printf("type :%s, start: %d, end: %d, number : %d\n", cd.getType(), cd.getStart()
                        , cd.getEnd(), i);
            }
        }*/


    }

    /**
     * Fonction d'extraction d'une sous chaine de CDS dans une séquence ORIGIN
     * @param origin
     * @param bornes
     * @return
     */
    public static String getSousChaine(StringBuilder origin, ArrayList<CDS> bornes)
    {
        String res = "";

        for(CDS borne : bornes)
        {
            res+=(origin.substring(borne.getStart()-1,borne.getEnd()-1));
        }

        if(bornes.get(0).getType()=="cj" || bornes.get(0).getType()=="c")
        {
            res = Chain.complement(res);
        }

        return res;
    }

}
