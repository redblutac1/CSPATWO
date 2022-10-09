package com.example.cs5132_patwo;

import com.example.cs5132_patwo.model.Reagent;

import java.util.Arrays;

public class ChemisTREETester {
    public static void main(String[] args) {
        ChemisTREE<Reagent> tree1 = new ChemisTREE<>(new Reagent[]{new Reagent("A1"), new Reagent("B1"), new Reagent("B2"), new Reagent("B3"), new Reagent("B4")});
        ChemisTREE<Reagent> tree2 = new ChemisTREE<>(new Reagent[]{new Reagent("A2"), new Reagent("B5"), new Reagent("B6"), new Reagent("B7")});
        ChemisTREE<Reagent> tree3 = new ChemisTREE<>(new Reagent[]{new Reagent("A3"), new Reagent("B8"), new Reagent("B9")});

        ChemisTREE<Reagent> rootOfAll = new ChemisTREE<>(new Reagent("ROOT"));
        rootOfAll.insert(tree1.getRoot());
        rootOfAll.insert(tree2.getRoot());
        rootOfAll.insert(tree3.getRoot());

        System.out.println(Arrays.toString(rootOfAll.getChildren()[1].neighbours));

        System.out.println(Arrays.toString(rootOfAll.getRoot().neighbours));
        String serial = rootOfAll.getRoot().serialize();
        System.out.println(serial);

        ReagentNode<Reagent> notStack = rootOfAll.getRoot().deserialize(serial);

//        System.out.println(stack.size());
//        while (stack.size() >= 2) stack.pop();

        System.out.println(Arrays.toString(notStack.neighbours));
        System.out.println(Arrays.toString(notStack.neighbours[0].neighbours));
        System.out.println(Arrays.toString(notStack.neighbours[1].neighbours));
        System.out.println(Arrays.toString(notStack.neighbours[2].neighbours));
    }

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
