import models.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ExtractTest {

    @Test
    public void testExtraction() throws Exception
    {
        FileInputStream is = new FileInputStream("tests/testExtraction.gbk");
        ArrayList<String> res = ExtractTest.processFile(is);

        Assert.assertEquals("tttgaattataacaattcttacga".toUpperCase(), res.get(0).toUpperCase());

        Assert.assertEquals("aattcttacgattacgtttttcgattcacattggattttaacgctcgttgt".toUpperCase() +
                "ttttgtcgtttagttgtcaatgacttttaaggtaactttcctagttttgaaaatgtaatc".toUpperCase() +
                "ttcaggcctcagttgttcaa".toUpperCase(), res.get(1).toUpperCase());

        Assert.assertEquals("aattcttacgattacgtttttcgattcacattggattttaacgctcgttgt".toUpperCase() +
                "ttttgtcgtttagttgtcaatgacttttaaggtaactttcctagttttgaaaatgtaatc".toUpperCase() +
                "ttcaggcctcagttgttcaaagggtgggtaagtgctgtccgctggataaatcactatcca".toUpperCase() +
                "gtggacgagtgctatcgaaatcatttgcgttacccagtgg".toUpperCase(), res.get(2).toUpperCase());

        Assert.assertEquals("attacgtttttcgattcacattggattttaacgctcgttgt".toUpperCase() +
                "ttttgtcgtttagttgtcaatgacttttaaggtaactttcctagttttgaaaatgtaatc".toUpperCase()+
                "ttcaggcctcagttgttcaaagggtgggtaagtgctgtccgctggataaatcactatcca".toUpperCase() +
                "gtggacgagt".toUpperCase(), res.get(3).toUpperCase());
        Assert.assertEquals("ttggattttaacgctcgttgt".toUpperCase() +
                "ttttgtcgttaggtaactttcctagttttgaaaatgtaatcaagggtgggtaagtgctgtcc".toUpperCase(), res.get(4).toUpperCase());
    }

    public static ArrayList<String> processFile(InputStream file) throws FileNotFoundException {

        //Initialiser l' hashmap
        ArrayList<String> res = new ArrayList<String>();

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
                        sousChaine = TreatFile.getSousChaine(currentOrigin, cds, startIndex); //on récupère la sous-chaine grâce aux CDS

                        res.add(sousChaine);
                        //System.out.println(sousChaine);


                    }
                }
                if(sc.hasNextLine())
                {
                    sc.nextLine();
                }

            }
        }



        return res;
    }

}
