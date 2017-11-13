package com.jexecutioner.contracts;

import static org.junit.jupiter.api.Assertions.fail;

import com.jexecutioner.contracts.ScriptLoaders.*;
import com.jexecutioner.scriptloader.ScriptLoader;
import com.jexecutioner.sorters.ISorter;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.junit.Assert;
import org.junit.jupiter.api.*;

class ScriptLoaderTests {

	private ZonedDateTime _now;
	private UUID _sysId;
	private ISorter _sorter;

	@BeforeEach
	void setup() {
		ZonedDateTime tmp = ZonedDateTime.now(ZoneOffset.UTC);
		_now = ZonedDateTime.of(tmp.getYear(), tmp.getMonthValue(), tmp.getDayOfMonth(), 0, 0 , 0, 0, tmp.getZone());
		_sysId = UUID.randomUUID();
		_sorter = new MockSorter();
	}
	
	@Test
	void baseScriptLoaderCreatesDocuments() {
		try {
			int scriptCount = 4;
			int order = 0;
			String xml = buildScriptDocumentXml(_now, order, scriptCount);
			ScriptLoader loader = new TestScriptLoader(xml, _sorter);
			loader.loadDocuments();
			
			List<ScriptDocument> sDocs = loader.getDocuments();
			Assert.assertTrue(sDocs.isEmpty() == false && sDocs.size() == 1);
			
			ScriptDocument sDoc = sDocs.get(0);
			Assert.assertTrue(sDoc.getScripts().size() == scriptCount);
			Assert.assertTrue(sDoc.getOrder() == order);
			Assert.assertTrue(sDoc.getDateCreatedUtc().equals(_now));
		}
		catch (Exception err) {
			String msg = String.format("Exception: %s Message: %s", err.getClass().getSimpleName(), err.getMessage());
			fail(msg);
		}
	}
	
	private String buildScriptDocumentXml(ZonedDateTime date, int order, int scriptCount) {
			Script[] scripts = new Script[scriptCount];
			for (short i = 0; i < scripts.length; ++i) {
				scripts[i] = new Script();
				scripts[i].setDateCreatedUtc(ZonedDateTime.now(ZoneOffset.UTC));
				scripts[i].setDocumentId(_sysId);
				scripts[i].setSysId(UUID.randomUUID());
				scripts[i].setScriptText("SELECT * FROM Table");
			}
			ScriptDocument sDoc = new ScriptDocument();
			sDoc.setScripts(Arrays.asList(scripts));
			sDoc.setSysId(_sysId);
			sDoc.setOrder(order);
			sDoc.setDateCreatedUtc(_now);
			
			return toString(sDoc);
	}
	
	private String toString(ScriptDocument doc) {

		DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String orderStr = dtFormatter.format(doc.getDateCreatedUtc());
		if (doc.getOrder() != 0) orderStr += String.format(":%d", doc.getOrder());

		StringBuilder sb = new StringBuilder();
		sb.append("<ScriptDocument>");
		sb.append(String.format("<Id>%s</Id>", _sysId));
		sb.append(String.format("<Order>%s</Order>", orderStr));
		sb.append("<Scripts>");
		
		for (Script script : doc.getScripts()) {
			sb.append(
				String.format(
					"<Script Id='%s' Executor='%s' Order='%s'>\n%s\n</Script>\n",
					script.getSysId(),
					(script.getExecutorName() != null) ? script.getExecutorName() : "",
					orderStr,
					(script.getScriptText() != null) ? script.getScriptText() : ""
				)
			);
		}

		sb.append("</Scripts>");
		sb.append("</ScriptDocument>");
		return sb.toString();
	}

}
