package com.example.demo.model;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.inchi.InChIGeneratorFactory;

public class ReagentUse {

    private String inchi;
    private String name;
    private IAtomContainer molecule;
    private double amount;
    private String units;

    public ReagentUse(IAtomContainer molecule, String name, String inchi, double amount, String units) {
        this.molecule = molecule;
        this.name = name;
        this.inchi = inchi;
        this.amount = amount;
        this.units = units;
    }

    public ReagentUse(IAtomContainer molecule, String name, double amount, String units) throws CDKException {
        this.molecule = molecule;
        this.name = name;
        this.amount = amount;
        this.units = units;

        this.inchi = InChIGeneratorFactory.getInstance().getInChIGenerator(molecule).getInchi();
    }
}
