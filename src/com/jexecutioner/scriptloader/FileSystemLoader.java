package com.jexecutioner.scriptloader;

import java.util.*;
import java.io.*;

import com.jexecutioner.contracts.*;
import com.jexecutioner.converters.Converters;
import com.jexecutioner.sorters.ISorter;

public class FileSystemLoader extends ScriptLoader {
	
	private String _rootDirectory;
	private ISorter _sorter;
	
	public FileSystemLoader(String rootDirectory, ISorter sorter) {
		super(sorter);
		_sorter = sorter;
		_rootDirectory = rootDirectory;
	}

	private List<ScriptDocument> _documents;
	@Override
	public List<ScriptDocument> getDocuments() { return _documents; }

	@Override
	public void loadDocuments() throws FileNotFoundException {
		
		File folder = new File(_rootDirectory);
		if (!folder.exists()) folder.mkdir();

		File[] files = folder.listFiles();
		List<ScriptDocument> docs = new ArrayList<>();

		try {
			for (File file : files) {
				FileInputStream iStream = new FileInputStream(file);
				docs.add(super.createScriptDocument(iStream, file.getName()));
			}
			
			if (docs.size() == 0) throw new FileNotFoundException(String.format("Script documents not found in %s", _rootDirectory));
			_documents = sort(docs);
		}
		catch (FileNotFoundException err) {
			throw err;
		}
		catch (Exception err) {
			// Not sure what I want to do with this yet.
		}
	}

	private List<ScriptDocument> sort(List<? extends IOrderedItem> collection) {
		List<? extends IOrderedItem> sortedCollection = _sorter.sort(collection);
		return Converters.toScriptDocuments.convert(sortedCollection);
	}

}