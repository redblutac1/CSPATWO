package com.example.demo;
import com.example.demo.model.Reaction;
import com.example.demo.model.ReagentUse;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.inchi.InChIGeneratorFactory;
import org.openscience.cdk.inchi.InChIToStructure;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.smiles.SmilesParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class RepConverter {

    public static IAtomContainer parseInChI(String inchi) throws CDKException {
        InChIGeneratorFactory factory = InChIGeneratorFactory.getInstance();
        InChIToStructure i = factory.getInChIToStructure(inchi, DefaultChemObjectBuilder.getInstance());

        IAtomContainer container = i.getAtomContainer();

        return container;
    }

    public static IAtomContainer parseSMILES(String smiles) throws CDKException {
        SmilesParser parser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IAtomContainer container =  parser.parseSmiles(smiles);

        return container;
    }

    public static Reaction readCML(String filename) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(filename);
        doc.getDocumentElement().normalize();

        Reaction reaction = new Reaction();

        NodeList reactants = doc.getElementsByTagName("reactant");
        for(int i = 0; i < reactants.getLength(); i++) {
            Element el = (Element) reactants.item(i);
            String inchi = el.getElementsByTagName("identifier").item(1).getAttributes().item(1).getTextContent();
            System.out.println(el.getElementsByTagName("name").item(0).getTextContent());
            // reaction.addReactant(new ReagentUse());
        }

        return null;
    }

    public static void writeToFile(IAtomContainer container) throws CDKException, IOException {
        DepictionGenerator dg = new DepictionGenerator().withSize(512, 512).withAtomColors();
        dg.depict(container).writeTo("mol.png");
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        readCML("reaction1460_1.cml");
    }

}
