package model;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.inchi.InChIGeneratorFactory;
import org.openscience.cdk.interfaces.IAtomContainer;

public class Reagent {
    private String inchi;
    private String name;
    private IAtomContainer molecule;

    public Reagent(IAtomContainer molecule, String name, String inchi) {
        this.molecule = molecule;
        this.name = name;
        this.inchi = inchi;
    }

    public Reagent(IAtomContainer molecule, String name) throws CDKException {
        this.molecule = molecule;
        this.name = name;

        this.inchi = InChIGeneratorFactory.getInstance().getInChIGenerator(molecule).getInchi();
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name + ":" + inchi;
    }
}
