package com.example.cs5132_patwo;

import com.example.cs5132_patwo.model.Reagent;

import java.util.Arrays;

public class ChemisTREETester {
    public static void main(String[] args) {
        // Test out serialize and deserialize functionality used in the MyCompounds tab
        ChemisTREE<Reagent> tree1 = new ChemisTREE<>(new Reagent[]{new Reagent("A1"), new Reagent("B1"), new Reagent("B2"), new Reagent("B3"), new Reagent("B4")});
        ChemisTREE<Reagent> tree2 = new ChemisTREE<>(new Reagent[]{new Reagent("A2"), new Reagent("B5"), new Reagent("B6"), new Reagent("B7")});
        ChemisTREE<Reagent> tree3 = new ChemisTREE<>(new Reagent[]{new Reagent("A3"), new Reagent("B8"), new Reagent("B9")});

        ChemisTREE<Reagent> rootOfAll = new ChemisTREE<>(new Reagent("ROOT"));
        rootOfAll.insert(tree1.getRoot());
        rootOfAll.insert(tree2.getRoot());
        rootOfAll.insert(tree3.getRoot());

        String serial = rootOfAll.getRoot().serialize();
        System.out.println(serial);

        ReagentNode<Reagent> deserialized = ReagentNode.deserialize(serial);

        System.out.println(Arrays.toString(deserialized.getNonNullNeighbours()));
        System.out.println(Arrays.toString(((ReagentNode<Reagent>) deserialized.neighbours[0]).getNonNullNeighbours()));
        System.out.println(Arrays.toString(((ReagentNode<Reagent>) deserialized.neighbours[1]).getNonNullNeighbours()));
        System.out.println(Arrays.toString(((ReagentNode<Reagent>) deserialized.neighbours[2]).getNonNullNeighbours()));
    }
}
