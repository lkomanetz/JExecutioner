package com.jexecutioner.contracts.ScriptLoader;

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.AbstractMap.*;
import java.util.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import com.jexecutioner.contracts.Script;
import com.jexecutioner.contracts.ScriptDocument;

public abstract class ScriptLoader {
	
	public abstract List<ScriptDocument> getDocuments();
	public abstract void loadDocuments();
	
	protected ScriptDocument createScriptDocument(InputStream iStream, String resourceName) throws 
		ParserConfigurationException,
		IOException,
		Exception {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmlDoc = docBuilder.parse(iStream);
		xmlDoc.getDocumentElement().normalize();
		NodeList childNodes = xmlDoc.getDocumentElement().getChildNodes();
		
		ScriptDocument sDoc = new ScriptDocument();
		for (short i = 0; i < childNodes.getLength(); ++i) {
			Element childElement = (Element)childNodes.item(i);

			switch (childElement.getNodeName()) {
				case ScriptLoaderConstants.SCRIPTS_NODE:
					sDoc.setScripts(createScripts(childElement, sDoc.getSysId()));
					break;
				case ScriptLoaderConstants.DOCUMENT_ORDER_NODE:
					SimpleEntry<ZonedDateTime, Integer> dateOrderPair = parseOrderAttribute(childElement.getTextContent());
					sDoc.setOrder(dateOrderPair.getValue());
					sDoc.setDateCreatedUtc(dateOrderPair.getKey());
					break;
				case ScriptLoaderConstants.DOCUMENT_ID_NODE:
					sDoc.setSysId(UUID.fromString(childElement.getTextContent()));
					break;
			}
		}

		return sDoc;
	}
	
	private List<Script> createScripts(Element scriptElement, UUID docId) {
		NodeList scriptNodes = scriptElement.getElementsByTagName(ScriptLoaderConstants.SCRIPT_NODE);
		
		List<Script> scripts = new ArrayList<Script>();
		for (short i = 0; i < scriptNodes.getLength(); ++i) {
			Element scriptNode = (Element)scriptNodes.item(i);
			Script script = new Script();
			script.setSysId(UUID.fromString(scriptNode.getAttribute(ScriptLoaderConstants.SCRIPT_ID_ATTRIBUTE)));
			script.setExecutorName(scriptNode.getAttribute(ScriptLoaderConstants.EXECUTOR_NAME_ATTRIBUTE));
			script.setScriptText(scriptNode.getTextContent());

			SimpleEntry<ZonedDateTime, Integer> dateOrderPair = parseOrderAttribute(
				scriptNode.getAttribute(ScriptLoaderConstants.ORDER_ATTRIBUTE)
			);
			
			script.setDateCreatedUtc(dateOrderPair.getKey());
			script.setOrder(dateOrderPair.getValue());
			script.setDocumentId(docId);

			scripts.add(script);
		}

		return scripts;
	}
	
	private SimpleEntry<ZonedDateTime, Integer> parseOrderAttribute(String value) {
		DateTimeFormatter dtFormatter = new DateTimeFormatterBuilder()
			.appendPattern("yyyy-MM-dd['T'HH:mm:ss.SSSz]")
			.parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
			.parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
			.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
			.parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
			.parseDefaulting(ChronoField.NANO_OF_SECOND, 0)
			.parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
			.toFormatter();

		String[] values = value.split(":");
		ZonedDateTime orderDate;
		int order = 0;

		try {
			orderDate = ZonedDateTime.parse(values[0], dtFormatter);
		}
		catch (DateTimeParseException parseErr) {
			orderDate = null;
		}
		
		if (values.length == 2) {
			try {
				order = Integer.parseInt(values[1]);
			}
			catch (NumberFormatException numFormatErr) {
				
			}
		}

		return new SimpleEntry<ZonedDateTime, Integer>(orderDate, order);
	}

}
