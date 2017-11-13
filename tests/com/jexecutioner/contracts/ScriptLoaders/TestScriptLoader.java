package com.jexecutioner.contracts.ScriptLoaders;

import java.io.*;

import java.util.*;

import com.jexecutioner.contracts.*;
import com.jexecutioner.scriptloader.*;
import com.jexecutioner.sorters.ISorter;

public class TestScriptLoader extends ScriptLoader {
	private static final String RESOURCE_NAME = "InMemory";
	private String _xml;
	private List<ScriptDocument> _documents;
	
	public TestScriptLoader(String xml, ISorter sorter) {
		super(sorter);
		_xml = xml;
		_documents = new ArrayList<ScriptDocument>();
	}

	@Override
	public List<ScriptDocument> getDocuments() { return _documents; }

	@Override
	public void loadDocuments() {
		try {
			InputStream iStream = new ByteArrayInputStream(_xml.getBytes());
			_documents.add(super.createScriptDocument(iStream, RESOURCE_NAME));
		}
		catch (Exception err) {
			// Not sure what I want to do here for error handling.
		}
	}

}
