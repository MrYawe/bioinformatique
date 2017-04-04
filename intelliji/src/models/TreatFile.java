package models;

import statistics.CDSResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe permettant de traiter un fichier complet
 */
public class TreatFile {

    public static CDSResult processFile(File file) throws FileNotFoundException {

        //Initialiser l'hashmap
        CDSResult res = new CDSResult();

        int cdsInvalides = 0;

        //Ouverture du scanner
        Scanner sc = new Scanner(file);

        //on récupère la première ligne du fichier
        String nowLine = sc.nextLine();
        int nbCDS = 0;
        int nbMalformedCDS = 0;
        int nbIdenticalCDS = 0;

        //On initialise la liste de CDS
        // Liste de listes de CDS
        ArrayList<ArrayList<CDS>> listCDS = new ArrayList<>();

        //boucle principale de lecture
        while(sc.hasNextLine())
        {
            listCDS.clear();

            //Detection des CDS, tant que le fichier contient une ligne
            while(sc.hasNextLine() && !nowLine.startsWith("ORIGIN"))
            {
                if (nowLine.contains("/organelle="))
                {
                    if (nowLine.contains("plastid"))
                    {
                        res.setType(CDSResult.Type.PLAST);
                    }
                    else
                    {
                        res.setType(CDSResult.Type.MITOCHONDRION);
                    }
                }
                else if (nowLine.contains("/chromosome="))
                {
                    res.setType(CDSResult.Type.CHROMOSOME);
                }
                else if (nowLine.contains("/linkage_group="))
                {
                    res.setType(CDSResult.Type.LINKAGE);
                }
                else if (nowLine.contains("/plasmid="))
                {
                    res.setType(CDSResult.Type.PLASMID);
                }
                else if (nowLine.matches("^([\\s\\t]+(CDS)([\\s\\t])+.*$)"))
                {
                    //Récupérer le CDS complet
                    String current_CDS="";
                    while(!nowLine.contains("/"))
                    {
                        current_CDS+=nowLine;
                        nowLine = sc.nextLine();
                    }

                    ArrayList<CDS> lcds = CDS.processCDS(CDS.format(current_CDS));
                    nbCDS++;

                    if (!lcds.isEmpty())
                    {
                        int index = CDS.getIndex(lcds, listCDS);
                        // On ajoute le CDS récupéré seulement s'il est unique
                        if (index != -1)
                        {
                            listCDS.add(index, lcds);
                        }
                        else
                        {
                            nbIdenticalCDS++;
                        }
                    }
                    else
                    {
                        nbMalformedCDS++;
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
                    int stopIndex = 0; // index courant de fin de la séquence origine courante

                    int finDuCDS = 0; //fin du CDS courant, pour savoir quand arrêter d'extraire du contenu d'origin
                    //on parcours la liste des CDS et on récupère les sous-chaines
                    for (ArrayList<CDS> cds : listCDS)
                    {
                        HashMap<String, Integer> currentNumber0 = res.initializationHmap();
                        HashMap<String, Integer> currentNumber1 = res.initializationHmap();
                        HashMap<String, Integer> currentNumber2 = res.initializationHmap();

                        if (cds.get(0).getStart() - 60 > finDuCDS && finDuCDS != 0) // on vérifie la borne du CDS précédent (-60 pour garder la ligne courante)
                        {
                            startIndex += currentOrigin.length(); // on met à jour le nouveau start
                            currentOrigin = new StringBuilder(); //on vide le bloc origin
                        }

                        if (cds.get(cds.size() - 1).getEnd() > finDuCDS)
                        {
                            finDuCDS = cds.get(cds.size() - 1).getEnd(); //on récupère (mise à jour) la dernière borne du CDS
                        }

                        String buffer; //buffer temporaire qui récupère le traitement de la ligne

                        while (stopIndex <= finDuCDS) //on rempli le ORIGIN nécessaire
                        {
                            buffer = Origin.formatOriginLine(nowLine); //on format la ligne

                            currentOrigin.append(buffer); //on ajoute le buffer au bloc origin qu'on construit
                            stopIndex += buffer.length(); //on met à jour l'index de fin du bloc origin récupéré

                            nowLine = sc.nextLine();

                        }
                        /*System.out.println("Start "+ cds.get(0).getStart());
                        System.out.println("End " + cds.get(cds.size()-1).getEnd());
                        System.out.println("Start index " + startIndex);
                        System.out.println("Stop index " + stopIndex);*/
                        String sousChaine = getSousChaine(currentOrigin, cds, startIndex); //on récupère la sous-chaine grâce aux CDS


                        if (Chain.isLengthMultipleOf3(sousChaine) && Chain.isChainValid(sousChaine) && Chain.startEnd(sousChaine))
                        {
                            // STATS

                            // Trinucléotides
                            for (int i = 0; i < sousChaine.length() - 3; i += 3)
                            {
                                String s1 = sousChaine.substring(i, i + 3);
                                currentNumber0.put(s1, currentNumber0.get(s1) + 1);

                                String s2 = sousChaine.substring(i + 1, i + 4);
                                currentNumber1.put(s2, currentNumber1.get(s2) + 1);

                                String s3 = sousChaine.substring(i + 2, i + 5);
                                currentNumber2.put(s3, currentNumber2.get(s3) + 1);
                            }

                            if (sousChaine.length() % 2 == 0)
                            {
                                // Dinucléotides
                                for (int i = 0; i < sousChaine.length() - 4; i += 2)
                                {
                                    String s1 = sousChaine.substring(i, i + 2);
                                    res.getDiPhase0().put(s1, res.getDiPhase0().get(s1) + 1);

                                    String s2 = sousChaine.substring(i + 1, i + 3);
                                    res.getDiPhase1().put(s2, res.getDiPhase1().get(s2) + 1);
                                }
                            }
                            else
                            {
                                // Dinucléotides
                                for (int i = 0; i < sousChaine.length() - 3; i += 2)
                                {
                                    String s1 = sousChaine.substring(i, i + 2);
                                    res.getDiPhase0().put(s1, res.getDiPhase0().get(s1) + 1);

                                    String s2 = sousChaine.substring(i + 1, i + 3);
                                    res.getDiPhase1().put(s2, res.getDiPhase1().get(s2) + 1);
                                }
                            }
                        }
                        else
                        {
                            cdsInvalides++;
                            /*System.out.println(sousChaine);
                            System.out.println(sousChaine.length());*/
                        }

                        // Phase pref
                        for(Map.Entry<String, Integer> e : res.getTriPrefPhase0().entrySet())
                        {
                            String key = e.getKey();

                            Integer cur0 = currentNumber0.get(key);
                            Integer cur1 = currentNumber1.get(key);
                            Integer cur2 = currentNumber2.get(key);

                            res.getTriPhase0().put(key, res.getTriPhase0().get(key) + cur0);
                            res.getTriPhase1().put(key, res.getTriPhase1().get(key) + cur1);
                            res.getTriPhase2().put(key, res.getTriPhase2().get(key) + cur2);

                            Integer curMax = Math.max(cur0, Math.max(cur1, cur2));
                            if (curMax > 0)
                            {
                                if (Integer.compare(cur0, curMax) == 0)
                                    res.getTriPrefPhase0().put(key, res.getTriPrefPhase0().get(key) + 1);
                                if (Integer.compare(cur1, curMax) == 0)
                                    res.getTriPrefPhase1().put(key, res.getTriPrefPhase1().get(key) + 1);
                                if (Integer.compare(cur2, curMax) == 0)
                                    res.getTriPrefPhase2().put(key, res.getTriPrefPhase2().get(key) + 1);
                            }
                        }
                    }
                }
                if (sc.hasNextLine())
                {
                    sc.nextLine();
                }

            }
        }

        //Renvoi l'hashtable
        res.setNbCDS(nbCDS);
        res.setNbMalformedCDS(nbMalformedCDS);
        res.setNbIdenticalCDS(nbIdenticalCDS);
        res.setNbInvalidCDS(cdsInvalides);

        return res;
    }

    /**
     * Fonction d'extraction d'une sous chaine de CDS dans une séquence ORIGIN
     * @param origin Séquence dans laquelle on souhaite extraire la chaîne correspondant au CDS
     * @param bornes Liste des bornes du CDS
     * @return La sous-séquence du CDS
     */
    public static String getSousChaine(StringBuilder origin, ArrayList<CDS> bornes, int startIndex)
    {
        String res = "";

        for(CDS borne : bornes)
        {
            res+=(origin.substring(borne.getStart() - startIndex   ,borne.getEnd() - startIndex + 1));
        }

        if(bornes.get(0).getType() == CDS.Type.COMPLEMENT_JOIN || bornes.get(0).getType() == CDS.Type.COMPLEMENT)
        {
            res = Chain.complement(res);
        }

        return res;
    }
}
