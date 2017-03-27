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

    public static CDSResult processFile(InputStream file) throws FileNotFoundException {

        //Initialiser l' hashmap
        CDSResult res = new CDSResult();

        int cdsInvalides = 0;

        //Ouverture du scanner
        Scanner sc = new Scanner(file);

        //on récupère la première ligne du fichier
        String nowLine = sc.nextLine();
        int nbCDS = 0;
        int nbMalformedCDS = 0;

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
                if (nowLine.contains("/organelle="))
                {
                    if (nowLine.contains("plastid"))
                    {
                        res.setType(CDSResult.Type.CHLOROPLAST);
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
                else if (nowLine.matches("^([\\s\\t]+(CDS).*$)"))
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
                        else
                        {
                            nbMalformedCDS++;
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
                                res.getTriPhase0().put(s1, res.getTriPhase0().get(s1)+1);

                                String s2 = sousChaine.substring(i+1, i+4);
                                res.getTriPhase1().put(s2, res.getTriPhase1().get(s2)+1);

                                String s3 = sousChaine.substring(i+2, i+5);
                                res.getTriPhase2().put(s3, res.getTriPhase2().get(s3)+1);
                            }

                            if (sousChaine.length() % 2 == 0)
                            {
                                // Dinucléotides
                                for (int i = 0; i<sousChaine.length()-4; i+=2)
                                {
                                    String s1 = sousChaine.substring(i, i+2);
                                    res.getDiPhase0().put(s1, res.getDiPhase0().get(s1)+1);

                                    String s2 = sousChaine.substring(i+1, i+3);
                                    res.getDiPhase1().put(s2, res.getDiPhase1().get(s2)+1);
                                }
                            }
                            else
                            {
                                // Dinucléotides
                                for (int i = 0; i<sousChaine.length()-3; i+=2)
                                {
                                    String s1 = sousChaine.substring(i, i+2);
                                    res.getDiPhase0().put(s1, res.getDiPhase0().get(s1)+1);

                                    String s2 = sousChaine.substring(i+1, i+3);
                                    res.getDiPhase1().put(s2, res.getDiPhase1().get(s2)+1);
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
        res.setNbCDS(nbCDS);
        res.setNbMalformedCDS(nbMalformedCDS);
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
