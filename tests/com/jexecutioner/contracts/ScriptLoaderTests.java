package com.jexecutioner.contracts;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.junit.Assert;
import org.junit.jupiter.api.*;

class ScriptLoaderTests {

	private ZonedDateTime _now;
	private UUID _sysId;

	@BeforeEach
	void setup() {
		_now = ZonedDateTime.now(ZoneOffset.UTC);
		_sysId = UUID.randomUUID();
	}
	
	@Test
	void scriptLoaderUtilityReturnsScriptDocument() {
		try {
			ScriptDocument sDoc = getScriptDocument(_now, 0, new Script[] {});
			Assert.assertTrue(sDoc != null);
		}
		catch (Exception err) {
			fail("Test Failed: " + err.getMessage());
		}
	}
	
	@Test
	void scriptLoaderUtilityReturnsFilledInScriptDocument() {
		try {
			Script[] scripts = new Script[4];
			for (short i = 0; i < scripts.length; ++i) {
				scripts[i] = new Script();
				scripts[i].setDateCreatedUtc(ZonedDateTime.now(ZoneOffset.UTC));
				scripts[i].setDocumentId(_sysId);
				scripts[i].setSysId(UUID.randomUUID());
				scripts[i].setScriptText("SELECT * FROM Table");
			}
			
			ScriptDocument sDoc = getScriptDocument(_now, 0, scripts);
			Assert.assertTrue(
				sDoc.getDateCreatedUtc().getYear() == _now.getYear() &&
				sDoc.getDateCreatedUtc().getMonth().equals(_now.getMonth()) &&
				sDoc.getDateCreatedUtc().getDayOfYear() == _now.getDayOfYear()
			);
			Assert.assertTrue(sDoc.getScripts().size() == scripts.length);
			for (Script script : sDoc.getScripts()) {
				Assert.assertTrue(script.getScriptText().length() > 0);
				Assert.assertTrue(script.getDocumentId().equals(sDoc.getSysId()));
			}
			Assert.assertTrue(sDoc.getSysId().equals(_sysId));
		}
		catch (Exception err) {
			String msg = "Exception: " + err.getClass().getSimpleName();
			msg += "Message: " + err.getMessage();
			fail(msg);
		}
	}
	
	private ScriptDocument getScriptDocument(ZonedDateTime date, int order, Script[] scripts) 
		throws Exception {

		ScriptDocument sDoc = new ScriptDocument();
		sDoc.setDateCreatedUtc(date);
		sDoc.setSysId(_sysId);
		sDoc.setOrder(order);
		sDoc.setScripts(Arrays.asList(scripts));
		
		InputStream iStream = new ByteArrayInputStream(toString(sDoc).getBytes());
		return ScriptLoaderUtilities.createScriptDocument(iStream, new String(""));
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
