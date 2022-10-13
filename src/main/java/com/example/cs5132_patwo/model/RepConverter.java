package com.example.cs5132_patwo.model;

import com.example.cs5132_patwo.model.Amount;
import com.example.cs5132_patwo.model.Reaction;
import com.example.cs5132_patwo.model.Reagent;
import com.example.cs5132_patwo.model.ReagentUse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.inchi.InChIGenerator;
import org.openscience.cdk.inchi.InChIGeneratorFactory;
import org.openscience.cdk.inchi.InChIToStructure;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

import org.json.JSONObject;

public class RepConverter {

    public static IAtomContainer parseInChI(String inchi) throws CDKException {
        InChIGeneratorFactory factory = InChIGeneratorFactory.getInstance();
        InChIToStructure i = factory.getInChIToStructure(inchi, DefaultChemObjectBuilder.getInstance());

        IAtomContainer container = i.getAtomContainer();

        return container;
    }

    public static IAtomContainer parseSMILES(String smiles) throws CDKException {
        SmilesParser parser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IAtomContainer container = parser.parseSmiles(smiles);

        return container;
    }

    public static String toInChI(IAtomContainer molecule) throws CDKException {
        InChIGeneratorFactory factory = InChIGeneratorFactory.getInstance();
        InChIGenerator gen = factory.getInChIGenerator(molecule);

        return gen.getInchi();
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
        } catch (Exception e) {
            return null;
        }
    }

    public static void moleculeToFile(IAtomContainer container) throws CDKException, IOException {
        DepictionGenerator dg = new DepictionGenerator().withSize(512, 512).withAtomColors();
        dg.depict(container).writeTo("mol.png");
    }

    public static void reactionToFile(ArrayList<Reaction> reactions, String filename) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));
        for (Reaction reaction : reactions) {
            bw.write(reaction.toString());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    public static JSONObject ORDtoJSON(String line) {
        String[] args = line.split(" ");
        //System.out.println(Arrays.toString(args));

        boolean inQuotes = false;
        String key = null;

        JSONObject json = new JSONObject();
        Stack<JSONObject> objs = new Stack();
        objs.add(json);
        String build = "";
        for(String arg : args) {
            if(arg.equals("")) {
                continue;
            }
            if(arg.equals("{")) {
                if(key != null) {
                    JSONObject newObj = new JSONObject();
                    if(objs.peek().has(key)) {
                        if(objs.peek().get(key).getClass().equals(JSONObject.class)) {
                            JSONArray jsonArray = new JSONArray();
                            jsonArray.put(objs.peek().get(key));
                            objs.peek().put(key, jsonArray);
                        }

                        objs.peek().getJSONArray(key).put(newObj);
                    }
                    else {
                        if(key.equals("inputs") || key.equals("outcomes") || key.equals("products") || key.equals("identifiers") || key.equals("amount") || key.equals("measurements") || key.equals("components")) {
                            JSONArray jsonArray = new JSONArray();
                            jsonArray.put(newObj);
                            objs.peek().put(key, jsonArray);
                        }
                        else {
                            objs.peek().put(key, newObj);
                        }
                    }

                    objs.add(newObj);
                    key = null;
                }
            }
            else if(arg.equals("}")) {
                objs.pop();
            }
            else {
                if(key == null) {
                    key = arg.replace(":", "");
                }
                else {
                    if(!inQuotes && StringUtils.countMatches(arg, "\"") % 2 == 1) {
                        build = arg.replace("\"", "");
                        inQuotes = true;
                    }
                    else if(inQuotes && StringUtils.countMatches(arg, "\"") % 2 == 1) {
                        build += " " + arg.replace("\"", "");
                        inQuotes = false;
                    }
                    else if(inQuotes) {
                        build += " " + arg;
                        arg = build;
                    }
                    if(!inQuotes) {
                        objs.peek().put(key, arg.replace("\"", ""));
                        key = null;
                    }
                }
            }
        }

        //StringWriter out = new StringWriter();
        //json.write(out);
        //System.out.println(json.toString(2));


        return json;
    }

    public static Reaction parseORD(String line) throws CDKException, InvalidSmilesException {
        Map<String, String> WORD_TO_UNITS = new HashMap<String, String>();
        WORD_TO_UNITS.put("MILLIMOLE", "mmol");


        JSONObject json = ORDtoJSON(line);
        Reaction reaction = new Reaction();

        JSONArray inputs = json.getJSONArray("inputs");

        double yield = -1.0;

        for(int i = 0; i < inputs.length(); i++) {
            JSONObject input = inputs.getJSONObject(i);
            Amount amountObj = null;
            try {
                JSONObject amount = input.getJSONObject("value").getJSONArray("components").getJSONObject(0).getJSONArray("amount").getJSONObject(0);
                amount = amount.getJSONObject(amount.keys().next());

                if(amount != null) {
                    amountObj = new Amount(WORD_TO_UNITS.get(amount.getString("units")), Double.parseDouble(amount.getString("value")));
                }
            }
            catch (JSONException e) {continue;}


            Map<String, String> ids = new HashMap<>();
            Reagent reagent = null;
            JSONArray identifiers = input.getJSONObject("value").getJSONArray("components").getJSONObject(0).getJSONArray("identifiers");
            for(int j = 0; j < identifiers.length(); j++) {
                JSONObject identifier = identifiers.getJSONObject(j);
                ids.put(identifier.getString("type"), identifier.getString("value"));
            }

            String name = "Unknown";
            if(ids.containsKey("NAME")) {
                name = ids.get("NAME");
            }
            if(ids.containsKey("INCHI")) {
                reagent = new Reagent(name, ids.get("INCHI"));
            }
            else {
                reagent = new Reagent(parseSMILES(ids.get("SMILES")), name);
            }


            ReagentUse ru = new ReagentUse(reagent, amountObj);


            String role = input.getJSONObject("value").getJSONArray("components").getJSONObject(0).getString("reaction_role");
            switch(role) {
                case "REACTANT":
                    reaction.add(ru, 1);
                    break;
                case "SOLVENT":
                    reaction.add(ru, 3);
                    break;
                case "CATALYST":
                    reaction.add(ru, 4);
                    break;
            }
        }

        JSONArray products = json.getJSONArray("outcomes").getJSONObject(0).getJSONArray("products");
        for(int i = 0; i < products.length(); i++) {
            JSONObject product = products.getJSONObject(i);
            JSONObject amount = null;
            if(product.has("measurements")) {
                JSONArray measurements = product.getJSONArray("measurements");
                for (int j = 0; j < measurements.length(); j++) {
                    if (measurements.getJSONObject(j).getString("type").equals("AMOUNT")) {
                        amount = measurements.getJSONObject(j).getJSONArray("amount").getJSONObject(0);
                        amount = amount.getJSONObject(amount.keys().next());
                    }
                    if (measurements.getJSONObject(j).getString("type").equals("YIELD")) {
                        yield = Double.parseDouble(measurements.getJSONObject(j).getJSONObject("percentage").getString("value"));
                    }
                }
            }

            Amount amountObj = null;
            if (amount != null) {
                amountObj = new Amount(WORD_TO_UNITS.get(amount.getString("units")), Double.parseDouble(amount.getString("value")));
            }

            Map<String, String> ids = new HashMap<>();
            JSONArray identifiers = product.getJSONArray("identifiers");
            for(int j = 0; j < identifiers.length(); j++) {
                JSONObject identifier = identifiers.getJSONObject(j);
                ids.put(identifier.getString("type"), identifier.getString("value"));
            }

            Reagent reagent = null;
            String name = "Unknown";
            if(ids.containsKey("NAME")) {
                name = ids.get("NAME");
            }
            if(ids.containsKey("INCHI")) {
                reagent = new Reagent(name, ids.get("INCHI"));
            }
            else {
                reagent = new Reagent(parseSMILES(ids.get("SMILES")), name);
            }

            ReagentUse ru = new ReagentUse(reagent, amountObj);


            reaction.addProduct(ru);
        }

        reaction.setYield(yield);


        return reaction;
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, CDKException {
        ArrayList<Reaction> reactions = new ArrayList();

        File dir = new File("new");

        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".txt");
            }
        };

        int filenum = 1;
        for (File file : dir.listFiles(fileFilter)) {
            System.out.println();
            System.out.println(file.getName());
            System.out.println();

            Scanner scan = new Scanner(file);
            int rxnnum = 1;
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                try {
                    Reaction rxn = parseORD(line);
                    System.out.println(filenum + " " + rxnnum);
                    reactions.add(rxn);
                }
                catch(Exception e) {
                    System.out.println("Reaction skipped.");
                }
                rxnnum++;
            }
            scan.close();

            filenum++;


        }

        reactionToFile(reactions, "ord-total-2.txt");
    }

}
