package com.jexecutioner.scriptloader;

import static org.junit.jupiter.api.Assertions.*;

import com.jexecutioner.sorters.*;
import com.jexecutioner.contracts.MockSorter;
import java.io.*;
import java.util.*;

import org.junit.Assert;
import org.junit.jupiter.api.*;

class FileLoaderTests {
	
	private String _rootDir;
	private int _documentCount;
	private List<UUID> _documentIds;
	private ISorter _sorter;
	
	@BeforeEach
	void initializeTest() {
		_sorter = new MockSorter();
		_rootDir = "C:\\FileLoaderTests";
		_documentCount = 5;
		_documentIds = new ArrayList<UUID>();
		
		generateDocuments(getScripts());
	}
	
	@AfterEach
	void afterTest() {
		File rootDir = new File(_rootDir);
		if (rootDir.exists()) deleteDirectory(rootDir);
	}

	@Test
	void fileLoaderCreatesRootDirectoryIfMissing() {
		File root = new File(_rootDir);
		deleteDirectory(root);
		
		try {
			FileSystemLoader loader = new FileSystemLoader(_rootDir, _sorter);
			loader.loadDocuments();
		}
		catch (FileNotFoundException err) { }
		finally {
			Assert.assertTrue(root.exists());
		}
	}
	
	@Test
	void fileLoaderLoadsDocuments() {
		try {
			FileSystemLoader loader = new FileSystemLoader(_rootDir, _sorter);
			loader.loadDocuments();
			Assert.assertTrue(loader.getDocuments().size() == _documentCount);
			
			for (UUID sysId : _documentIds) {
				boolean idFound = loader.getDocuments()
					.stream()
					.anyMatch(sdoc -> sdoc.getSysId().equals(sysId));
				
				Assert.assertTrue(idFound);
			}
		}
		catch (FileNotFoundException err) {}
	}
	
	@Test
	void fileLoaderMissingDocumentsThrowsException() {
		assertThrows(FileNotFoundException.class, () -> {
			File root = new File(_rootDir);
			File[] filesInRoot = root.listFiles();
			for (short i = 0; i < filesInRoot.length; ++i) {
				filesInRoot[i].delete();
			}
			
			FileSystemLoader loader = new FileSystemLoader(_rootDir, _sorter);
			loader.loadDocuments();
		});
	}

	private void generateDocuments(String scripts) {
		_documentIds.clear();
		
		File rootDir = new File(_rootDir);
		if (!rootDir.exists()) rootDir.mkdir();
		
		for (short i = 0; i < _documentCount; ++i) {
			UUID sysId = UUID.randomUUID();
			String doc = String.format("<ScriptDocument><Id>%s</Id><Order>2017-11-14:%d</Order>%s</ScriptDocument>", sysId, i, scripts);
			File docFile = new File(String.format("%s\\Doc_%d.sdoc", _rootDir, i));
			
			boolean fileCreated = false;
			try {
				FileOutputStream oStream = new FileOutputStream(docFile);
				oStream.write(doc.getBytes());
				oStream.close();
				fileCreated = true;
			}
			catch (IOException err) {
				fileCreated = false;
			}
			
			if (fileCreated) _documentIds.add(sysId);
		}
	}
	
	private String getScripts() {
		return String.format(
			"<Scripts><Script Id='%s' Executor='FakeExecutor' Order='2017-11-14'>PRINT 'Hello'</Script><Script Id='%s' Executor='FakeExecutor' Order='2017-11-14:1'>PRINT 'Hello'</Script></Scripts>",
			UUID.randomUUID(),
			UUID.randomUUID()
		);
	}
	
	private void deleteDirectory(File dir) {
		File[] contents = dir.listFiles();
		for (File content : contents) {
			if (content.isDirectory()) deleteDirectory(content);
			content.delete();
		}
		dir.delete();
	}

}