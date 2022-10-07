package com.example.cs5132_patwo;

import com.example.cs5132_patwo.model.Reagent;
import org.openscience.cdk.exception.CDKException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ChemisTREETester {
//    public static void main(String[] args) {
//        ArrayList<ArrayList<Reagent>> allReagents = new ArrayList<>();
//
//        try {
//            FileReader fr = new FileReader("text.txt");
//            BufferedReader br = new BufferedReader(fr);
//
//            String line;
//            while ((line = br.readLine()) != null) {
//                ArrayList<Reagent> reagents = new ArrayList<>();
//
//                // for each product
//                String productString = line.split(Pattern.quote("\\"))[1];
//                reagents.add(new Reagent(productString.split(":")[0], productString.split(":")[1]));
//
//                // for each reactant
//                for (String s : line.split(Pattern.quote("\\"))[0].split("~")) {
//                    reagents.add(new Reagent(s.split(":")[0], s.split(":")[1]));
//                }
//
//                allReagents.add(reagents);
//            }
//
//            br.close();
//            fr.close();
//        } catch (IOException | CDKException e) {
//            e.printStackTrace();
//        }
//
//        ArrayList<ChemisTREE<Reagent>> chemisTREEs = new ArrayList<>();
//        ChemisTREE<Reagent> superChemisTREE = new ChemisTREE<>();
//
//        for (ArrayList<Reagent> reagent : allReagents) {
//            ChemisTREE<Reagent> chemisTREE = new ChemisTREE<Reagent>(reagent);
//            System.out.println(chemisTREE.getProduct().getName());
//            superChemisTREE.insert(chemisTREE.getRoot());
//        }
//
//        System.out.println("\n\n\nsuperChemisTREE!!!");
//        System.out.println(Arrays.toString(superChemisTREE.getChildren()[0].neighbours));
//    }
}
