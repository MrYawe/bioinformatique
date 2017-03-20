package models;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by mourse on 20/02/17.
 */
public class TreatFile {

    /**
     * Permet d'initialiser un HashMap pour les statistiques des trinucléotides
     * @return Un HashMap initialisé
     */
    private static HashMap<String, Integer> initializationHmap() {

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

    /**
     * Permet d'initialiser un HashMap pour les statistiques des dinucléotides
     * @return Un HashMap initialisé
     */
    private static HashMap<String, Integer> initializationHmapDi()
    {
        HashMap<String, Integer> hash = new HashMap<>();
        hash.put("AA", 0);
        hash.put("AC", 0);
        hash.put("AG", 0);
        hash.put("AT", 0);
        hash.put("CA", 0);
        hash.put("CC", 0);
        hash.put("CG", 0);
        hash.put("CT", 0);
        hash.put("GA", 0);
        hash.put("GC", 0);
        hash.put("GG", 0);
        hash.put("GT", 0);
        hash.put("TA", 0);
        hash.put("TC", 0);
        hash.put("TG", 0);
        hash.put("TT", 0);
        return hash;
    }

    public static CDSResult processFile(InputStream file) throws FileNotFoundException {

        //Initialiser l' hashmap
        CDSResult res = new CDSResult();

        HashMap<String, Integer> hmapTriPhase0 = initializationHmap();
        HashMap<String, Integer> hmapTriPhase1 = initializationHmap();
        HashMap<String, Integer> hmapTriPhase2 = initializationHmap();

        HashMap<String, Integer> hmapDiPhase0 = initializationHmapDi();
        HashMap<String, Integer> hmapDiPhase1 = initializationHmapDi();

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
                        boolean exit = true;
                        int j = 0;
                        int index = listCDS.size();
                        // TODO Trier les CDS dans cette boucle
                        // On s'arrête à la fin de la liste ou dès qu'on a trouvé un CDS identique
                        while (exit && j < listCDS.size())
                        {
                            ArrayList<CDS> l = listCDS.get(j);
                            int i = 0;
                            exit = false;
                            // On sort de la boucle des bornes dès qu'une des bornes est différente du CDS à tester
                            while (!exit && i < l.size() && i < lcds.size())
                            {
                                CDS c = l.get(i);
                                CDS cdsToTest = lcds.get(i);
                                // Il faut que les deux bornes soient identiques
                                /*if (i == 0 && cdsToTest.getStart() > c.getStart())
                                {
                                    index = j;
                                }*/
                                if (c.getStart() == cdsToTest.getStart() && c.getEnd() == cdsToTest.getEnd())
                                {
                                    i++;
                                }
                                else
                                {
                                    exit = true;
                                }
                            }
                            j++;
                        }


                        // On ajoute le CDS récupéré seulement s'il est unique
                        if (exit)
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
                    //séquence origin
                    StringBuilder currentOrigin = new StringBuilder();

                    nowLine = sc.nextLine();

                    int startIndex = 1; // index courant de début de la séquence origine courante
                    int stopIndex = 0 ; // index courant de fin de la séquence origine courante

                    String sousChaine = ""; //sous-chaine extraite grâce aux cds

                    int finDuCDS=0; //fin du CDS courant, pour savoir quand arrêter d'extraire du contenu d'origin
                    //on parcours la liste des CDS et on récupère les sous-chaines
                    for(ArrayList<CDS> cds : listCDS)
                    {

                        if(cds.get(0).getStart() - 60 > finDuCDS && finDuCDS != 0) // on vérifie la borne du CDS précédent (-60 pour garder la ligne courante)
                        {
                            startIndex += currentOrigin.length(); // on met à jour le nouveau start
                            currentOrigin = new StringBuilder(); //on vide le bloc origin
                        }

                        if(cds.get(cds.size()-1).getEnd() > finDuCDS)
                        {
                            finDuCDS = cds.get(cds.size()-1).getEnd(); //on récupère (mise à jour) la dernière borne du CDS
                        }

                        String buffer = ""; //buffer temporaire qui récupère le traitement de la ligne

                        while(stopIndex<=finDuCDS) //on rempli le ORIGIN nécessaire
                        {
                            buffer = Origin.formatOriginLine(nowLine); //on format la ligne

                            currentOrigin.append(buffer) ; //on ajoute le buffer au bloc origin qu'on construit
                            stopIndex+=buffer.length(); //on met à jour l'index de fin du bloc origin récupéré

                            nowLine = sc.nextLine();

                        }
                        /*System.out.println("Start "+ cds.get(0).getStart());
                        System.out.println("End " + cds.get(cds.size()-1).getEnd());
                        System.out.println("Start index " + startIndex);
                        System.out.println("Stop index " + stopIndex);*/
                        sousChaine = getSousChaine(currentOrigin, cds, startIndex); //on récupère la sous-chaine grâce aux CDS


                        if (Chain.isLengthMultipleOf3(sousChaine) && Chain.isChainValid(sousChaine) && Chain.startEnd(sousChaine))
                        {
                            // STATS

                            // Trinucléotides
                            for (int i = 0; i<sousChaine.length()-3; i+=3)
                            {
                                String s1 = sousChaine.substring(i, i+3);
                                hmapTriPhase0.put(s1, hmapTriPhase0.get(s1)+1);

                                String s2 = sousChaine.substring(i+1, i+4);
                                hmapTriPhase1.put(s2, hmapTriPhase1.get(s2)+1);

                                String s3 = sousChaine.substring(i+2, i+5);
                                hmapTriPhase2.put(s3, hmapTriPhase2.get(s3)+1);
                            }

                            if (sousChaine.length() % 2 == 0)
                            {
                                // Dinucléotides
                                for (int i = 0; i<sousChaine.length()-4; i+=2)
                                {
                                    String s1 = sousChaine.substring(i, i+2);
                                    hmapDiPhase0.put(s1, hmapDiPhase0.get(s1)+1);

                                    String s2 = sousChaine.substring(i+1, i+3);
                                    hmapDiPhase1.put(s2, hmapDiPhase1.get(s2)+1);
                                }
                            }
                            else
                            {
                                // Dinucléotides
                                for (int i = 0; i<sousChaine.length()-3; i+=2)
                                {
                                    String s1 = sousChaine.substring(i, i+2);
                                    hmapDiPhase0.put(s1, hmapDiPhase0.get(s1)+1);

                                    String s2 = sousChaine.substring(i+1, i+3);
                                    hmapDiPhase1.put(s2, hmapDiPhase1.get(s2)+1);
                                }
                            }
                        }
                        else
                        {
                            cdsInvalides++;
                            /*System.out.println(sousChaine);
                            System.out.println(sousChaine.length());*/
                        }
                    }
                }
                if(sc.hasNextLine())
                {
                    sc.nextLine();

                }

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

    /**
     * Fonction d'extraction d'une sous chaine de CDS dans une séquence ORIGIN
     * @param origin
     * @param bornes
     * @return
     */
    public static String getSousChaine(StringBuilder origin, ArrayList<CDS> bornes, int startIndex)
    {
        String res = "";

        for(CDS borne : bornes)
        {
            res+=(origin.substring(borne.getStart() - startIndex   ,borne.getEnd() - startIndex + 1));
        }

        if(bornes.get(0).getType().equals("cj") || bornes.get(0).getType().equals("c"))
        {
            res = Chain.complement(res);
        }

        return res;
    }

}
