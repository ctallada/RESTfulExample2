package com.mkyong.rest;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class MSTMockServer {

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException {
		Long startTime = System.currentTimeMillis();
		WriteFile writer = new WriteFile();
		Map<String, String> map = writer.getPrepareOrderFiles();
		Long endTime = System.currentTimeMillis();
		System.out.println("Time difference ::: " + (endTime - startTime));

	}

}
