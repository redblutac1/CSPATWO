package com.example.demo;
import com.example.demo.model.Amount;
import com.example.demo.model.Reaction;
import com.example.demo.model.Reagent;
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
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static Reaction readCML(File file) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            Reaction reaction = new Reaction();
            String[] legend = {"reactant", "product", "spectator"};

            for (int count = 1; count < 4; count++) {
                NodeList nodeList = doc.getElementsByTagName(legend[count - 1]);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element el = (Element) nodeList.item(i);
                    String inchi = el.getElementsByTagName("identifier").item(1).getAttributes().item(1).getTextContent();
                    IAtomContainer container = parseInChI(inchi);
                    String name = el.getElementsByTagName("name").item(0).getTextContent();
                    NodeList amounts = el.getElementsByTagName("amount");

                    Amount[] amtArr = new Amount[amounts.getLength()];

                    if (amtArr.length == 0) {
                        reaction.add(new ReagentUse(new Reagent(container, name, inchi)), count);
                    } else {
                        for (int j = 0; j < amounts.getLength(); j++) {
                            amtArr[j] = (new Amount(amounts.item(j).getAttributes().item(0).toString().replaceAll("\"", "").replace("units=unit:", ""), Double.parseDouble(amounts.item(j).getTextContent())));
                        }
                        Arrays.sort(amtArr);

                        Amount amount = amtArr[0];

                        reaction.add(new ReagentUse(new Reagent(container, name, inchi), amount), count);

                        if (count == 2 && amtArr[amtArr.length - 1].getUnits().equals("percentYield")) {
                            reaction.setYield(amtArr[-1].getAmount());
                        }
                    }
                }
            }

            return reaction;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void moleculeToFile(IAtomContainer container) throws CDKException, IOException {
        DepictionGenerator dg = new DepictionGenerator().withSize(512, 512).withAtomColors();
        dg.depict(container).writeTo("mol.png");
    }
    public static void reactionToFile(ArrayList<Reaction> reactions, String filename) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));
        for(Reaction reaction : reactions) {
            bw.write(reaction.toString());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, CDKException {
        ArrayList<Reaction> reactions = new ArrayList();

        File dir = new File("C:\\Users\\Leemen Chan\\Downloads\\Extraction of chemical structures and reactions from the literature_Supporting Information\\ThesisSupportingInformation\\data\\reactionExtraction\\evaluation\\reactionsToBeEvaluated");

        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if(pathname.getName().endsWith(".cml")) {
                    return true;
                }
                return false;
            }
        };

        for(File file : dir.listFiles()) {
            if(file.isDirectory()) {
                File child = file.listFiles(fileFilter)[0];
                System.out.println(file.getName());
                System.out.println(child.getName());
                System.out.println();
                Reaction reaction = readCML(child);
                if(reaction == null) {
                    continue;
                }
                reactions.add(reaction);
            }
        }

        reactionToFile(reactions, "text.txt");
    }

}
