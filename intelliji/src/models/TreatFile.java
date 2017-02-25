package models;

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

    public static void processFile(java.io.File file) throws FileNotFoundException {

        //Initialiser l' hashmap
        HashMap<String, Integer> hmap = initializationHmap();

        //Ouverture du scanner
        Scanner sc = new Scanner(file);

        //on récupère la première ligne du fichier
        String nowLine = sc.nextLine();

        //On initialise la liste de CDS
        ArrayList<ArrayList<CDS>> listCDS = new ArrayList<>();

        //boucle principale de lecture
        while(sc.hasNextLine())
        {
            //Detection des CDS, tant que le fichier contient une ligne
            while(sc.hasNextLine() && !nowLine.startsWith("    ORIGIN")){

                //TODO: expression régulière sur le nombre d'espaces avant le mot clé 'CDS'
                if (nowLine.startsWith("     CDS"))
                {
                    //Récupérer le CDS complet
                    String current_CDS="";
                    while(! nowLine.contains("/"))
                    {
                        current_CDS+=nowLine;
                        nowLine = sc.nextLine();
                    }
                    System.out.println(CDS.format(current_CDS));

                    //Appliquer les tests

                    //Faire le traitement

                    //Construire le tableau
                    listCDS.add(CDS.processCDS(CDS.format(current_CDS)));

                }

                nowLine = sc.nextLine();
            }

            while(sc.hasNextLine() && !nowLine.contains("//"))
            {

            }

            //Application à l'origin

            //On extrait la chaine

            //On test la chaine

            //Remplit les stats

            //Renvoi l'hashtable

        }
        for(int i = 0; i < listCDS.size(); i++) {
            ArrayList<CDS> cds = listCDS.get(i);
            for (CDS cd : cds) {
                System.out.printf("type :%s, start: %d, end: %d, number : %d\n", cd.getType(), cd.getStart()
                        , cd.getEnd(), i);
            }
        }


    }

}
