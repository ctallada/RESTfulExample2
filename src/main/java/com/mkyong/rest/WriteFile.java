package com.mkyong.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class WriteFile {
	private static final String FODLER_PATH = "F:\\Examples\\RESTfulExample1\\src\\main\\resources\\mst\\PrepareOrder\\";
	private static final String KEYS = "F:\\Examples\\RESTfulExample1\\src\\main\\resources\\mst\\PrepareOrder\\PrepareOrderKeys.txt";

	public String writeXMLFile(String fileName) {

		String filePath = FODLER_PATH + fileName + ".xml";
		try {
			InputStream is = new FileInputStream(filePath);

			DocumentBuilderFactory dFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			try {
				StringWriter sw = new StringWriter();
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer transformer = tf.newTransformer();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
						"no");
				transformer.setOutputProperty(OutputKeys.METHOD, "xml");
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

				transformer.transform(new DOMSource(doc), new StreamResult(sw));
				String s = sw.toString();
				System.out.println(s);
				return s;
			} catch (Exception ex) {
				throw new RuntimeException("Error converting to String", ex);
			}
		} catch (Exception e) {
		}
		return "";
	}

	public Map<String, String> getPrepareOrderFiles() {
		try {
			File file = new File(KEYS);
			Scanner scanner = new Scanner(file);
			String prepareOrderkeys = "";
			while (scanner.hasNextLine()) {
				prepareOrderkeys = scanner.nextLine();
				// System.out.println(scanner.nextLine());
			}
			scanner.close();
			Map<String, String> map = new HashMap<String, String>();
			System.out.println("Prepare Orderkeys ::: " + prepareOrderkeys);
			String[] keysArray = prepareOrderkeys.split(",");
			for (String string : keysArray) {
				// System.out.println(string);
				map.put(string, writeXMLFile(string));
			}
			return map;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
