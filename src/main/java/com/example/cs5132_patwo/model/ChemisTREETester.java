package com.example.cs5132_patwo.model;

import org.openscience.cdk.exception.CDKException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ChemisTREETester {
    public static void main(String[] args) {
        ArrayList<ArrayList<Reagent>> allReactants = new ArrayList<>();
        ArrayList<Reagent> allProducts = new ArrayList<>();

        try {
            FileReader fr = new FileReader("text.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                // for each reactant
                ArrayList<Reagent> reactants = new ArrayList<>();
                for (String s : line.split(Pattern.quote("\\"))[0].split("~")) {
                    reactants.add(new Reagent(s.split(":")[0], s.split(":")[1]));
                }
                allReactants.add(reactants);

                // for each product
                String productString = line.split(Pattern.quote("\\"))[1];
                allProducts.add(new Reagent(productString.split(":")[0], productString.split(":")[1]));
            }

            br.close();
            fr.close();
        } catch (IOException | CDKException e) {
            e.printStackTrace();
        }

        ArrayList<ChemisTREE<Reagent>> chemisTREEs = new ArrayList<>();
        for (int i = 0; i < allReactants.size(); i++) {
            ChemisTREE<Reagent> chemisTREE = new ChemisTREE<Reagent>(allProducts.get(i), allReactants.get(i));
            System.out.println(chemisTREE.getProduct().getName());
        }
    }
}
