package com.jexecutioner.contracts;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;

class ScriptLoaderUtilities {
	
	public static ScriptDocument createScriptDocument(InputStream iStream, String resourceName)
		throws ParserConfigurationException
		, IOException 
		, Exception {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmlDoc = docBuilder.parse(iStream);

		return new ScriptDocument();
		
	}

}
