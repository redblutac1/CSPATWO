package com.example.cs5132_patwo.model;

import org.jetbrains.annotations.NotNull;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.inchi.InChIGeneratorFactory;
import org.openscience.cdk.interfaces.IAtomContainer;

public class Reagent implements Comparable<Reagent> {
    private final String inchi;
    private final String name;
    private final IAtomContainer molecule;

    public Reagent(IAtomContainer molecule, String name, String inchi) {
        this.molecule = molecule;
        this.name = name;
        this.inchi = inchi;
    }

    public Reagent(String name, String inchi) throws CDKException {
        this(InChIGeneratorFactory.getInstance().getInChIToStructure(inchi, DefaultChemObjectBuilder.getInstance()).getAtomContainer(), name, inchi);
    }

    public Reagent(IAtomContainer molecule, String name) throws CDKException {
        this.molecule = molecule;
        this.name = name;
        this.inchi = InChIGeneratorFactory.getInstance().getInChIGenerator(molecule).getInchi();
    }

    public Reagent(String name) {
        this(null, name, null);
    }


    public String getName() {
        return name;
    }

    public IAtomContainer getMolecule() {
        return molecule;
    }

    public String toString() {
        return name;
    }

    @Override
    public int compareTo(@NotNull Reagent o) {
        return name.compareTo(o.name);
    }
}
